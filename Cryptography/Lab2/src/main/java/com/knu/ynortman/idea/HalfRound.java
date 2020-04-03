package com.knu.ynortman.idea;

import com.knu.ynortman.bits.AdditionModuloOperator;
import com.knu.ynortman.bits.BitArray;
import com.knu.ynortman.bits.MultiplicationModuloOperator;

public class HalfRound {
    public TextBlock encrypt(TextBlock block, BitArray k1, BitArray k2, BitArray k3, BitArray k4) {
        if (    k1.size() != 16 ||
                k2.size() != 16 ||
                k3.size() != 16 ||
                k4.size() != 16 ) {
            throw new IllegalArgumentException();
        }

        TextBlock resultBlock = block.cloneDeep();

        AdditionModuloOperator additionModuloOperator = new AdditionModuloOperator();
        MultiplicationModuloOperator multiplicationModuloOperator = new MultiplicationModuloOperator();

        BitArray[] block16bits = resultBlock.split16();
        BitArray a = block16bits[0];
        BitArray b = block16bits[2]; //swapped
        BitArray c = block16bits[1]; //swapped
        BitArray d = block16bits[3];

        a = multiplicationModuloOperator.combine(a, k1);
        b = additionModuloOperator.combine(b, k2);
        c = additionModuloOperator.combine(c, k3);
        d = multiplicationModuloOperator.combine(d, k4);

        return new TextBlock(a, b, c, d);
    }
}