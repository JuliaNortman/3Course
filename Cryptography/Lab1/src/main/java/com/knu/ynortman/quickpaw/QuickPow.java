package com.knu.ynortman.quickpaw;

import java.math.BigInteger;

/**
 * link http://e-maxx.ru/algo/export_binary_pow
 * link https://prog-cpp.ru/pow-mod/
 *
 */

public class QuickPow {
    public static BigInteger paw(BigInteger x, BigInteger y, BigInteger n) {
        /*if(y.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ONE;
        }*/

        BigInteger res = BigInteger.ONE;
        while (y.compareTo(BigInteger.ZERO) > 0) {
            if(y.and(BigInteger.ONE).compareTo(BigInteger.ZERO) > 0) {
                res = res.multiply(x);
            }
            x = x.multiply(x);
            y = y.shiftRight(1);
        }
        return res.mod(n);
    }
}
