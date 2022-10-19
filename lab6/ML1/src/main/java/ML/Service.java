package ML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

    public double predictionErrorRegressionMT(double[][] realOutput, double[][] computedOutput) {
        double sumaErori = 0;

        for (int i = 0; i < realOutput.length; i++) {
            double[] r = realOutput[i];
            double[] c = computedOutput[i];

            double sumaSet = 0;
            for (int j = 0; j < r.length; j++) {
                sumaSet += (r[j] - c[j]) * (r[j] - c[j]);
            }
            sumaErori += Math.sqrt(sumaSet / r.length);
        }
        return sumaErori / realOutput.length;
    }

    private String detLabel(String[] labels, double[] probabilities) {
        double max = 0;
        int index = 0;
        for (int i = 0; i < probabilities.length; i++) {
            if (probabilities[i] > max) {
                max = probabilities[i];
                index = i;
            }
        }
        return labels[index];
    }

    //clasificare multi-class cu output de tip eticheta/ probanilitati
    public Result detClassification(String[] labels, String[] realOutput, String[] computedOutput, double[][] computedProbs, boolean usesProbabilities) {
        int nr = realOutput.length;
        String[] comp;
        if (usesProbabilities) {
            comp = new String[nr];
            for (int i = 0; i < nr; i++)
                comp[i] = detLabel(labels, computedProbs[i]);
        } else
            comp = computedOutput;

        double accuracy = 0;
        for (int i = 0; i < nr; i++) {
            if (comp[i].equals(realOutput[i]))
                accuracy++;
        }
        accuracy /= nr;

        // (TP+TN)/(TP+TN+FP+FN)-accuracy

        Map<String, Double> precision = new HashMap<>();
        Map<String, Double> rappel = new HashMap<>();

        for (String label : labels) {
            double tp = 0, tn = 0, fp = 0, fn = 0;
            for (int i = 0; i < nr; i++) {
                if (realOutput[i].equals(label)) {
                    if (comp[i].equals(label))
                        tp++;
                    else
                        fn++;
                } else {
                    if (comp[i].equals(label))
                        fp++;
                    else
                        tn++;
                }
            }

            //TP/(TP+FP)-precision
            if(tp+fp>0)
                precision.put(label, (tp / (tp + fp)));
            //TP/(TP+FN)-recall
            if(tp+fn>0)
                rappel.put(label, (tp / (tp + fn)));
        }

        return new Result(accuracy, precision, rappel);
    }

    //clasificare binara cu output de tip eticheta
    public Result detBinaryClassificationWithLabel(String[] labels, String[] realOutput, String[] computedOutput) {
        int nr = realOutput.length;

        double accuracy = 0;
        for (int i = 0; i < nr; i++) {
            if (computedOutput[i].equals(realOutput[i]))
                accuracy++;
        }
        accuracy /= nr;

        Map<String, Double> precision = new HashMap<>();
        Map<String, Double> rappel = new HashMap<>();

        for (String label : labels) {
            double tp = 0, tn = 0, fp = 0, fn = 0;
            for (int i = 0; i < nr; i++) {
                if (realOutput[i].equals(label)) {
                    if (computedOutput[i].equals(label))
                        tp++;
                    else
                        fn++;
                } else {
                    if (computedOutput[i].equals(label))
                        fp++;
                    else
                        tn++;
                }
            }

            precision.put(label, (tp / (tp + fp)));
            rappel.put(label, (tp / (tp + fn)));
        }

        return new Result(accuracy, precision, rappel);
    }

    public int detMaxIndex(double[] v){
        double max=v[0];
        int index = 0;
        for(int i=1;i<v.length;i++){
            if(v[i]>max) {
                max = v[i];
                index = i;
            }
        }
        return index;
    }

    //clasificare binara cu output de tip probabilitati
    public Result detBinaryClassificationWithProbabilities(String[] labels, String[] realOutput, double[][] computedOutput) {
        int nr = realOutput.length;

        double accuracy = 0;
        for (int i = 0; i < nr; i++) {
            if (labels[this.detMaxIndex(computedOutput[i])].equals(realOutput[i]))
                accuracy++;
        }
        accuracy /= nr;

        Map<String, Double> precision = new HashMap<>();
        Map<String, Double> rappel = new HashMap<>();

        for (String label : labels) {
            double tp = 0, tn = 0, fp = 0, fn = 0;
            for (int i = 0; i < nr; i++) {
                if (realOutput[i].equals(label)) {
                    if (labels[this.detMaxIndex(computedOutput[i])].equals(label))
                        tp++;
                    else
                        fn++;
                } else {
                    if (labels[this.detMaxIndex(computedOutput[i])].equals(label))
                        fp++;
                    else
                        tn++;
                }
            }

            precision.put(label, (tp / (tp + fp)));
            rappel.put(label, (tp / (tp + fn)));
        }

        return new Result(accuracy, precision, rappel);
    }

    //Mean Square Error, Quadratic loss, L2 Loss
    public double lossForRegression(double[][] realOutput, double[][] computedOutput){
        double loss = 0;

        for (int i = 0; i < realOutput.length; i++) {
            double[] r = realOutput[i];
            double[] c = computedOutput[i];

            double localLoss = 0;
            for (int j = 0; j < r.length; j++) {
                localLoss += (r[j] - c[j]) * (r[j] - c[j]);
            }
            loss = loss + localLoss;
        }
        return loss / realOutput.length;
    }

    public double lossForBinaryClassification(double[][] realOutput, double[][] computedOutput){
        double rez = 0;
        List<double[]> err = new ArrayList<double[]>();

        int pr;
        for(int i=0;i<computedOutput.length;i++){
            double bestMatchComputed=computedOutput[i][0];
            int bestIndexComputed=0;
            for (int j=0;j<computedOutput[i].length;j++)
                if (computedOutput[i][j] > bestMatchComputed) {
                    bestMatchComputed = computedOutput[i][j];
                    bestIndexComputed = j;
                }
            double bestMatchReal=realOutput[i][0];
            int bestIndexReal=0;
            for (int j=0;j<realOutput[i].length;j++)
                if (realOutput[i][j] > bestMatchReal) {
                    bestMatchReal = realOutput[i][j];
                    bestIndexReal= i;
                }

            if(bestIndexReal==bestIndexComputed)
                pr=1;
            else{
                pr=0;
                err.add(computedOutput[i]);
            }
            rez+=-1*(pr*(Math.log(bestMatchComputed))+(1-pr)*(Math.log(1-bestMatchComputed)));
        }
//        err.forEach(e->{
//            for (double v : e) System.out.print(v + " ");
//        });
//        System.out.println("\n");

        return rez;
    }

    //Cross-entropy
//    Determinarea loss-ului (funcție de cost) în cazul problemelor de clasificare multi-clasă
//    (outputul clasificatorului este reprezentat ca o matrice cu noSamples x noClasses
//     valori reale)
    public double lossForMultiClassClassification(String[] labels, String[] realOutput, double[][] computedProbs) {
        double loss=0;
        int i=0;
        double[] computed;
        for (String data : realOutput) {
            double localLoss = 0;
            computed = computedProbs[i];
            i=i+1;

            double bestMatchComputed=computed[0];
            int bestIndexComputed=0;
            for (int j=0;j<computed.length;j++)
                if (computed[j] > bestMatchComputed) {
                    bestMatchComputed = computed[j];
                    bestIndexComputed = j;
                }
            String computedData = realOutput[bestIndexComputed];

            //localLoss = (-1)*Math.log(computed[bestIndexComputed]);

            int ind=0;
            for(int l=0;l<labels.length;l++){
                if(labels[l].equals(data))
                    ind=l;
            }
            localLoss = (-1)*Math.log(computed[ind]);
            loss = loss + localLoss;
        }

        return loss;
    }

//    Determinarea loss-ului (funcție de cost) în cazul problemelor de clasificare multi-label
//    (outputul clasificatorului este reprezentat ca o matrice cu noSamples x noClasses
//     valori reale
    public double lossForMultiLabelClassification(String[] labels, double[][] realOutput, double[][] computedProbs) {
        double loss=0;
        int i=0;
        double[] real;
        double[] computed;
        for (int j=0; j<realOutput.length;j++) {
            double localLoss = 0;
            real = realOutput[j];
            computed = computedProbs[j];

            for(int k=0;k<real.length;k++){
//                if(real[i]==1)
//                    localLoss = localLoss + (-1)*(real[i]* Math.log(computed[i]) + (1-real[i])*Math.log(1-computed[i]));
                localLoss = localLoss + (-1)*(real[i]* Math.log(computed[i]) + (1-real[i])*Math.log(1-computed[i]));
            }
            loss = loss + localLoss;
        }

        return loss;
    }
}
