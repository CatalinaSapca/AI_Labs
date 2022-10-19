package ML;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solve {
    private double[][] data;
    private double[][] trainX;
    private double[] trainY;
    private double[][] B;
    private double[][] testX;
    private double[] testY;

    //---------------------------matrix operations
    //-matrix operations
    public static double[][] transpose(double[][] a) {
        int n = a.length;
        if (n == 0)
            throw new RuntimeException("Invalid matrix");

        int m = a[0].length;
        if (m == 0)
            throw new RuntimeException("Invalid matrix");

        double[][] rez = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rez[i][j] = a[j][i];
            }
        }

        return rez;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        int n = a.length;
        if (n == 0)
            throw new RuntimeException("Invalid first matrix");

        int p = b.length;
        if (p != a[0].length || p == 0)
            throw new RuntimeException("Can't multiply matrixes!!!");

        int m = b[0].length;
        if (m == 0)
            throw new RuntimeException("Invalid second matrix");

        double[][] rez = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int k = 0; k < p; k++)
                    sum += a[i][k] * b[k][j];
                rez[i][j] = sum;
            }
        }

        return rez;
    }

    public static double[][] inverse3(double[][] mat) {
        if (mat.length != 3 || mat[0].length != 3)
            throw new RuntimeException("Invalid matrix size");

        double a = mat[0][0], b = mat[0][1], c = mat[0][2];
        double d = mat[1][0], e = mat[1][1], f = mat[1][2];
        double g = mat[2][0], h = mat[2][1], i = mat[2][2];

        double A = e * i - f * h;
        double B = -(d * i - f * g);
        double C = d * h - e * g;
        double D = -(b * i - c * h);
        double E = a * i - c * g;
        double F = -(a * h - b * g);
        double G = b * f - c * e;
        double H = -(a * f - c * d);
        double I = a * e - b * d;


        double det = a * A + b * B + c * C;

        if (det == 0)
            throw new RuntimeException("Zero det");

        det = 1 / det;

        return new double[][]{
                {det * A, det * D, det * G},
                {det * B, det * E, det * H},
                {det * C, det * F, det * I}
        };
    }

    public static void print(double[][] a) {
        for (double[] line : a) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < a[0].length; j++)
                sb.append(line[j]).append(" ");
            System.out.println(sb.toString());
        }
    }
//---------------------------------------------------------------------------------------------------------

    public Solve(double[][] data) {
        this.data = data;
    }

    private void splitData(double[][] raw, double procTest) {
        List<double[]> train = new LinkedList<>();
        List<double[]> test = new LinkedList<>();

        Random rand = new Random();

        for (double[] d : raw) {
            if (rand.nextDouble() < procTest)
                test.add(d);
            else
                train.add(d);
        }

        trainX = new double[train.size()][3];
        trainY = new double[train.size()];
        fillData(trainX, trainY, train);

        testX = new double[test.size()][3];
        testY = new double[test.size()];
        fillData(testX, testY, test);
    }

    private void fillData(double[][] X, double[] Y, List<double[]> data) {
        int i = 0;
        for (double[] d : data) {
            X[i][0] = 1;
            X[i][1] = d[1];
            X[i][2] = d[2];
            Y[i] = d[0];
            i++;
        }
    }

    private void computeB() {
        double[][] Xt = transpose(trainX);
        B = multiply(multiply(inverse3(multiply(Xt, trainX)), Xt), transpose(new double[][]{trainY}));
    }

    public void prepareData(double procTest) {
        double[][] rawData = data;

        splitData(rawData, procTest);
    }

    public List<String> manual() {
        computeB();

        List<String> testResults = new LinkedList<>();
        String format = "%.5f";

        double loss = 0;
        for (int i = 0; i < testX.length; i++) {
            double[][] mat = new double[][]{testX[i]};
            double pred = multiply(mat, B)[0][0];
            double real = testY[i];
            loss += Math.pow(real - pred, 2);

            testResults.add("Predicted: " + String.format(format, pred) + "   Real: " + String.format(format, real) + "   (dif: " + String
                            .format(format, Math.abs(pred - real)) + ")");
        }

        testResults.add("");
        testResults.add("Date de antrenament: " + trainX.length);
        testResults.add("Numar de teste: " + testX.length);
        testResults.add("f(x) = " + String.format(format, B[0][0]) + " + " + String.format(format, B[1][0]) + " * gdp + " + String
                .format(format, B[2][0]) + " * freedom");
        testResults.add("Loss: " + String.format(format, loss));

        return testResults;
    }

    public List<String> tool() {
        double[][] newX = new double[trainX.length][2];

        for (int i = 0; i < trainX.length; i++) {
            newX[i][0] = trainX[i][1];
            newX[i][1] = trainX[i][2];
        }

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        regression.newSampleData(trainY, newX);

        double[] beta = regression.estimateRegressionParameters();

        double[] residuals = regression.estimateResiduals();
        double loss = regression.calculateResidualSumOfSquares();

        List<String> testResults = new LinkedList<>();
        String format = "%.5f";
        testResults.add("Functie determinata de tool:");
        testResults.add("f(x) = " + String.format(format, beta[0]) + " + " + String.format(format, beta[1]) + " * gdp + " + String
                .format(format, beta[2]) + " * freedom");
        //testResults.add("Residuals: " + residuals.);
        //testResults.add("Loss: " + loss);

        return testResults;
    }
}
