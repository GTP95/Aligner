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

    private Matrix() {
        matrix=new ArrayList();
    }
}
