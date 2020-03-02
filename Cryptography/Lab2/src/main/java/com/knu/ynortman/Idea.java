package com.knu.ynortman;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Idea {
    private final byte[] key; //128-bit key
    private final byte[] text;

    public Idea(String text, int[] key) throws IOException {
        if(key.length != 8) {
            throw new IllegalArgumentException("Key must me exactly 128 bits");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        for(int i=0; i < key.length; ++i)
        {
            dos.writeInt(key[i]);
        }
        this.key = baos.toByteArray();
        this.text = text.getBytes("UTF-8");
        System.out.println(Arrays.toString(this.key));
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getText() {
        return text;
    }
}
