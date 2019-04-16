package com.sequence;

import java.util.Scanner;

public class Main {

    private static String sequence1;
    private static String sequence2;

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
	    System.out.println("Enter first sequence");
        sequence1=scanner.next();
        System.out.println("Enter second sequence");
        sequence2=scanner.next();

        Aligner aligner=new LocalAligner(sequence1, sequence2);
        aligner.align();
        try {
            System.out.println("Alignment score: " + aligner.getAlignmentScore());
            aligner.traceback();
        }
       catch (AlignmentNotCompletedException e){
            System.err.println("Alignment not yet finished");
       }
        catch (WrongAlignmentException e){
            System.err.println("Something went wrong while trying to align the two strings! Please report this error and the strings you where trying to align");
            System.err.println("Additional debug infos:");
            e.printInfo();
        }

    }
}
