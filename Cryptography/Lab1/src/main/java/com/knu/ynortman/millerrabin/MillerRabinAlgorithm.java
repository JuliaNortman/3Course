package com.knu.ynortman.millerrabin;

import java.math.BigInteger;

import static com.knu.ynortman.fermatprimality.FermatPrimality.BigIntegerRandom;

public class MillerRabinAlgorithm {
    public static boolean isPrime(BigInteger n, int iteration) {
        if (n.compareTo(BigInteger.ZERO) == 0 || n.compareTo(BigInteger.ONE) == 0) {
            return false;
        }
        if (n.compareTo(BigInteger.TWO) == 0) {
            return true;
        }
        if (n.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0){
            return false;
        }

        BigInteger t = n.subtract(BigInteger.ONE);
        int s = 0;
        while (t.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0){
            t = t.divide(BigInteger.TWO);
            s++;
        }

        for(int i = 0; i < iteration; ++i) {
            BigInteger a = BigIntegerRandom(BigInteger.TWO, n.subtract(BigInteger.TWO));
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
