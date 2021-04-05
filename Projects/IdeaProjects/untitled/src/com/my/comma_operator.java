package com.my;

class comma_operator {
    public static void main(String args[]) {
        tes(5, new int[]{1, 2, 3});
    }


    private static int calLeft(int[] f_number, int i) {
        int sum = 0;
        for (int j = i; j >= 0; j--) {
            sum += f_number[j];
        }
        return sum;
    }


    private static int calRight(int[] f_number, int i) {
        int sum = 0;
        for (int j = i; j < f_number.length; j++) {
            sum += f_number[j];
        }

        return sum;
    }

    private static int tes(long input1, int[] input2) {
        long current_ans = -1;
        int current_city = -1;
        long prev_ans = 0;
        long left_sum = 0;
        long right_sum = calRight(input2, 0);
        long total_sum = right_sum;
        for (int i = 0; i < input2.length; i++) {
            //Math.max()

            if (!(i == 0 || i == input2.length - 1)) {
                //current_ans = Math.max(calLeft(f_number, i), calRight(f_number, i));
                left_sum += input2[i];
                right_sum -= input2[i];
                current_ans = Math.max(left_sum, right_sum);


            } else if (i == 0)

            {
                current_ans = input2[input2.length - 1];
                prev_ans = current_ans;
                current_city = i;
            } else {
                current_ans = total_sum;

            }
            System.out.println("_____________if");
            System.out.println(current_ans + " current_ans");
            System.out.println(prev_ans + " prev_ans");
            System.out.println(current_city + " prev_city");
            System.out.println(i + " current_city");
            System.out.println("_______________________________if_");

            if (current_ans > input1 && prev_ans > input1) {

                if (prev_ans < current_ans) {
                    current_ans = prev_ans;

                }
                else{
                    current_city = i;
                }


            }
            else if (current_ans > input1 && current_city == 0 )
            {
                current_city = i;
            }


            System.out.println(current_ans + " current_ans");
            System.out.println(prev_ans + " prev_ans");
            System.out.println(current_city + " prev_city");
            System.out.println(i + " current_city");

            prev_ans = current_ans;


        }
        System.out.println(current_ans > input1 ? current_city+1 : -1);
        return Math.toIntExact(current_ans > input1 ? current_ans : -1);

    }
}



    /*There are N cities in a kingdom. These cities are aligned in a row.
     So, each city has two neighbours except first and last city.
     Each city has an F-number which denotes the maximum cities he can ask for help in case of an attack.
      Each F-number lies between 1 to N.
      Two cities cannot have same F-number.
      The Power of a city c is the sum of F-numbers of the cities that c asked for help.
      In case of an attack, to save time, representative of the city will go to the first city he wants help from and
      ask the city to propagate the message to its right neighbour.
      Right neighbour will propagate the message to its right neighbour and so on.
      This process will stop when the attacked city gets the help form as many cities as it wants.
      It is obvious that a city will try to get maximum power.

Now a neighbour kingdom with a collective power K has attacked the kingdom.
In this attack a city will survive if its power is greater than the power of the attacking kingdom(K)
otherwise the city will be destroyed.
Now the king wants to prepare a plan to protect the kingdom from this threat. So he hired you.
 King wants to know which city with minimum F-number can survive. So help the king to save the kingdom.

INPUT SPECIFICATION
Your function contains two arguments- An integer K denoting the collective power of the attacking kingdom
and an One dimensional Integer array of Size N in which ith element denotes the F-number of the ith city.
First line of input contains an Integer K.(1<=K<=10^12)
Second line of input contains an Integer N denoting the size of Array. (1<=N<=10^5)
Next N lines of input contains a single integer from 1 to N.

OUTPUT SPECIFICATION
You must return smallest F-number of the city that can survive. Return -1 if no city can survive.

EXAMPLES
Sample Test Case 1-
Input
5
3
1
2
3
4
Output
3

Explanation
maximum power of the cities will be as following-
1st city- max(1,2,3,4,5)=5
2nd city- max((1+2),(2+3+4+5)) = 14
3rd city- max((1+2+3),(3+4+5)) = 12
4th city- max((1+2+3+4),max(4+5)) = 10
5th city- 1+2+3+4+5 = 15
Power of the first two cities is less than or equal to the collective power of the attacking kingdom(5).
 So, Both these cities will be destroyed. Only city remaining will be 3rd city.*/


