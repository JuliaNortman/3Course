package com.knu.ynortman.millerrabin;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        System.out.println(MillerRabinAlgorithm.isPrime(BigInteger.valueOf(5510389), 300));
    }
}
