package ML;

import java.util.Map;

public class Result {
    public final Double accuracy;
    public final Map<String, Double> precision;
    public final Map<String, Double> rappel;

    public Result(Double accuracy, Map<String, Double> precision, Map<String, Double> rappel) {
        this.accuracy = accuracy;
        this.precision = precision;
        this.rappel = rappel;
    }
}