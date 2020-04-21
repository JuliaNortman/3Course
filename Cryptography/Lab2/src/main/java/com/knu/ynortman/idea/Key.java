package com.knu.ynortman.idea;

import com.knu.ynortman.bits.BitArray;

import java.util.ArrayList;
import java.util.List;

//128-bits key
public class Key {
    private BitArray key;

    public Key(BitArray key) {
        this.key = key;
    }

    public Key() {
        this.key = new BitArray(128);
    }

    public void setK(int id, int n) {
        BitArray bitArray = new BitArray(n, 16);

        for (int i = 0;i < 16;i++) {
            key.set(id*16 + i, bitArray.get(i));
        }
    }

    //2-byte(16-bits) subkey
    public BitArray getSubkey(int id) {
        BitArray subKey = new BitArray(16);

        for(int i = 0; i < 16; i++) {
            subKey.set(i, key.get(id * 16 + i));
        }

        return subKey;
    }

    @Override
    public String toString() {
        return key.toHexString();
    }

    public String toBinString() {
        return key.toBinString();
    }

    public Key generateNextKey() {
        return new Key(key.rotateLeft(25));
    }

    public List<BitArray> getSubkeys() {
        ArrayList<BitArray> subkeys = new ArrayList<>();

        for (int i = 0;i < 8;i++) {
            subkeys.add(getSubkey(i));
        }
        return subkeys;
    }

    public List<BitArray> getHalfOfSubkeys() {
        ArrayList<BitArray> subkeys = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            subkeys.add(getSubkey(i));
        }
        return subkeys;
    }
}
