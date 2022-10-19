package lab9;

import java.util.List;

public class Main {

    public static void test(Solution s1, Solution s2){
        assert (Math.abs(s1.accuracy- s2.accuracy)<0.05);
        assert (Math.abs(s1.precision- s2.precision)<0.05);
        assert (Math.abs(s1.recall- s2.recall)<0.05);
        assert (Math.abs(s1.loss- s2.loss)<0.5);

        System.out.println(s1.accuracy+" "+s2.accuracy);
        System.out.println(s1.recall+" "+s2.recall);
        System.out.println(s1.precision+" "+s2.precision);
        System.out.println(s1.loss+" "+s2.loss);
    }

    public static void main(String[] args) throws Exception {
        Solve solve = new Solve("iris.csv");

        //manual
        solve.init(0.2, 2000, 0.001);
        List<String> result = solve.test();
        result.forEach(System.out::println);

        System.out.println();
        System.out.println();
        System.out.println("--------------------------------------------------------------------------");
        //cu tool
        try {
            solve.tool();
        } catch (Exception e) {
            e.printStackTrace();
        }

        test(solve.manualSolution, solve.toolSolution);
    }
}
