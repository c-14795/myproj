package com.test;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String s = "(" +
                "?=" +
                "(([^ac]*[ac]){2})*[^ac]*$)" +
                "?=(([^bd]*[bd]){2})*[^bd]*";

    }


    public static String print(String s)
    {
        return  s+"Hey i";
    }

    public static int add(int a,int b)
    {

        return (a+b+div(a,b))/2;
    }

    public static int div(int a,int b)
    {
        return (a+b)/2;
    }

    public static List<Integer> sortIntersect(List<Integer> f, List<Integer> m) {
        // Write your code here

        List<Integer> a = new ArrayList<Integer>();

        for(int i = 0; i <= f.size() ; i++)
        {
            if(m.contains(f.get(i)))
            {
                m.remove(m.lastIndexOf(f.get(i)));
                a.add(f.get(i));
            }
        }

        return a;
    }


}
