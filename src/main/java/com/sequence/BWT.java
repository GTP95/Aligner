package com.sequence;

//implementation of the Burrows-Wheeler transform. Uses '$' as terminator, make sure your alphabet doesn't use it
import java.util.ArrayList;

public class BWT {
    private ArrayList<String> table;
    private static char terminator='$';

    public BWT() {
        this.table=new ArrayList<String>();
    }

    public String bwt(String string){
        char temp;
        string=string+terminator;
        String bwt=new String();
        for(int i=0;i<string.length();i++) {
            table.add(string);
            temp=string.charAt(string.length()-1); //get last character of the string
            string=string.substring(0, string.length()-1);  //removes the last character from the string
            string=temp+string;
        }

        java.util.Collections.sort(table);

        for(int i=0;i<string.length();i++){
            bwt=table.get(i).charAt(string.length()-1)+bwt; //constructs the BWT taking the last column of table
        }
        return bwt;
    }
}

