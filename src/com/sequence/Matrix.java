package com.sequence;


import java.util.ArrayList;

public class Matrix {
    private static Matrix ourInstance = new Matrix();
    private ArrayList<ArrayList<Cell>> matrix;

    public static Matrix getInstance() {
        return ourInstance;
    }

    public void addCell(Cell cell, int row, int column){
        if(matrix.size()<=row) matrix.add(row, new ArrayList());
        matrix.get(row).add(column, cell);
    }

    public Cell getCell(int row, int column){
        return matrix.get(row).get(column);
    }

    public Cell getMaxCell(){   //Returns the cell that holds the maximum value stored in the matrix
        Cell cell=matrix.get(0).get(0);
        for(int row=0; row<matrix.size(); row++){
            for(int column=0; column<matrix.get(row).size(); column++){
                if(matrix.get(row).get(column).getValue()>cell.getValue()) cell=matrix.get(row).get(column);
            }
        }
        return cell;
    }

    private Matrix() {
        matrix=new ArrayList();
    }
}
