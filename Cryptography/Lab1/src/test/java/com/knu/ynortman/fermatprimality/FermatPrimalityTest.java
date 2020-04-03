package com.knu.ynortman.fermatprimality;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class FermatPrimalityTest {
    @Test
    public void test() {
        assertTrue(FermatPrimality.test(new BigInteger("307169"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("45787669"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("407153"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("65654749"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("643439"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("715549"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("715817"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("756673"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("1248715577"), 300));
        assertTrue(FermatPrimality.test(new BigInteger("12315271"), 300));
    }

}