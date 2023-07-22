package com.github.kirillKarlson.FirstTryin;

import java.util.Scanner;

public class FirstTryin {


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        int b = scan.nextInt();


        int c = mnozh(a,b);




    }

    public static int mnozh(int a, int b){
        return a*b;
    }

}
