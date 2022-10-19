package sol;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solve {
    private double[] B;
    private double[][] trainingData;
    private double[][] testingData;
    private final double[][] allData;

    //------------------------------------------------------------------------------------------------

    private double[] media;
    private double[] devStd;

    public Solve(double[][] allData) {
        this.allData = allData;
    }

    public List<String> gradientDescent(double procTest, int noOfVar, int noOfEpochs, double learningRate) {
        compute(procTest, noOfVar, noOfEpochs, learningRate);

        List<String> testResults = new LinkedList<>();
        String format = "%.5f";

        double loss = 0;
        int i = 1;

        testResults.add("Training data: " + trainingData.length + "\n"
                        + "No of tests: " + testingData.length + "\n");

        for (double[] d : testingData) {
            double guess = B[0];

            for (int j = 1; j <= noOfVar; j++)
                guess = guess + B[j] * d[j];

            double real = d[0];

            loss = loss + Math.pow(real - guess, 2);

            testResults
                    .add("Test " + (i) + " | Predicted: " + String.format(format, guess) + " |  Real: " + String.format(format, real) + " |  (dif: " + String
                            .format(format, Math.abs(guess - real)) + ")");
            i = i + 1;
        }
        loss = loss / (double) testingData.length;



        if (noOfVar == 2)
            testResults.add("f(x) = " + String.format(format, B[0]) + " + " + String.format(format, B[1]) + " * gdp + " + String
                    .format(format, B[2]) + " * freedom");
        else
            testResults.add("f(x) = " + String.format(format, B[0]) + " + " + String.format(format, B[1]) + " * gdp");

        testResults.add("Loss: " + String.format(format, loss));

        return testResults;
    }

    private void compute(double procTest, int nrVar, int epochs, double learningRate) {
        splitData(allData, procTest);

        media = new double[allData[0].length];
        devStd = new double[allData[0].length];

        normalization(trainingData, 1, true);
        normalization(trainingData, 2, true);
        normalization(testingData, 1, false);
        normalization(testingData, 2, false);

        B = new double[nrVar + 1];

        //aici fac GD
        GD_Batch(nrVar, epochs, learningRate);
    }

    private void GD_Stochastic(int noOfVariables, int noOfEpochs, double learningRate){
        // GradDesc
        // Stochastic GD
        // Eroarea se calculează pentru fiecare exemplu de antrenament
        // Modelul se updatează pentru fiecare exemplu de antrenament (online learning)
        for (int i = 0; i < noOfEpochs; i++) {
            double[] tempB = B.clone();

            for (double[] d : trainingData) {
                double computedGuess = B[0];

                for (int j = 1; j <= noOfVariables; j++)
                    computedGuess += B[j] * d[j];

                //error(t) = computed-realOutput
                double error = computedGuess - d[0];
                //System.out.println(error*error);

                tempB[0] = tempB[0] - error * learningRate;
                for (int j = 1; j <= noOfVariables; j++)
                    tempB[j] = tempB[j] - error * learningRate * d[j];

                B = tempB;
            }
        }
    }

    private void GD_Batch(int noOfVariables, int noOfEpochs, double learningRate){
        // GradDesc
        // Batch GD
        // Eroarea se calculează pentru fiecare exemplu de antrenament
        // Modelul se updatează dupa ce toate exemplele de antrenament au fost evaluate (la finalul unei epoci)
        for (int i = 0; i < noOfEpochs; i++) {
            double[] tempB = B.clone();

            for (double[] d : trainingData) {
                double computedGuess = B[0];

                for (int j = 1; j <= noOfVariables; j++)
                    computedGuess += B[j] * d[j];

                //error(t) = computed-realOutput
                double error = computedGuess - d[0];
                //System.out.println(error*error);

                tempB[0] = tempB[0] - error * learningRate;
                for (int j = 1; j <= noOfVariables; j++)
                    tempB[j] = tempB[j] - error * learningRate * d[j];
            }

            B = tempB;
        }
    }

    private void normalization(double[][] data, int col, boolean trainData) {
        if (trainData) {
            media[col] = 0;
            for (double[] d : data)
                media[col] += d[col];
            media[col] /= data.length;

            double suma = 0;
            for (double[] d : data) {
                double dif = d[col] - media[col];
                suma += dif * dif;
            }

            devStd[col] = Math.sqrt((1D / (data.length - 1D)) * suma);
        }

        for (double[] d : data) {
            d[col] = (d[col] - media[col]) / devStd[col];
        }
    }

    private void splitData(double[][] raw, double procTest) {
        List<double[]> trainingList = new LinkedList<>();
        List<double[]> testingList = new LinkedList<>();

        Random rand = new Random();

        for (double[] d : raw) {
            if (rand.nextDouble() < procTest)
                testingList.add(d);
            else
                trainingList.add(d);
        }

        trainingData = new double[trainingList.size()][raw[0].length];
        int i = 0;
        for (double[] d : trainingList)
            trainingData[i++] = d;

        testingData = new double[testingList.size()][raw[0].length];
        i = 0;
        for (double[] d : testingList)
            testingData[i++] = d;
    }


}
