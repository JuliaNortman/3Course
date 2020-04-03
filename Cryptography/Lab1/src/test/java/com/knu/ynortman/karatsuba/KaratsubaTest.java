package com.knu.ynortman.karatsuba;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class KaratsubaTest {

    @Test
    public void multiply() {
        assertEquals(new BigInteger("258758084595"),
                Karatsuba.multiply(new BigInteger("177965"), new BigInteger("1453983")));

        assertEquals(new BigInteger("47252909852332"),
                Karatsuba.multiply(new BigInteger("4785413"), new BigInteger("9874364")));

        assertEquals(new BigInteger("2701562463559330350"),
                Karatsuba.multiply(new BigInteger("41258755654"), new BigInteger("65478525")));

        assertEquals(new BigInteger("4725525138607482976"),
                Karatsuba.multiply(new BigInteger("478546"), new BigInteger("9874756321456")));

        assertEquals(new BigInteger("4224787285471125857271"),
                Karatsuba.multiply(new BigInteger("774554465151"), new BigInteger("5454474121")));

        assertEquals(new BigInteger("769067847381145622295155"),
                Karatsuba.multiply(new BigInteger("78845157817541"), new BigInteger("9754154455")));

        assertEquals(new BigInteger("3570043760236748470998725"),
                Karatsuba.multiply(new BigInteger("54516565415"), new BigInteger("65485485614515")));
    }
}