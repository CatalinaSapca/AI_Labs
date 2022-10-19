package ML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    //--citirea datelor
    private static double[][]  loadData(String filename) throws IOException{
        double[][] data;
        List<String> lines = readFile(filename);

        if (lines == null)
            throw new IOException("Error!!!");

        lines.remove(0);

        data = new double[lines.size()][3];

        int i = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            data[i][0] = Double.parseDouble(parts[2]);
            data[i][1] = Double.parseDouble(parts[3]);
            data[i][2] = Double.parseDouble(parts[6]);
            i++;
        }

        return data;
    }

    private static List<String> readFile(String filename) {
        List<String> lines = null;

        Path path = Paths.get("data\\" + filename);
        if (!Files.exists(path))
            return null;

        try {
            lines = Files.lines(path).collect(Collectors.toList());
        } catch (IOException ignored) {
        }

        return lines;
    }

    public static void test(Result rManual, Result rTool){
        assert (rManual.getA()-rTool.getA() < 0.01);
        assert (rManual.getGdp()-rTool.getGdp() < 0.01);
        assert (rManual.getFreedom()-rTool.getFreedom() < 0.01);
    }

    public static void main(String[] args) throws IOException {
        double[][] data = loadData("happinessIndex2017.csv");
        Solve calculateSolution = new Solve(data);

        calculateSolution.prepareData(0.2);
        Result rManual = calculateSolution.manual();
        Result rTool  = calculateSolution.tool();


        System.out.println("\n");
        test(rManual, rTool);
    }
}