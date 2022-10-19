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
        Path p = Paths.get("data\\" + filename);
        //Path p = Path.of(filename);
        if (!Files.exists(p))
            return null;

        List<String> lines = null;

        try {
            lines = Files.lines(p).collect(Collectors.toList());
        } catch (IOException ignored) {
        }

        return lines;
    }

    public static void main(String[] args) throws IOException {
        double[][] data = loadData("happinessIndex2019.csv");
        Solve calculateSolution = new Solve(data);

//        me.ai.MatrixUtils.print(me.ai.MatrixUtils.inverse3(new double[][] {
//                {3, 0,1},
//                {0, 2, 0},
//                {0, 0, 1}
//        }));

        calculateSolution.prepareData(0.2);
        calculateSolution.manual().forEach(System.out::println);

        System.out.println("\n");
        calculateSolution.tool().forEach(System.out::println);
    }
}