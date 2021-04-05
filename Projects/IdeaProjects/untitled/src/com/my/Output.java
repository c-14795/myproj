package  com.my;

import java.util.*;
class Output {
    public static double sumOfList(List<? extends Number> list) {
        double s = 0.0;
        for (Number n : list)
            s += n.doubleValue();
        return s;
    }
    public static void main(String args[]) {
        List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
        System.out.println(sumOfList(ld));
        int x;
        x = 10;
        x = x >> 1;
        System.out.println(x);
        boolean as = true;
        boolean bs= false;
        boolean cs = as ^ bs;
        System.out.println(!cs);
        int a = 1;
        int b = 2;
        int c;
        int d;
        c = ++b;
        d = a++;
        c++;
        b++;
        ++a;
        System.out.println(a + " " + b + " " + c);
        char array_variable [] = new char[10];
        for (int i = 0; i < 10; ++i) {
            array_variable[i] = 'i';
            System.out.print(array_variable[i] + "" );
            i++;
        }
        Queue<String> ax = new PriorityQueue<String>();
        TreeSet asd = null;
        List assd = null;
        HashSet sss = null;
        int arr[] = {10, 20, 30, 40, 50};
        for(int i=0; i < arr.length; i++)
        {
            System.out.print(" " + arr[i]);
        }

    }
}
