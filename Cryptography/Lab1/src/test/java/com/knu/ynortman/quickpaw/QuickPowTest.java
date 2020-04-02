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

    }
}