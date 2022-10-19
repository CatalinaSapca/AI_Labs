import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * problema 4.
 */

public class Main {
    //test pentru prima varianta
    public static void testV1() {
        assert (Words.solve1(new ArrayList<String>(Arrays.asList("ana", "ana", "mere"))).equals(new ArrayList<String>(Collections.singletonList("mere"))));
        assert (Words.solve1(new ArrayList<String>(Arrays.asList("ana", "ana", "mere", "are", "are", "pere"))).equals(new ArrayList<String>(Arrays.asList("mere", "pere"))));
        assert (Words.solve1(new ArrayList<String>(Arrays.asList("ana", "ana", "mere", "are", "pere"))).size()==3);
        assert (Words.solve1(new ArrayList<String>(Arrays.asList("ana", "mere", "are", "pere"))).size()==4);
    }
    //test pentru a doua varinata
    public static void testV2(){
        assert (Words.solve2(new ArrayList<String>(Arrays.asList("ana", "ana", "mere"))).equals(new ArrayList<String>(Collections.singletonList("mere"))));
        assert (Words.solve2(new ArrayList<String>(Arrays.asList("ana", "ana", "mere", "are", "are", "pere"))).equals(new ArrayList<String>(Arrays.asList("mere", "pere"))));
        assert (Words.solve2(new ArrayList<String>(Arrays.asList("ana", "ana", "mere", "are", "pere"))).equals(new ArrayList<String>(Arrays.asList("mere", "are", "pere"))));
        assert (Words.solve2(new ArrayList<String>(Arrays.asList("ana", "mere", "are", "pere"))).equals(new ArrayList<String>(Arrays.asList("ana", "mere", "are", "pere"))));

    }
    public static void main(String[] args){
        testV1();
        testV2();
    }
}

