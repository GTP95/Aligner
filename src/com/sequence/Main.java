package com.sequence;

import java.util.Scanner;

public class Main {

    private static String sequence1;
    private static String sequence2;

    private static int max(int x, int y, int z){
        if(x>=y){
            if(x>=z) return x;
            return z;
        }
        return y;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
	    System.out.println("Enter first sequence");
        sequence1=scanner.next();
        System.out.println("Enter second sequence");
        sequence2=scanner.next();

        Aligner aligner=new GlobalAligner(sequence1, sequence2);
        aligner.align();
        try {
            System.out.println("Alignment score: " + aligner.getAlignmentScore());
        }
       catch (AlignmentNotCompletedException e){
            System.err.println("Alignment not yet finished");
       }

    }
}
