import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Matrix {
//    public static void solveMatrix(int n, int m){
//        String fileName = "data/matrix.csv";
//
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String linie;
//            if((linie = br.readLine()) != null) {
//                List<String> attr = Arrays.asList(linie.split(" "));
//                n= Integer.parseInt(attr.get(0));
//                m= Integer.parseInt(attr.get(1));
//            }
//            int[][] matrix = new int[n][m];
//            for(int i=0;i<n;i++){
//                linie = br.readLine();
//                List<String>attr = Arrays.asList(linie.split(" "));
//                for(int j=0;j<m;j++){
//                    matrix[i][j]=Integer.parseInt(attr.get(j));
//                }
//            }
//            determineIndex1(n,m,matrix);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * varianta 1-mai eficienta
     * parcurg matricea pe coloane; pt fiecare coloana aflu linia care contine elementul 1--> acesta este raspunsul(nu este necesar sa continui)
     */
    public static int determineIndex1(int n,int m,int[][] matrix){
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++)
                if(matrix[j][i]==1) {
                    return j+1;
                }
        }
        return -1;
    }

    /**
     * varianta 2
     * parcurg matricea pe linii
     * decid daca linia curenta este o varinta mai buna decat cea mai buna anterioara
     * (trebuie sa parcurg pana la ultima linie pt a afla raspunsul corect)
     */
    public static int determineIndex2(int n,int m,int[][] matrix){
        int index=0;
        int minIndex=m+1;

        for(int i=0;i<n;i++){
            int j=0;
            while(j<m) {
                if (matrix[i][j] == 1 && j < minIndex) {
                    minIndex = j;
                    index = i + 1;
                    j = m + 2;
                } else
                    j = j + 1;
            }
        }

        return index;
    }
}
