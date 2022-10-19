import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Words {
    /**
     * varianta 1-cu un HashMap in care retin cuvantul si numarul de aparitii
     */
    public static List<String> solve1(ArrayList<String> attr){
        Map<String,Integer> myMap=new HashMap<>();

        attr.forEach(el->{
            if(myMap.containsKey(el))
                myMap.put(el,2);
            else
                myMap.putIfAbsent(el,1);
        });

        List<String> result=new ArrayList<>();
        myMap.forEach((k,v)->{
            if(v==1)
                result.add(k);
        });
        return result;
    }

    public static int nrAparitii(String el, List<String> attr)
    {
        int nr=0;
        for (int i=0;i< attr.size();i++)
            if(attr.get(i).equals(el))
                nr=nr+1;

        return nr;
    }

    /**
     * varianta 2-pentru fiecare cuvant din lista data aflu numarul de aparitii
     * daca este 1 atunci adaug cuvantul la lista rezultat, altfel nu.
     */
    public static List<String> solve2(ArrayList<String> attr) {
        List<String> result=new ArrayList<>();

        attr.forEach(el -> {
            if (nrAparitii(el,attr)==1)
                result.add(el);
        });

        return result;
    }
}
