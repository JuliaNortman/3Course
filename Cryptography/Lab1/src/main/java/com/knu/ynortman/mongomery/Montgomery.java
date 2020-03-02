package com.knu.ynortman.mongomery;

import com.knu.ynortman.extendedeuclid.ExtendedEuclid;
import com.knu.ynortman.karatsuba.Karatsuba;

import java.math.BigInteger;

public class Montgomery {

    public static BigInteger countR(BigInteger n) {
        BigInteger r = BigInteger.ONE;
        return r.shiftLeft(n.bitLength());
    }

    public static BigInteger multiplication(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger r = countR(n);
        BigInteger a1 = a.shiftLeft(r.bitLength()-1).mod(n);
        BigInteger b1 = b.shiftLeft(r.bitLength()-1).mod(n);
        System.out.println("a1="+a1);
        System.out.println("b1="+b1);
        BigInteger t = Karatsuba.multiply(a1, b1);
        System.out.println("t="+t);
        ExtendedEuclid.Result eeRes = ExtendedEuclid.extendedEuclid(r, n);
        if(eeRes.getGcd().compareTo(BigInteger.ONE) != 0) {
            throw new ArithmeticException("N and R are not prime numbers");
        }
        BigInteger n1 = eeRes.getSecondCoef().multiply(BigInteger.valueOf(-1));
        System.out.println("n1"+n1);
        BigInteger residue = Karatsuba.multiply(t, n1).subtract(Karatsuba.multiply(t, n1)
                .shiftRight(r.bitLength()-1).shiftLeft(r.bitLength()-1));
        System.out.println("residue="+residue);
        BigInteger u = t.add(Karatsuba.multiply(residue, n)).shiftRight(r.bitLength()-1);
        System.out.println("u="+u);
        while(u.compareTo(n) >= 0) {
            u = u.subtract(n);
        }
        return u;

    }
}
