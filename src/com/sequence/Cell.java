package com.sequence;

public class Cell {
    private int value;
    private Cell tracebackPointer;

    public Cell(int value, Cell tracebackPointer) {
        this.value = value;
        this.tracebackPointer = tracebackPointer;
    }

    public int getValue() {
        return value;
    }

    public Cell getTracebackPointer() {
        return tracebackPointer;
    }
}

