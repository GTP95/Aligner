package com.sequence;

public class GlobalAligner implements Aligner{
    String firstSequence;
    String secondSequence;
    Matrix matrix;
    int matchPoints;
    int substitutionPoints;
    int inDelPoints;
    boolean isAlignmentDone;

    public GlobalAligner(String firstSequence, String secondSequence) {
        this.firstSequence = "-".concat(firstSequence);
        this.secondSequence = "-".concat(secondSequence);
        this.matrix=Matrix.getInstance();
        this.matchPoints=1;
        this.substitutionPoints=0;
        this.inDelPoints=0;
        this.isAlignmentDone=false;
    }

    public GlobalAligner(String firstSequence, String secondSequence, int matchPoints, int substitutionPoints, int inDelPoints) {
        this.firstSequence = "-".concat(firstSequence);
        this.secondSequence = "-".concat(secondSequence);
        this.matchPoints = matchPoints;
        this.substitutionPoints = substitutionPoints;
        this.inDelPoints = inDelPoints;
        this.isAlignmentDone=false;
    }

    public void align(){
       // matrix.addCell(new Cell(0, null), 0, 0);    //initializes first cell of the matrix
        for(int row=0; row<secondSequence.length();row++){
            for(int column=0;column<firstSequence.length();column++){
                if(row==0){
                    matrix.addCell(new Cell(column*inDelPoints, null, row, column), row, column);
                    continue;
                }
                if(column==0){
                    matrix.addCell(new Cell(row*inDelPoints, null, row, column), row, column);
                    continue;
                }
                matrix.addCell(scoreFunction(row, column), row, column);
            }
        }
        this.isAlignmentDone=true;
    }

    @Override
    public int getAlignmentScore() throws AlignmentNotCompletedException{
        if(isAlignmentDone) return matrix.getCell(secondSequence.length()-1, firstSequence.length()-1).getValue();
        throw new AlignmentNotCompletedException();
    }

    @Override
    public void traceback() throws AlignmentNotCompletedException{    //starts from the last cell and reconstructs the alignment
        if(isAlignmentDone) {
            String firstSequence;
            String secondSequence;
        }
    }

    private Cell scoreFunction(int row, int column){
        int sxValue=matrix.getCell(row-1, column).getValue()+inDelPoints;
        int upValue=matrix.getCell(row, column-1).getValue()+inDelPoints;
        int upSxValue=matrix.getCell(row-1, column-1).getValue()+sigma(row, column);
        if(sxValue>=upValue){
            if(sxValue>=upSxValue) return new Cell(matrix.getCell(row-1, column).getValue()+inDelPoints, matrix.getCell(row-1, column), row, column);
            return new Cell(matrix.getCell(row-1, column-1).getValue()+sigma(row, column), matrix.getCell(row-1, column-1), row, column);
        }
        if(upValue>=upSxValue) return new Cell(matrix.getCell(row, column-1).getValue()+inDelPoints, matrix.getCell(row, column-1), row, column);
        return new Cell(matrix.getCell(row-1, column-1).getValue()+sigma(row, column), matrix.getCell(row-1, column-1), row, column);
    }

    private int sigma(int row, int column){
        if(firstSequence.charAt(column)==secondSequence.charAt(row)) return matchPoints;
        return substitutionPoints;
    }



}
