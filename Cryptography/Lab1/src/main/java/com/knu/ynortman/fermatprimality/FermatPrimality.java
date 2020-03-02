package com.knu.ynortman.fermatprimality;

import java.math.BigInteger;
import java.util.Random;

public class FermatPrimality {
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static boolean test(BigInteger p, int iteration) {
        if(p.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }
        if(p.compareTo(TWO) == 0) {
            return true;
        }
        if(p.mod(TWO).compareTo(BigInteger.ZERO) == 0) {
            return false;
        }
        for(int i = 0; i < iteration; ++i) {
            BigInteger a = BigIntegerRandom(TWO, p);
            if(!modPow(a, p.subtract(BigInteger.ONE), p).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    public static BigInteger modPow(BigInteger num, BigInteger pow, BigInteger mod) {
        BigInteger res = BigInteger.ONE;
        num = num.mod(mod);
        while(pow.compareTo(BigInteger.ZERO) > 0) {
            if(pow.and(BigInteger.ONE).equals(BigInteger.ONE)) {
                res = res.multiply(num).mod(mod);
            }
            pow = pow.shiftRight(1);
            num = num.multiply(num).mod(mod);
        }
        return res;
    }

    public static BigInteger BigIntegerRandom(BigInteger min, BigInteger max) {
        BigInteger bigInteger1 = max.subtract(min);
        Random rnd = new Random();
        int maxNumBitLength = max.bitLength();

        BigInteger aRandomBigInt;

        aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
        if (aRandomBigInt.compareTo(min) < 0) {
            aRandomBigInt = aRandomBigInt.add(min);
        }
        if (aRandomBigInt.compareTo(max) >= 0) {
            aRandomBigInt = aRandomBigInt.mod(bigInteger1).add(min);
        }
        return aRandomBigInt;
    }
}
