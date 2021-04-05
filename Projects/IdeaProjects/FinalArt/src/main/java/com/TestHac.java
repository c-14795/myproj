package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestHac {

    public static void main(String[] args) {
        List<Integer> a  = new ArrayList<Integer>();
        List<Integer> b  = new ArrayList<Integer>();


        for (Integer i : a)
        {
            if(b.contains(i))
            {
                a.subList(i,8);
                Collections.sort(a,Collections.reverseOrder());
                b.remove(b.indexOf(i));

                double d = 222d;

            }
        }
    }

}
