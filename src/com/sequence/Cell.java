package com.sequence;

public class Cell {
    private int value;
    private Cell tracebackPointer;  //Holds a reference to the cell that stores the value used to compute this cell's value
    private int row;        //coordinates of the cell inside the matrix.
    private int column;    //This information is stored here to permit faster access than having to scan the matrix to find out the coordinates of a given cell

    public Cell(int value, Cell tracebackPointer, int row, int column) {
        this.value = value;
        this.tracebackPointer = tracebackPointer;
        this.row=row;
        this.column=column;
    }

    public int getValue() {
        return value;
    }

    public Cell getTracebackPointer() {
        return tracebackPointer;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}

