package com.knu.ynortman.quickpaw;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class QuickPowTest {

    @Test
    public void paw() {
        assertEquals(new BigInteger("6"),
                QuickPow.paw(new BigInteger("123456"), new BigInteger("98"), BigInteger.valueOf(15)));

        assertEquals(new BigInteger("10"),
                QuickPow.paw(new BigInteger("18"), new BigInteger("75"),
                        new BigInteger("11")));

        assertEquals(new BigInteger("1"),
                QuickPow.paw(new BigInteger("1457"), new BigInteger("74"), BigInteger.valueOf(7)));

        assertEquals(new BigInteger("12"),
                QuickPow.paw(new BigInteger("45885"), new BigInteger("74"), BigInteger.valueOf(13)));

        assertEquals(new BigInteger("1"),
                QuickPow.paw(new BigInteger("5161511"), new BigInteger("154"), BigInteger.valueOf(145)));

        assertEquals(new BigInteger("16"),
                QuickPow.paw(new BigInteger("41516146"), new BigInteger("35"), BigInteger.valueOf(19)));

        assertEquals(new BigInteger("52"),
                QuickPow.paw(new BigInteger("154494"), new BigInteger("19"), BigInteger.valueOf(53)));

        assertEquals(new BigInteger("51"),
                QuickPow.paw(new BigInteger("154614564"), new BigInteger("613"), BigInteger.valueOf(93)));

    }
}