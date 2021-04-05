package com.my;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Character.getNumericValue;

class Modulus {
    public static void main(String args[]) {

        System.out.println(convertToBinary(0));
        Scanner sc = new Scanner(System.in);
        int no_of_rounds = sc.nextInt();
        char[] binary = null;
        int number;
        for (int i = 0; i < no_of_rounds; i++) {
            number = sc.nextInt();
            binary = convertToBinary(number).toCharArray();

            for (int j = 0; j < binary.length; j++) {

                if (j != 0)
                    binary[j - 1] = binary[j] == binary[j - 1] ? toggle0Or1(binary[j - 1]) : binary[j - 1];
                if (j != binary.length - 1)
                    binary[j + 1] = binary[j] == binary[j + 1] ? toggle0Or1(binary[j + 1]) : binary[j + 1];

                binary[j] = toggle0Or1(binary[j]);
                System.out.println(Arrays.toString(binary));
            }
            System.out.println(decideWinner(binary, number));

        }


    }


    static String convertToBinary(int d) {


        return d != 0 ? convertToBinary(d / 2) + "" + d % 2 : "";
    }

    static char toggle0Or1(char c) {
        return c == '0' ? '1' : '0';
    }

    static char decideWinner(char[] binary, int value) {
        int number = 0;
        for (int i = binary.length - 1; i >= 0; i--) {
            number += getNumericValue(binary[i]) * Math.pow(2, binary.length - 1 - i);

        }

        return (value == number || value == number + 1 || value == number - 1) && binary.length % 2 == 0 ? 'Y' : 'X';

    }
}