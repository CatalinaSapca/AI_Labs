package ML;

public class Main {
    public static void ErrorRegressionMT(Service s){
        double[][] realOutput = {{1, 2, 3, 4}, {3, 5, 5, 4}, {1, 2, 6, 8}, {2, 5, 5, 3}};
        double[][] computedOutput = {{1, 2.322, 2.9, 4.1}, {3.02, 4.98, 5.01, 4.2}, {1.4, 1.92, 6.12, 8.1}, {2.04, 4.87, 4.96, 3}};
        System.out.println("Eroare regresie multi-target:");
        System.out.println(s.predictionErrorRegressionMT(realOutput, computedOutput));
        System.out.println("\n");
    }
    public static void BinaryClassification(Service s){
        //----------cu output de tip eticheta
        String[] BinaryLabels = {"cat", "non-cat"};
        String[] BinaryRealOutput = {"cat", "cat", "non-cat", "cat"};
        String[] BinaryComputedOutput = {"cat", "non-cat", "non-cat", "cat"};
        //double[][] BinaryComputedOutput = {{0.95, 0, 0, 0.05}, {0.9, 0.01, 0, 0.09}, {1, 0, 0, 0}, {0.2, 0.75, 0.05, 0}, {0.4, 0.1, 0.2, 0.3}, {0.05, 0.45, 0.5, 0}, {0, 1, 0, 0}, {0.1, 0.8, 0, 0.1}, {0.2, 0.3, 0.45, 0.05}, {0, 0.9, 0.1, 0}, {0.95, 0, 0, 0.05}, {0.45, 0.55, 0, 0}, {0.05, 0.15, 0.8, 0}, {0.6, 0.05, 0, 0.35}, {0, 0, 1, 0}, {0.1, 0.1, 0.7, 0.1}, {0.05, 0.05, 0, 0.9}, {0.05, 0.05, 0.1, 0.8}, {0.45, 0.05, 0.2, 0.3}, {0.3, 0.6, 0.1, 0}, {0.05, 0.95, 0, 0}};
        System.out.println("Binary classification with label output:");
        Result rez = s.detBinaryClassificationWithLabel(BinaryLabels, BinaryRealOutput, BinaryComputedOutput);

        System.out.println("Acuratete: " + rez.accuracy);

        System.out.println("Precizie:");
        rez.precision.forEach((x, y) -> System.out.println(x + " = " + y));

        System.out.println("Rapel:");
        rez.rappel.forEach((x, y) -> System.out.println(x + " = " + y));
        System.out.println("\n");

        //------------cu output de tip probabilitati
        String[] BinaryLabels2 = {"cat", "non-cat"};
        String[] BinaryRealOutput2 = {"cat", "cat", "non-cat", "cat"};
        double[][] BinaryComputedOutput2 = {{0.95, 0.05}, {0.9, 0.1}, {0.8, 0.2}, {0.25, 0.75}};
        System.out.println("Binary classification with label output:");
        Result rez2 = s.detBinaryClassificationWithProbabilities(BinaryLabels2, BinaryRealOutput2, BinaryComputedOutput2);

        System.out.println("Acuratete: " + rez2.accuracy);

        System.out.println("Precizie:");
        rez2.precision.forEach((x, y) -> System.out.println(x + " = " + y));

        System.out.println("Rapel:");
        rez2.rappel.forEach((x, y) -> System.out.println(x + " = " + y));
        System.out.println("\n");

    }

    public static void MultiClassClassification(Service s){
        String[] labels = {"Pieton", "Biciclist", "Masina", "Semafor"};
        String[] realOutput = {"Masina", "Pieton", "Pieton", "Pieton", "Pieton", "Biciclist", "Biciclist"};
        String[] computedOutput = {"Pieton", "Pieton", "Pieton", "Biciclist", "Pieton", "Masina", "Biciclist"};
        double[][] computedProbs = {{0.55, 0.3, 0.1, 0.05}, {0.7, 0.01, 0.2, 0.09}, {0.8, 0.05, 0.05, 0.1}, {0.2, 0.65, 0.05, 0.1}, {0.4, 0.1, 0.2, 0.3}, {0.05, 0.45, 0.4, 0.1}, {0.1, 0.1, 0.1, 0.7}, {0.1, 0.6, 0.2, 0.1}, {0.2, 0.3, 0.45, 0.05}, {0.1, 0.7, 0.1, 0.1}, {0.65, 0.2, 0.1, 0.05}, {0.45, 0.35, 0.1, 0.1}, {0.05, 0.15, 0.7, 0.1}, {0.5, 0.05, 0.2, 0.25}};
        System.out.println("Clasificare multi-clasa:");
        Result rez = s.detClassification(labels, realOutput, computedOutput, computedProbs, false);

        System.out.println("Acuratete: " + rez.accuracy);

        System.out.println("Precizie:");
        rez.precision.forEach((x, y) -> System.out.println(x + " = " + y));

        System.out.println("Rapel:");
        rez.rappel.forEach((x, y) -> System.out.println(x + " = " + y));
        System.out.println("\n");
    }

    public static void lossForRegression(Service s){
        double[][] realOutput = {{1, 2, 3, 4}, {3, 5, 5, 4}, {1, 2, 6, 8}, {2, 5, 5, 3}};
        double[][] computedOutput = {{1, 2.322, 2.9, 4.1}, {3.02, 4.98, 5.01, 4.2}, {1.4, 1.92, 6.12, 8.1}, {2.04, 4.87, 4.96, 3}};
        System.out.println("Loss for regression:");
        System.out.println(s.lossForRegression(realOutput, computedOutput));
        System.out.println("\n");
    }

    public static void lossForBinaryClassification(Service s){
        double[][] realOutput = {{0.9, 0.1}, {0.7, 0.3}, {0.2, 0.8} };
        double[][] computedOutput = {{0.8, 0.2}, {0.4, 0.6}, {0.2, 0.8} };
        System.out.println("Loss for binary classification:");
        System.out.println(s.lossForBinaryClassification(realOutput, computedOutput));
        System.out.println("\n");
    }

    public static void lossForMultiClassClassification(Service s){
        String[] labels = {"Pieton", "Biciclist", "Masina", "Semafor"};
//        String[] realOutput = {"Masina", "Pieton", "Pieton", "Pieton", "Pieton", "Biciclist", "Biciclist", "Biciclist", "Biciclist", "Biciclist"
//                , "Pieton", "Pieton", "Masina", "Semafor", "Masina", "Masina", "Semafor", "Semafor", "Semafor", "Biciclist", "Biciclist"};
//        double[][] computedOutput = {{0.95, 0, 0, 0.05}, {0.9, 0.01, 0, 0.09}, {1, 0, 0, 0}, {0.2, 0.75, 0.05, 0}, {0.4, 0.1, 0.2, 0.3}, {0.05, 0.45, 0.5, 0}, {0, 1, 0, 0}, {0.1, 0.8, 0, 0.1}, {0.2, 0.3, 0.45, 0.05}, {0, 0.9, 0.1, 0}, {0.95, 0, 0, 0.05}, {0.45, 0.55, 0, 0}, {0.05, 0.15, 0.8, 0}, {0.6, 0.05, 0, 0.35}, {0, 0, 1, 0}, {0.1, 0.1, 0.7, 0.1}, {0.05, 0.05, 0, 0.9}, {0.05, 0.05, 0.1, 0.8}, {0.45, 0.05, 0.2, 0.3}, {0.3, 0.6, 0.1, 0}, {0.05, 0.95, 0, 0}};
        String[] realOutput = {"Pieton", "Pieton", "Pieton", "Pieton", "Biciclist", "Biciclist", "Biciclist", "Biciclist", "Biciclist"
                , "Pieton", "Pieton", "Masina", "Semafor", "Masina", "Masina", "Semafor"};
        double[][] computedOutput = {{0.9, 0.01, 0, 0.09}, {1, 0, 0, 0}, {0.2, 0.75, 0.05, 0}, {0.4, 0.1, 0.2, 0.3}, {0.05, 0.45, 0.5, 0}, {0, 1, 0, 0}, {0.1, 0.8, 0, 0.1}, {0.2, 0.3, 0.45, 0.05}, {0, 0.9, 0.1, 0}, {0.95, 0, 0, 0.05}, {0.45, 0.55, 0, 0}, {0.05, 0.15, 0.8, 0}, {0.6, 0.05, 0, 0.35}, {0, 0, 1, 0}, {0.1, 0.1, 0.7, 0.1}, {0.05, 0.05, 0, 0.9}};
        System.out.println("Loss for multi-class classification:");
        System.out.println(s.lossForMultiClassClassification(labels, realOutput, computedOutput));
        System.out.println("\n");
    }

    public static void lossForMultiLabelClassification(Service s){
        String[] labels = {"Pieton", "Biciclist", "Masina", "Semafor"};
        double[][] realOutput = {{1, 0, 0, 1}, {1, 1, 0, 1}};
        double[][] computedOutput = {{0.2, 0, 0, 0.8}, {0.1, 0.2, 0.3, 0.4}};
        System.out.println("Loss for multi-label classification:");
        System.out.println(s.lossForMultiLabelClassification(labels, realOutput, computedOutput));
        System.out.println("\n");
    }

    public static void main(String[] args) {
        Service s = new Service();

        //------------------------------------------------------------------------
        ErrorRegressionMT(s);
        BinaryClassification(s);
        MultiClassClassification(s);
        //------------------------------------------------------------------------

        //loss
        lossForRegression(s);
        lossForBinaryClassification(s);
        lossForMultiClassClassification(s);
        lossForMultiLabelClassification(s);
    }
}

