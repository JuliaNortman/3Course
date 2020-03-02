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

        /*assertEquals(new BigInteger("18"),
                Montgomery.multiplication(new BigInteger("147852369"), new BigInteger("321654987"),
                        new BigInteger("159")));*/
    }
}