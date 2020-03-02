package com.knu.ynortman;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class IdeaTest {

    @Test
    public void getKey() throws IOException {
        Idea idea = new Idea("mytext", new int[]{147, 584, 6, 10245, 74458, 12, 654, 74985});
        byte[] expected = {
                (byte) 01101101,
                (byte) 01111001,
                (byte) 01110100,
                (byte) 01100101,
                (byte) 01111000,
                (byte) 01110100
        };
        assertArrayEquals(expected, idea.getText());
    }

    @Test
    public void getText() throws IOException {
        Idea idea = new Idea("mytext", new int[]{147, 584, 6, 10245, 74458, 12, 654, 74985});
        byte[] expected = {
                (byte) 00000000,
                (byte) 00000000,
                (byte) 00000000,
                (byte) 10010011,
                (byte) 00000000,
                (byte) 00000000,
                (byte) 00000010
        };
        assertArrayEquals(expected, idea.getKey());
    }
}