package com.knu.ynortman.karatsuba;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        System.out.println(Karatsuba.multiply(BigInteger.valueOf(12345678), BigInteger.valueOf(98765432)));
    }
}
