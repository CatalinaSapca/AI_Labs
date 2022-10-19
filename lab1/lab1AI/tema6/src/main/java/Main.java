import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * problema 6.
 */

public class Main {
    //test pentru prima varianta
    public static void testV1(){
        assert (MElement.solve1(11, new int[]{1, 2, 3, 4, 4, 4, 4,4,3,3,3})==4);
        assert (MElement.solve1(6, new int[]{1, 2, 2,3,3,3})==3);
        assert (MElement.solve1(5, new int[]{1,1,1,1,6})==1);
        assert (MElement.solve1(9, new int[]{1,2,5,8,9,9,9,9,9})==9);
    }

    //test pentru a doua varinata
    public static void testV2(){
        assert (MElement.solve1(11, new int[]{1, 2, 3, 4, 4, 4, 4,4,3,3,3})==4);
        assert (MElement.solve1(6, new int[]{1, 2, 2,3,3,3})==3);
        assert (MElement.solve1(5, new int[]{1,1,1,1,6})==1);
        assert (MElement.solve1(9, new int[]{1,2,5,8,9,9,9,9,9})==9);
    }

    public static void main(String[] args){
        testV1();
        testV2();
    }
}

