import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * problema 2.
 */

public class Main {
    //test pentru prima varianta
    public static void testV1() {
        assert (Math.round(EuclidianDistance.distance1(3,4,7,8)*100.0)/100.0==5.66);
        assert (Math.round(EuclidianDistance.distance1(-3,4,-9,8)*100.0)/100.0==7.21);
        assert (Math.round(EuclidianDistance.distance1(-1,-4,2,7)*100.0)/100.0==11.40);
    }
    //test pentru a doua varinata
    public static void testV2(){
        assert (Math.round(EuclidianDistance.distance1(3,4,7,8)*100.0)/100.0==5.66);
        assert (Math.round(EuclidianDistance.distance1(-3,4,-9,8)*100.0)/100.0==7.21);
        assert (Math.round(EuclidianDistance.distance1(-1,-4,2,7)*100.0)/100.0==11.40);
    }
    public static void main(String[] args){
//        int x1=0,y1=0,x2=0,y2=0;
//        String fileName = "C:\\Users\\Admin\\Desktop\\tema2\\data\\data.csv";
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String linie;
//            if((linie = br.readLine()) != null) {
//                List<String> attr = Arrays.asList(linie.split(","));
//                x1= Integer.parseInt(attr.get(0));
//                y1= Integer.parseInt(attr.get(1));
//            }
//            if((linie = br.readLine()) != null) {
//                List<String> attr = Arrays.asList(linie.split(","));
//                x2= Integer.parseInt(attr.get(0));
//                y2= Integer.parseInt(attr.get(1));
//            }
//
//            System.out.println(Math.round(EuclidianDistance.distance1(x1, y1, x2, y2)*100000.0)/100000.0);
//            System.out.println(EuclidianDistance.distance2(x1, y1, x2, y2));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        testV1();
        testV2();
    }
}