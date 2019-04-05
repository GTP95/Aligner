package com.sequence;

public class WrongAlignmentException extends Exception {

    Cell currentCell;
    Cell currentsCellTraceback;

    public WrongAlignmentException(Cell currentCell, Cell currentCellsTraceback){
            this.currentCell=currentCell;
            this.currentsCellTraceback=currentCellsTraceback;
    }

    public void printInfo(){
        System.err.println("Current cell: "+currentCell.toString());
        System.err.println("Current cell's traceback: "+currentsCellTraceback.toString());
    }
}
