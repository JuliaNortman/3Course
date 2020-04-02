package com.knu.ynortman;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class IdeaTest {

    @Test
    public void getKey() throws IOException {
        Idea idea = new Idea("mytextaa");
    }

    @Test
    public void shiftTest() {
        /*byte[] arr = new byte[3];
        arr[0] = (byte) 3;
        arr[1] = (byte) 7;
        arr[2] = (byte) 10;

        byte[] actual = Idea.cyclicLeftShift(arr, 4);
        assertEquals(3, actual.length);
        byte[] expected = new byte[3];
        expected[0] = (byte) 112;
        expected[1] = (byte) 160;
        expected[2] = (byte) 48;
        assertEquals(expected[0]&0xFF, actual[0]&0xFF);
        assertEquals(expected[1]&0xFF, actual[1]&0xFF);
        assertEquals(expected[2]&0xFF, actual[2]&0xFF);*/
        byte[] keys = new byte[16];
        keys[0] = (byte) 255;
        keys[1] = (byte) 31;
        keys[2] = (byte) 89;
        keys[3] = (byte) 119;
        keys[4] = (byte) 93;
        keys[5] = (byte) 75;
        keys[6] = (byte) 213;
        keys[7] = (byte) 171;
        keys[8] = (byte) 200;
        keys[9] = (byte) 46;
        keys[10] = (byte) 13;
        keys[11] = (byte) 7;
        keys[12] = (byte) 84;
        keys[13] = (byte) 131;
        keys[14] = (byte) 169;
        keys[15] = (byte) 17;
        //Idea.cyclicLeftShift(keys, 25);
    }

    @Test
    public void roundTest() {
        byte[] part = new byte[8];
        part[0] = (byte) 78;
        part[1] = (byte) 0;
        part[2] = (byte) 116;
        part[3] = (byte) 37;
        part[4] = (byte) 15;
        part[5] = (byte) 210;
        part[6] = (byte) 71;
        part[7] = (byte) 53;

        byte[] keys = new byte[16];
        keys[0] = (byte) 94;
        keys[1] = (byte) 31;
        keys[2] = (byte) 89;
        keys[3] = (byte) 119;
        keys[4] = (byte) 93;
        keys[5] = (byte) 75;
        keys[6] = (byte) 213;
        keys[7] = (byte) 171;
        keys[8] = (byte) 200;
        keys[9] = (byte) 46;
        keys[10] = (byte) 13;
        keys[11] = (byte) 7;
        keys[12] = (byte) 84;
        keys[13] = (byte) 131;
        keys[14] = (byte) 169;
        keys[15] = (byte) 17;
        //Idea.raund(part, keys);
    }

    @Test
    public void algorithmTest() throws IOException {
        Idea idea = new Idea("abcdffffa");
        System.out.println(idea.decrypt());
    }
}