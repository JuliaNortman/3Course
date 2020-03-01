package com.knu.ynortman.extendedeuclid;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class ExtendedEuclidTest {

    @Test
    void extendedEuclidTests() {
        ExtendedEuclid.Result r = ExtendedEuclid.extendedEuclid(new BigInteger("1235478"), new BigInteger("477965423"));
        assertEquals(new ExtendedEuclid.Result(BigInteger.ONE, new BigInteger("93485"), new BigInteger("-36166243")), r);
        r = ExtendedEuclid.extendedEuclid(new BigInteger("145675254"), new BigInteger("1234485"));
        assertEquals(new ExtendedEuclid.Result(BigInteger.valueOf(3), new BigInteger("93652"), new BigInteger("-11051393")), r);
        r = ExtendedEuclid.extendedEuclid(new BigInteger("7458512"), new BigInteger("354182045942"));
        assertEquals(new ExtendedEuclid.Result(BigInteger.valueOf(2), new BigInteger("-1116485"), new BigInteger("53018476281")), r);

    }
}