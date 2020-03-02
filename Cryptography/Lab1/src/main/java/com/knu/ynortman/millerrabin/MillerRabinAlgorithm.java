package com.knu.ynortman.millerrabin;

import java.math.BigInteger;

import static com.knu.ynortman.fermatprimality.FermatPrimality.BigIntegerRandom;

public class MillerRabinAlgorithm {
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static boolean isPrime(BigInteger n, int iteration) {
        if (n.compareTo(BigInteger.ZERO) == 0 || n.compareTo(BigInteger.ONE) == 0) {
            return false;
        }
        if (n.compareTo(TWO) == 0) {
            return true;
        }
        if (n.mod(TWO).compareTo(BigInteger.ZERO) == 0){
            return false;
        }

        BigInteger t = n.subtract(BigInteger.ONE);
        int s = 0;
        while (t.mod(TWO).compareTo(BigInteger.ZERO) == 0){
            t = t.divide(TWO);
            s++;
        }

        for(int i = 0; i < iteration; ++i) {
            BigInteger a = BigIntegerRandom(TWO, n.subtract(TWO));
            BigInteger x = a.modPow(t, n);
            if(x.compareTo(BigInteger.ONE) == 0 || x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                continue;
            }
            boolean nextIteration = false;
            for(int j = 0; j < s-1; ++j) {
                x = x.multiply(x).mod(n);
                if(x.compareTo(BigInteger.ONE) == 0) {
                    return false;
                }
                if(x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                    nextIteration = true;
                    break;
                }
            }
            if(!nextIteration) {
                return false;
            }
        }
        return true;
    }
}
