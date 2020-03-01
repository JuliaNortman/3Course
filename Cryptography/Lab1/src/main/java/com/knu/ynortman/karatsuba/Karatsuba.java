package com.knu.ynortman.karatsuba;

import java.math.BigInteger;

public class Karatsuba {
    public static BigInteger multiply(BigInteger x, BigInteger y) {
        if(x.compareTo(BigInteger.TEN) < 0 || y.compareTo(BigInteger.TEN) < 0) {
            return x.multiply(y);
        }
        int N = Math.min(x.bitLength(), y.bitLength());
        N = N/2;
        BigInteger a = x.shiftRight(N);
        BigInteger b = x.subtract(a.shiftLeft(N));
        BigInteger c = y.shiftRight(N);
        BigInteger d = y.subtract(c.shiftLeft(N));

        BigInteger ac = multiply(a, c);
        BigInteger bd = multiply(b, d);
        BigInteger abcd = multiply(a.add(b), c.add(d));

        return bd.add((abcd.subtract(ac).subtract(bd)).shiftLeft(N)).add(ac.shiftLeft(N*2));
    }
}
