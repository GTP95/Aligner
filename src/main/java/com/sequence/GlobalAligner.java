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
        this.matrix=Matrix.getInstance();
    }

    public void align(){
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
            Cell currentCell=matrix.getCell(this.secondSequence.length()-1, this.firstSequence.length()-1); //Starts from the last cell in the matrix to reconstruct the alignment
            while(!(currentCell.getRow()==0 || currentCell.getColumn()==0)){

                if(currentCell.getRow()==currentCell.getTracebackPointer().getRow()){   //the value of the cell has been calculated from the cell on it's left
                    firstSequence=this.firstSequence.charAt(currentCell.getColumn())+firstSequence;
                    secondSequence="-".concat(secondSequence);    //residue aligned with a gap
                }

               else if(currentCell.getColumn()==currentCell.getTracebackPointer().getColumn()){ //the value of the cell has been calculated from the cell above
                   secondSequence=this.secondSequence.charAt(currentCell.getRow())+secondSequence;  //in case of bug try to switch getRow() with getColumn()
                   firstSequence="-".concat(firstSequence);   //residue aligned with a gap
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

    private Cell scoreFunction(int row, int column){    //written to give precedence to match when multiple alignment choices give the same score
        int sxValue=matrix.getCell(row, column-1).getValue()+inDelPoints;
        int upValue=matrix.getCell(row-1, column).getValue()+inDelPoints;
        int upSxValue=matrix.getCell(row-1, column-1).getValue()+sigma(row, column);

        if(upSxValue>=upValue){
            if(upSxValue>=sxValue) return new Cell(matrix.getCell(row-1, column-1).getValue()+sigma(row, column), matrix.getCell(row-1, column-1), row, column);
            return new Cell(matrix.getCell(row, column-1).getValue()+inDelPoints, matrix.getCell(row, column-1), row, column);
        }
        if(upValue>=sxValue) return new Cell(matrix.getCell(row-1, column).getValue()+inDelPoints, matrix.getCell(row-1, column), row, column);
        return new Cell(matrix.getCell(row, column-1).getValue()+inDelPoints, matrix.getCell(row, column-1), row, column);
        
    }

    private int sigma(int row, int column){
        if(firstSequence.charAt(column)==secondSequence.charAt(row)) return matchPoints;
        return substitutionPoints;
    }



}
