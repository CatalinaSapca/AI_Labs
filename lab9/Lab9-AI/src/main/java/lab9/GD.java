package lab9;

public class GD {
    private double[] B;

    public GD(double[][] caractTrain, int[] real, int epochs, double learningRate) {
        compute(caractTrain, real, epochs, learningRate);
    }

    private void compute(double[][] caractTrain, int[] real, int epochs, double learningRate) {
        B = new double[caractTrain[0].length + 1];

        for (int i = 0; i < epochs; i++) {
            double[] tempB = B.clone();

            double meanerror=0;
            int k = 0;
            for (double[] d : caractTrain) {
                double guess = B[0];

                for (int j = 1; j <= caractTrain[0].length; j++)
                    guess += B[j] * d[j - 1];

                double error = (1D / (1D + Math.exp(-guess))) - real[k];
                meanerror = meanerror + error;
                //System.out.println(error*error);

                tempB[0] = tempB[0] - error * learningRate;
                for (int j = 1; j <= caractTrain[0].length; j++)
                    tempB[j] = tempB[j] - error * learningRate * d[j - 1];
                k++;
            }
            B = tempB;
            meanerror = meanerror / caractTrain.length;
            System.out.println(meanerror);
        }
    }

    public double predict(double[] caract) {
        double guess = B[0];

        for (int i = 0; i < caract.length; i++)
            guess = guess + B[i + 1] * caract[i];

        return guess;
    }
}
