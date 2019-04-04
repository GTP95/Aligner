package com.sequence;

public interface Aligner {
    public void align();
    public int getAlignmentScore() throws AlignmentNotCompletedException;
    //Cell scoreFunction();

}
