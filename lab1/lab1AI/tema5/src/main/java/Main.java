

/**
 * problema 5.
 */

public class Main {
    //test pentru prima varianta
    public static void testV1(){
        assert (Numbers.solve1(7, new int[]{1, 2, 3, 4, 5, 6, 6})==6);
        assert (Numbers.solve1(3, new int[]{1, 2, 2})==2);
        assert (Numbers.solve1(5, new int[]{1, 2, 3, 4, 4})==4);
        assert (Numbers.solve1(9, new int[]{1, 1, 2, 3, 5, 6, 4,7,8})==1);
    }

    //test pentru a doua varinata
    public static void testV2(){
        assert (Numbers.solve2(7, new int[]{1, 2, 3, 4, 5, 6, 6})==6);
        assert (Numbers.solve2(3, new int[]{1, 2, 2})==2);
        assert (Numbers.solve2(5, new int[]{1, 2, 3, 4, 4})==4);
        assert (Numbers.solve2(9, new int[]{1, 1, 2, 3, 5, 6, 4,7,8})==1);
    }

    //test pentru a treia varinata
    public static void testV3(){
        assert (Numbers.solve3(7, new int[]{1, 2, 3, 4, 5, 6, 6})==6);
        assert (Numbers.solve3(3, new int[]{1, 2, 2})==2);
        assert (Numbers.solve3(5, new int[]{1, 2, 3, 4, 4})==4);
        assert (Numbers.solve3(9, new int[]{1, 1, 2, 3, 5, 6, 4,7,8})==1);
    }

    public static void main(String[] args){
        testV1();
        testV2();
        testV3();
//       System.out.println(Numbers.solve1(7, new int[]{1, 2, 3, 4, 5, 6, 6}));
//       System.out.println(Numbers.solve2(7, new int[]{1, 2, 3, 4, 5, 6, 6}));
    }
}
