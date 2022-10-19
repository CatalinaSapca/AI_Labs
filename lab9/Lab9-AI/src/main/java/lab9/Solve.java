package lab9;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.SimpleLogistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Solve {
    private double[][] trainCaract;
    private int[] trainReal;
    private double[][] testCaract;
    private int[] testReal;
    private GD[] models;

    String filename;
    private Map<Integer, String> labelMapping= new HashMap<>();;
    private double[][] attributes;
    private int[] labels;

    Map<String, Integer> labelNames;

    public Solution manualSolution = new Solution();
    public Solution toolSolution = new Solution();

    //----------------------------------------------------------------------------------------------------
    private double media, devStd;

    private void readData(String filename) throws Exception {
        Path p = Paths.get(".\\data\\" + filename);
        if (!Files.exists(p))
            throw new Exception("File doesn't exist!");

        List<String> lines = null;
        try {
            lines = Files.lines(p).collect(Collectors.toList());
        } catch (IOException ignored) {
        }
        if (lines == null)
            throw new IOException("Reading file error!");

        labelNames = new HashMap<>();
        labels = new int[lines.size()];

        int n = lines.get(0).split(",").length;
        attributes = new double[lines.size()][n - 1];

        int i = 0;
        for (String line : lines) {
            String[] parts = line.split(",");

            for (int j = 0; j < n - 1; j++)
                attributes[i][j] = Double.parseDouble(parts[j]);

            if (!labelNames.containsKey(parts[n - 1])) {
                int nr = labelNames.keySet().size();
                labelNames.put(parts[n - 1], nr);
                labelMapping.put(nr, parts[n - 1]);
                labels[i] = nr;
            } else
                labels[i] = labelNames.get(parts[n - 1]);
            i++;
        }
    }

    public Solve(String filename) {
        this.filename = filename;
    }

    public void init(double procTest, int epochs, double learningRate) throws Exception {
        readData(this.filename);
        splitData(this.attributes, this.labels, procTest);
        for (int i = 0; i < trainCaract[0].length; i++) {
            normalization(trainCaract, i, false);
            normalization(testCaract, i, true);
        }

        models = new GD[this.labelMapping.size()];
        for (int i = 0; i < this.labelMapping.size(); i++) {
            int[] tempReal = new int[trainReal.length];
            for (int j = 0; j < trainReal.length; j++) {
                if (trainReal[j] == i)
                    tempReal[j] = 1;
                else
                    tempReal[j] = 0;
            }

            models[i] = new GD(trainCaract, tempReal, epochs, learningRate);
        }
    }

    public List<String> test() {
        List<String> testResults = new LinkedList<>();
        String format = "%.5f";
        String[] computed = new String[testCaract.length];

        for (int i = 0; i < testCaract.length; i++) {
            double[] probabilities = new double[this.labelMapping.size()];

            double sumaLoss = 0;
            for (int label = 0; label < this.labelMapping.size(); label++) {
                double pred = models[label].predict(testCaract[i]);
                probabilities[label] = (1D / (1D + Math.exp(-pred)));
                if (testReal[i] == label)
                    sumaLoss += Math.log(probabilities[label]);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("(Probabilities: ");
            for (double d : probabilities)
                sb.append(String.format(format, d)).append(" ");

            sb.append(")   - Predicted: ").append(detLabel(probabilities)).append("   - Actual: ").append(detLabel(testReal[i]));
            sb.append("     Cross-entropy loss: ").append(String.format(format, -sumaLoss));
            computed[i] = detLabel(probabilities);
            testResults.add(sb.toString());

            manualSolution.setLoss(-sumaLoss);
        }

        testResults.add("Date de training: " + trainCaract.length);
        testResults.add("Date de test: " + testReal.length);

        testResults.add("");
        testResults.addAll(detMatrix(computed, testReal));

        return testResults;
    }

    // Imparte datele in train si test dupa un procent dat
    private void splitData(double[][] cInitial, int[] rInitial, double procTest) {
        List<double[]> trainC = new LinkedList<>();
        List<double[]> testC = new LinkedList<>();
        List<Integer> trainR = new LinkedList<>();
        List<Integer> testR = new LinkedList<>();

        Random rand = new Random();

        for (int i = 0; i < cInitial.length; i++) {
            if (rand.nextDouble() < procTest) {
                testC.add(cInitial[i]);
                testR.add(rInitial[i]);
            } else {
                trainC.add(cInitial[i]);
                trainR.add(rInitial[i]);
            }
        }

        trainCaract = new double[trainC.size()][cInitial[0].length];
        trainReal = new int[trainR.size()];
        for (int i = 0; i < trainC.size(); i++) {
            trainCaract[i] = trainC.get(i);
            trainReal[i] = trainR.get(i);
        }

        testCaract = new double[testC.size()][cInitial[0].length];
        testReal = new int[testR.size()];
        for (int i = 0; i < testC.size(); i++) {
            testCaract[i] = testC.get(i);
            testReal[i] = testR.get(i);
        }
    }

    private void normalization(double[][] caract, int col, boolean isTestData) {
        if (!isTestData) {
            media = 0;
            for (double[] d : caract)
                media += d[col];
            media /= caract.length;

            double suma = 0;
            for (double[] d : caract) {
                double dif = d[col] - media;
                suma += dif * dif;
            }

            devStd = Math.sqrt((1D / (caract.length - 1D)) * suma);
        }
        for (double[] d : caract) {
            d[col] = (d[col] - media) / devStd;
        }
    }

    private List<String> detMatrix(String[] computed, int[] real) {
        String format = "%.5f";
        int nr = real.length;

        String[] realLabels = new String[nr];
        for (int i = 0; i < nr; i++) {
            realLabels[i] = detLabel(real[i]);
        }

        double accuracy = 0;
        for (int i = 0; i < nr; i++) {
            if (computed[i].equals(realLabels[i]))
                accuracy++;
        }
        accuracy /= nr;

        List<String> res = new LinkedList<>();

        res.add("Accuracy: " + String.format(format, accuracy));

        double precision = 0;
        double recall = 0;
        for (String etch : this.labelMapping.values()) {
            double tp = 0, tn = 0, fp = 0, fn = 0;
            for (int i = 0; i < nr; i++) {
                if (realLabels[i].equals(etch)) {
                    if (computed[i].equals(etch))
                        tp++;
                    else
                        fn++;
                } else {
                    if (computed[i].equals(etch))
                        fp++;
                    else
                        tn++;
                }
            }

            precision = precision + tp / (tp + fp);
            recall = recall + tp / (tp + fn);
            res.add(etch + "  --  Precizie: " + String.format(format, (tp / (tp + fp))) + "    Recall: " + String.format(format, (tp / (tp + fn))));
        }
        precision = precision / labelNames.size();
        recall = recall / labelNames.size();

        manualSolution.setAccuracy(accuracy);
        manualSolution.setPrecision(precision);
        manualSolution.setRecall(recall);

        return res;
    }

    private String detLabel(int label) {
        return this.labelMapping.get(label);
    }

    private String detLabel(double[] probabilities) {
        double max = 0;
        int index = 0;
        for (int i = 0; i < probabilities.length; i++) {
            if (probabilities[i] > max) {
                max = probabilities[i];
                index = i;
            }
        }
        return this.labelMapping.get(index);
    }

    public void tool() throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(".\\data\\" + this.filename);
        Instances data = source.getDataSet();

        data.setClassIndex(data.numAttributes() - 1);
        System.out.println("Numarul de clase: " + data.numClasses());

        data.randomize(new Random());

        int trainSize = (int) Math.round(data.numInstances() * 0.8);

        SimpleLogistic logistic = new SimpleLogistic();

        int testSize = data.numInstances() - trainSize;

        Instances train_Set = new Instances(data, 0, trainSize);
        Instances test_Set = new Instances(data, trainSize, testSize);

        logistic.buildClassifier(train_Set);

        // Testing the classifier
        Evaluation evaluation = new Evaluation(train_Set);
        evaluation.evaluateModel(logistic, test_Set);

        // Test results
        System.out.println(evaluation.toSummaryString());
        System.out.println("False negative rate: " + evaluation.falseNegativeRate(1));
        System.out.println("False positive rate: " + evaluation.falsePositiveRate(1));
        System.out.println("True negative rate: " + evaluation.trueNegativeRate(1));
        System.out.println("True positive rate: " + evaluation.truePositiveRate(1));

        System.out.println();
        System.out.println(evaluation.toClassDetailsString());

        System.out.println();
        System.out.println(evaluation.toMatrixString()); // Confusion matrix

        ArrayList<Prediction> predictions = new ArrayList<>(evaluation.predictions());

        // Afisarea predictiilor eronate
        System.out.println("Number of wrong predictions: ");
        for (Prediction pred : predictions) {
            if (pred.predicted() != pred.actual()) {
                System.out.println("Predicted: " + detLabel((int) pred.predicted()) + "   Actual: " + detLabel((int) pred.actual()));
            }
        }
        //Afisarea acuratetei predictiilor
        System.out.println("Accuracy of predictions: " + (evaluation.correct()/evaluation.predictions().size())*100 + "%");

        System.out.println("Training data size: " + trainSize);
        System.out.println("Testing data size: " + testSize);


        toolSolution.setRecall((evaluation.recall(0)+evaluation.recall(1)+evaluation.recall(2))/3);
        toolSolution.setAccuracy(evaluation.correct()/evaluation.predictions().size());
        toolSolution.setLoss(evaluation.errorRate());
        toolSolution.setPrecision((evaluation.precision(0)+evaluation.precision(1)+evaluation.precision(2))/3);
    }
}
