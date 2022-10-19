import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Numbers {
    /**
     * varianta 1-
     * n,numbers
     * v: vector caracteristic ce memoreaza nr de aparitii al fiecarui element din numbers
     * se parcurge v si cand se gaseste o dublura, se returneaza pozitia pe care s-a gasit
     */
    public static int solve1(int n, int[] numbers){
        int[] v=new int[100];

        for (int i=0;i<=n;i++)
            v[i]=0;

        for (int number : numbers)
            v[number] = v[number] + 1;

        for(int i=1;i<n;i++)
            if(v[i]==2)
                return i;

        return -1;
    }

    /**
     * varianta 2-
     * n,numbers
     * v: vector caracteristic ce memoreaza nr de aparitii al fiecarui element din numbers
     * se parcurge numbers pentru a popula vectorul caracteristic v-->
     * daca numarul curent in numbers se afla deja in caracteristic-->acesta este dublura(nu este nevoie sa continuam)
     */
    public static int solve2(int n, int[] numbers){
        int[] v=new int[100];

        for (int i=0;i<=n;i++)
            v[i]=0;

        for (int number : numbers)
            if(v[number]==1)
                return number;
            else
                v[number] = v[number] + 1;

        return -1;
    }

    /**
     * varianta 2-
     * n,numbers
     * rezolvarea se bazeaza pe suma lui Gauss
     */
    public static int solve3(int n, int[] numbers){
        int Sum=(n*(n-1))/2;
        for (int number : numbers)
            Sum=Sum-number;

        return Sum*(-1);
    }
}
