package ML;

public class Result {
    private double a;
    private double gdp;
    private double freedom;

    public Result(double a, double gdp, double freedom) {
        this.a = a;
        this.gdp = gdp;
        this.freedom = freedom;
    }

    public double getA() {
        return a;
    }

    public double getGdp() {
        return gdp;
    }

    public double getFreedom() {
        return freedom;
    }
}
