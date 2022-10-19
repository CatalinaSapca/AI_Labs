

/**
 * problema 10.
 */

public class Main {
    public static void testV1(){
        int mat[][] = { { 0, 1, 1, 1 },
                { 0, 0, 0, 0 },
                { 0, 0, 1, 1 } };
        assert (Matrix.determineIndex1(3,4,mat)==1);

        mat= new int[][]{{0, 1, 1, 1},
                {0, 0, 0, 0},
                {1, 1, 1, 1}};
        assert (Matrix.determineIndex1(3,4,mat)==3);

        mat= new int[][]{{0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}};
        assert (Matrix.determineIndex1(2,5,mat)==2);
    }

    public static void testV2(){
        int mat[][] = { { 0, 1, 1, 1 },
                { 0, 0, 0, 0 },
                { 0, 0, 1, 1 } };
        assert (Matrix.determineIndex2(3,4,mat)==1);

        mat= new int[][]{{0, 1, 1, 1},
                {0, 0, 0, 0},
                {1, 1, 1, 1}};
        assert (Matrix.determineIndex2(3,4,mat)==3);

        mat= new int[][]{{0, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}};
        assert (Matrix.determineIndex2(2,5,mat)==2);
    }

    public static void main(String[] args){
        testV1();
        testV2();
    }
}
