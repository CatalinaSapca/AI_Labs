import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MElement {
    /**
     * varianta 1-
     * n,numbers
     * myMap- HashMap in care memorez numar din lista data si nr aparitii al acestuia in lista data
     * parcurg myMap si returnez cheia care are valoarea >= cu n/2
     * */
    public static int solve1(int n, int[] numbers){
        Map<Integer, Integer> myMap= new HashMap<>();
        
        for (int number : numbers) {
            if(myMap.get(number)!=null) {
                int v = myMap.get(number);
                myMap.put(number, v + 1);
            }
            else 
                myMap.putIfAbsent(number,1);
        }

        myMap.forEach((k,v)->{
            if(v>=n/2)
                numbers[0]=k;
        });
        return numbers[0];
    }

    /**
     * varianta 2-
     * n,numbers
     * v: vector caracteristic ce memoreaza nr de aparitii al fiecarui element din numbers
     * se parcurge numbers pentru a popula vectorul caracteristic v-->
     * daca numarul curent are deja un nr de aparitii >= n/2 -->acesta este dublura(nu este nevoie sa continuam)
     * am presupus ca vectorul dat are elemente de la 0 la 1000!!!
     */
    public static int solve2(int n, int[] numbers){
        int[] v=new int[1000];
        for (int i=0;i<=1000;i++)
            v[i]=0;

        for (int number : numbers)
            if(v[number]>=n/2)
                return number;
            else
                v[number] = v[number] + 1;

        return -1;
    }
}
