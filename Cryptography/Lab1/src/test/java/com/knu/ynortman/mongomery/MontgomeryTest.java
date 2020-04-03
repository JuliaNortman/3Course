package com.knu.ynortman.mongomery;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MontgomeryTest {

    @Test
    public void multiplication() {
        assertEquals(new BigInteger("9"),
                Montgomery.multiplication(
                        new BigInteger("123456"), new BigInteger("987654"), BigInteger.valueOf(15)));

        assertEquals(new BigInteger("8"),
                Montgomery.multiplication(new BigInteger("18"), new BigInteger("75"),
                        new BigInteger("11")));

        assertEquals(new BigInteger("1"),
                Montgomery.multiplication(
                        new BigInteger("123456"), new BigInteger("987654"), BigInteger.valueOf(13)));

        assertEquals(new BigInteger("10"),
                Montgomery.multiplication(
                        new BigInteger("65416861"), new BigInteger("354135453"), BigInteger.valueOf(17)));

        assertEquals(new BigInteger("81"),
                Montgomery.multiplication(
                        new BigInteger("6545145454"), new BigInteger("546454546"), BigInteger.valueOf(197)));

        assertEquals(new BigInteger("21"),
                Montgomery.multiplication(
                        new BigInteger("541645614"), new BigInteger("6845854735"), BigInteger.valueOf(51)));
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

        assertEquals(new BigInteger("1"),
                Montgomery.paw(new BigInteger("5161511"), new BigInteger("154"), BigInteger.valueOf(145)));

        assertEquals(new BigInteger("44"),
                Montgomery.paw(new BigInteger("154494"), new BigInteger("19"), BigInteger.valueOf(53)));
    }
}