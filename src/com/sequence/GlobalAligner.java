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
    public void traceback() throws AlignmentNotCompletedException, WrongAlignmentException{    //starts from the last cell and reconstructs the alignment
        if(isAlignmentDone) {
            String firstSequence=new String();
            String secondSequence=new String();
            Cell currentCell=matrix.getCell(this.secondSequence.length()-1, this.firstSequence.length()-1);
            while(!(currentCell.getRow()==0 || currentCell.getColumn()==0)){

                if(currentCell.getRow()==currentCell.getTracebackPointer().getRow()){   //the value of the cell has been calculated from the cell on it's left
                    secondSequence=this.secondSequence.charAt(currentCell.getRow())+secondSequence;
                    firstSequence="-".concat(firstSequence);    //residue aligned with a gap
                }

               else if(currentCell.getColumn()==currentCell.getTracebackPointer().getColumn()){ //the value of the cell has been calculated from the cell above
                   firstSequence=this.firstSequence.charAt(currentCell.getColumn())+firstSequence;
                   secondSequence="-".concat(secondSequence);   //residue aligned with a gap
                }

               else if(currentCell.getRow()-1==currentCell.getTracebackPointer().getRow() && currentCell.getColumn()-1==currentCell.getTracebackPointer().getColumn()){
                   firstSequence=this.firstSequence.charAt(currentCell.getColumn())+firstSequence;
                   secondSequence=this.secondSequence.charAt(currentCell.getRow())+secondSequence;  //two residues aligned together
                }

               else throw new WrongAlignmentException(currentCell, currentCell.getTracebackPointer());

               currentCell=currentCell.getTracebackPointer();

            }

            System.out.println(firstSequence);
            System.out.println(secondSequence);
        }

       else throw new AlignmentNotCompletedException();
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
