package com.knu.ynortman.extendedeuclid;

import java.math.BigInteger;

public class ExtendedEuclid {
    public static class Result {
        private BigInteger firstCoef;
        private BigInteger secondCoef;
        private BigInteger gcd;

        public Result(BigInteger residue, BigInteger firstCoef, BigInteger secondCoef) {
            this.firstCoef = firstCoef;
            this.secondCoef = secondCoef;
            this.gcd = residue;
        }

        public BigInteger getFirstCoef() {
            return firstCoef;
        }

        public BigInteger getSecondCoef() {
            return secondCoef;
        }

        public BigInteger getGcd() {
            return gcd;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "firstCoef=" + firstCoef +
                    ", secondCoef=" + secondCoef +
                    ", gcd=" + gcd +
                    '}';
        }
    }

    public static Result extendedEuclid(BigInteger a, BigInteger b) {
        if(b.compareTo(BigInteger.ZERO) == 0) {
            return new Result(a, BigInteger.ONE, BigInteger.ZERO);
        }
        Result r = extendedEuclid(b, a.mod(b));
        BigInteger x = r.getSecondCoef();
        BigInteger y = r.getFirstCoef().subtract(a.divide(b).multiply(x));
        return new Result(r.getGcd(), x, y);
    }
}
