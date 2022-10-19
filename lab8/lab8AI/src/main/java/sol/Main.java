package sol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static double[][] readDataFromFile(String fileName) throws IOException{
        Path p = Paths.get(".\\data\\" + fileName);
        if (!Files.exists(p))
            throw new IOException("File not found!");

        //each string from lines is a line from the given file
        List<String> lines = Files.lines(p).collect(Collectors.toList());
        System.out.println(lines.get(0).split(",")[2]+" "+ lines.get(0).split(",")[3]+" "+lines.get(0).split(",")[6]);
        //removing the headers
        lines.remove(0);

        double[][] data = new double[lines.size()][3];

        int i = 0;

        //data[i][0] contains Happiness Score
        //data[i][1] contains Economy
        //data[i][2] contains Freedom
        for (String line : lines) {
            String[] parts = line.split(",");
            data[i][0] = Double.parseDouble(parts[2]);
            data[i][1] = Double.parseDouble(parts[3]);
            data[i][2] = Double.parseDouble(parts[6]);
            i++;
        }

        return data;
    }

    public static void main(String[] args) throws IOException {
        double[][] data = readDataFromFile("happinessIndex2017.csv");

        Solve solve = new Solve(data);
        List<String> solution = solve.gradientDescent(0.2, 2, 2000, 0.01);

        //print solution
        solution.forEach(System.out::println);
    }
}






























// procTest = procent din date folosite pentru test = 20%
// nrVar = numar de variabile independente (aici sunt 2, gdp si freedom, dar merge si cu mai multe)
// epochs = numarul de iteratii, epoci
// learningrate = rata de invatare