package com.test;

import com.sequence.BWT;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BWTtest {

    @Test
    void bwt(){
        BWT bwt=new BWT();
        assertEquals(bwt.bwt("banana"), "annb$aa");
    }

}
