package com.knu.ynortman.mongomery;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MontgomeryTest {

    @Test
    public void countR() {
        assertEquals(new BigInteger("16"), Montgomery.countR(new BigInteger("15")));
    }

    @Test
    public void multiplication() {
        assertEquals(new BigInteger("9"),
                Montgomery.multiplication(new BigInteger("123456"), new BigInteger("987654"), BigInteger.valueOf(15)));

        assertEquals(new BigInteger("8"),
                Montgomery.multiplication(new BigInteger("18"), new BigInteger("75"),
                        new BigInteger("11")));

        assertEquals(new BigInteger("1"),
                Montgomery.multiplication(new BigInteger("123456"), new BigInteger("987654"), BigInteger.valueOf(13)));

    }

    @Test
    public void paw() {
        assertEquals(new BigInteger("6"),
                Montgomery.paw(new BigInteger("123456"), new BigInteger("98"), BigInteger.valueOf(15)));

        assertEquals(new BigInteger("10"),
                Montgomery.paw(new BigInteger("18"), new BigInteger("75"),
                        new BigInteger("11")));

        assertEquals(new BigInteger("1"),
                Montgomery.paw(new BigInteger("1457"), new BigInteger("74"), BigInteger.valueOf(7)));

        assertEquals(new BigInteger("10"),
                Montgomery.paw(new BigInteger("45885"), new BigInteger("74"), BigInteger.valueOf(13)));

    }
}