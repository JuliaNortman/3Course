package com.knu.ynortman;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Idea {
    public static final long MODULO = 65536;

    private final byte[] key; //128-bit key
    private final byte[] text;
    private BigInteger[][] encryptionKeys = new BigInteger[9][6];
    private BigInteger[][] decryptionKeys = new BigInteger[9][6];

    public Idea(String text) throws IOException {
        if(text.length() %8 != 0) {
            while (text.length() %8 != 0) {
                text = text.concat("a");
            }
        }
        this.text = text.getBytes();
        System.out.println(Arrays.toString(this.text));
        //this.key = key.getBytes();
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

        this.key = keys;
        encryptionKeyTable(this.key);
        decryptionKeyTable(encryptionKeys);
        print(encryptionKeys);
        System.out.println();
        //print(decryptionKeys);

        //System.out.println(encryptionKeys[0][0].testBit(7) + " " + encryptionKeys[0][0].testBit(8));
    }

    public byte[] getBytes(byte[] array, int indexFrom, int indexTo){
        byte[] bytes = new byte[indexTo-indexFrom+1];
        if(indexTo > array.length-1) {
            indexTo = array.length-1;
        }
        for(int i = indexFrom; i <= indexTo; ++i) {
            bytes[i-indexFrom] = array[i];
        }
        return bytes;
    }

    public BigInteger encrypt() {
        return (algorithm(convert64BitsTextToBigInt(text), encryptionKeys));
    }

    public BigInteger decrypt() {
        BigInteger encr =  algorithm(convert64BitsTextToBigInt(text), encryptionKeys);
        System.out.println("ENCRIPTED=" + encr.toString(2));
        BigInteger decr = algorithm(encr, decryptionKeys);
        System.out.println("DECRIPTED=" + decr.toString(2));
        return  decr;
    }


    public BigInteger algorithm(BigInteger text, BigInteger[][] table) {
        //LinkedList<Byte> cyfrotext = new LinkedList<>();
        //System.out.println("text length=" + byteText.length);
        //BigInteger text = convert64BitsTextToBigInt(byteText);
        //divide on 64-bits(8 bytes) blocks
        //for(int i = 0; i < ((text.length-1)/8)+1; ++i) {
            //System.out.println("i=" + i);
            //byte[] part = getBytes(text, i*8, (i+1)*8-1); //64-bits part
            BigInteger[] bigIntPart = new BigInteger[4];
            /**for(int k = 0; k < bigIntPart.length; ++k) { //divide 64-bits part into 16-bits p1, p2, p3, p4
                byte[] pi = Arrays.copyOfRange(part, k*2, (k+1)*2);
                bigIntPart[k] = new BigInteger(pi);
            }*/

            //divide 64-bits text into 16-bits parts
        bigIntPart = convert64BigIntTextTo16bitParts(text);

            for(int r = 1; r <= 8; ++r) { //8 raunds
                bigIntPart = raund(bigIntPart, table[r-1]);
                if(r != 8) {
                    //change p2 and p3
                    BigInteger p2 = bigIntPart[1];
                    bigIntPart[1] = bigIntPart[2];
                    bigIntPart[2] = p2;
                }
            }
            BigInteger res = outputTransformation(bigIntPart, table[9-1]);
            //for(int j = 0; j < res.length; ++j) {
                //cyfrotext.add(res[j]);
            //}
        //}
        //System.out.println("size="+cyfrotext.size());
        //byte[] res = new byte[cyfrotext.size()];
        //for(int i = 0; i < cyfrotext.size(); ++i) {
            //res[i] = cyfrotext.remove();
        //}
        return res;
        //System.out.println("res length=" + res.length);
        //return new String(res);
    }

    public static BigInteger[] raund(BigInteger[] part, BigInteger[] subkeys) {
        //part - p1, p2, p3, p4
        //keys - K1, K2, K3, K4, K5, K6

        BigInteger step1 = multiplyModulo(part[0], subkeys[0]);
        BigInteger step2 = addModulo(part[1], subkeys[1]);
        BigInteger step3 = addModulo(part[2], subkeys[2]);
        BigInteger step4 = multiplyModulo(part[3], subkeys[3]);
        BigInteger step5 = step1.xor(step3);
        BigInteger step6 = step2.xor(step4);
        BigInteger step7 = multiplyModulo(step5, subkeys[4]);
        BigInteger step8 = addModulo(step7, step6);
        BigInteger step9 = multiplyModulo(step8, subkeys[5]);
        BigInteger step10 = addModulo(step7, step9);
        BigInteger[] res = new BigInteger[4];
        res[0] = step1.xor(step9);
        res[1] = step3.xor(step9);
        res[2] = step2.xor(step10);
        res[3] = step4.xor(step10);
        return res;
    }

    public static BigInteger convert128BitsKeyToBigInt (byte[] key) {
        BigInteger result = BigInteger.ZERO;
        if(key.length == 16) {
            //System.out.println("key length=" + key.length);
            for (int i = 0; i < key.length; ++i) {
                result = result.add(BigInteger.valueOf(key[i] & 0xFF).multiply(BigInteger.TWO.pow((15 - i) * 8)));
                //System.out.println("key=" + result.toString(2));
            }
           // System.out.println("BigInt key=" + result.toString(2));
            //System.out.println("BigInt size=" + (result.toByteArray()[0] & 0xFF));
            //System.out.println("key=" + result.toString(2));
            return result;
        } else {
            for (int i = 1; i < key.length; ++i) {
                result = result.add(BigInteger.valueOf(key[i] & 0xFF).multiply(BigInteger.TWO.pow((15 - i+1) * 8)));
                //System.out.println("key=" + result.toString(2));
            }
            //System.out.println("key=" + result.toString(2));
            return result;
        }
    }

    public static BigInteger convert64BitsTextToBigInt (byte[] key) {
        BigInteger result = BigInteger.ZERO;
        if(key.length == 8) {
            for (int i = 0; i < key.length; ++i) {
                result = result.add(BigInteger.valueOf(key[i] & 0xFF).multiply(BigInteger.TWO.pow((7 - i) * 8)));
            }
        } else {
            int c = key.length - 8;
            for (int i = c; i < key.length; ++i) {
                result = result.add(BigInteger.valueOf(key[i] & 0xFF).multiply(BigInteger.TWO.pow((7 - i+c) * 8)));
            }
        }
        return result;
    }


    public static BigInteger[] convert64BigIntTextTo16bitParts(BigInteger text) {
        //System.out.println("KEY=" + key.toString(2));
        BigInteger[] array = new BigInteger[4];
        for(int i = 0; i < 4; i++) {
            array[3-i] =  BigInteger.ZERO;
            for(int j = 0; j < 16; ++j) {
                if(text.testBit(i*16+j)) {
                    array[3-i] = array[3-i].setBit(j);
                } else {
                    array[3-i] = array[3-i].clearBit(j);
                }
            }
        }
        /*for(int i = 0; i < 8; ++i) {

            //System.out.println("arri="+array[i].toString(2));
        }*/
        //System.out.println();
        return array;
    }


    public static BigInteger[] convert128BigIntKeyTo16BitBigIntKeys(BigInteger key) {
        //System.out.println("KEY=" + key.toString(2));
        BigInteger[] array = new BigInteger[8];
        for(int i = 0; i < 8; i++) {
            array[7-i] =  BigInteger.ZERO;
            for(int j = 0; j < 16; ++j) {
                if(key.testBit(i*16+j)) {
                    array[7-i] = array[7-i].setBit(j);
                } else {
                    array[7-i] = array[7-i].clearBit(j);
                }
            }
        }
        for(int i = 0; i < 8; ++i) {

            //System.out.println("arri="+array[i].toString(2));
        }
        //System.out.println();
        return array;
    }

    public static BigInteger convert16bitsByteArrayToBigInt(byte a, byte b) {
        if(a >= 0) {
            return new BigInteger(new byte[]{a, b});
        }
        return BigInteger.valueOf((a&0xFF)*256+(int)b);
    }

    public static BigInteger mergeBigIntParts(BigInteger[] parts) {
        BigInteger mergedText = BigInteger.ZERO;
        for(int i = parts.length-1; i >= 0; --i) {
            for(int j = 0; j < 16; ++j) {
                if(parts[i].testBit(j)) {
                    mergedText = mergedText.setBit(j+(3-i)*16);
                } else {
                    mergedText = mergedText.clearBit(j+(3-i)*16);
                }
            }
        }
        return mergedText;
    }

    public static byte[] convertBigIntTo16bitsByteArray(BigInteger bigInt) {
        byte[] res = new byte[2];
        if(bigInt.toByteArray().length == 0) {
            return res;
        }
        if(bigInt.toByteArray().length == 1) {
            res[1] = bigInt.toByteArray()[0];
            return  res;
        }
        res[0] = bigInt.toByteArray()[bigInt.toByteArray().length-2];
        res[1] = bigInt.toByteArray()[bigInt.toByteArray().length-1];
        return res;
    }

    public BigInteger outputTransformation(BigInteger[] part, BigInteger[] subkeys) {
        //part - R1, R2, R3, R4
        //K1, K2, K3, K4

        //byte[] res = new byte[8];
        BigInteger res = BigInteger.ZERO;
        BigInteger step1 = multiplyModulo(part[0], subkeys[0]);
        BigInteger step2 = addModulo(part[1], subkeys[1]);
        BigInteger step3 = addModulo(part[2], subkeys[2]);
        BigInteger step4 = multiplyModulo(part[3], subkeys[3]);
        res = mergeBigIntParts(new BigInteger[]{step1, step2, step3, step4});
        /*res[0] = convertBigIntTo16bitsByteArray(step1)[0];
        res[1] = convertBigIntTo16bitsByteArray(step1)[1];
        res[2] = convertBigIntTo16bitsByteArray(step2)[0];
        res[3] = convertBigIntTo16bitsByteArray(step2)[1];
        res[4] = convertBigIntTo16bitsByteArray(step3)[0];
        res[5] = convertBigIntTo16bitsByteArray(step3)[1];
        res[6] = convertBigIntTo16bitsByteArray(step4)[0];
        res[7] = convertBigIntTo16bitsByteArray(step4)[1];*/
        return res;
    }

    public static BigInteger addModulo(BigInteger a, BigInteger b) {
        return a.add(b).mod(BigInteger.valueOf(MODULO));
    }

    public static BigInteger multiplyModulo(BigInteger a, BigInteger b) {
        BigInteger res = a.multiply(b).mod(BigInteger.valueOf(MODULO+1));
        if(res.compareTo(BigInteger.valueOf(MODULO)) == 0) {
            res = BigInteger.ZERO;
        }
        return res;
    }

    public static BigInteger cyclicLeftShift(BigInteger array, int n) {

        //int zeroNum = 128 - array.bitLength();
        BigInteger result = BigInteger.ZERO;
        for(int i = 128-n; i < 128; ++i) {
            if(array.testBit(i)) {
                result = result.setBit(i-(128-n));
            } else {
                result = result.clearBit(i-(128-n));
            }
        }
        for(int i = 0; i < 128-n; ++i) {
            if(array.testBit(i)) {
                result = result.setBit(i+n);
            } else {
                result = result.clearBit(i+n);
            }
        }
        return result;
    }

    public void encryptionKeyTable(byte[] key) {
        List<BigInteger> keyslist = new LinkedList<>();
        for(int i = 0; i < 8; ++i){
            keyslist.add(convert16bitsByteArrayToBigInt(key[i*2], key[i*2+1]));
        }
        BigInteger bigIntKey = convert128BitsKeyToBigInt(key);
        //System.out.println("My key=" + bigIntKey.toString(2));
        for(int j = 0; j < 8; ++j) {
            bigIntKey = cyclicLeftShift(bigIntKey, 25);
            BigInteger[] keyArray = convert128BigIntKeyTo16BitBigIntKeys(bigIntKey);
            for (int i = 0; i < 8; ++i) {
                keyslist.add(keyArray[i]);
            }
        }
        int s = 0;
        for(int i = 0; i < encryptionKeys.length; ++i){
            for(int j = 0; j < encryptionKeys[i].length; ++j) {
                encryptionKeys[i][j] = keyslist.get(j+i*6);
            }
        }
        //System.out.println("s="+encryptionKeys.length);
    }

    public void decryptionKeyTable(BigInteger[][] encrKeys) {
        for(int i = 0; i < encrKeys.length; ++i) {
            for(int j = 0; j < encrKeys[i].length; ++j) {
                if(j == 0 || j == 3) {
                    decryptionKeys[i][j] = encrKeys[8-i][j].modInverse(BigInteger.valueOf(MODULO+1));
                }
                if(i == 0 || i == 8) {
                    if(j == 1 || j == 2) {
                        decryptionKeys[i][j] = BigInteger.valueOf(MODULO).subtract(encrKeys[8-i][j]);
                    }
                }
                if(i > 0 && i <= 7) {
                    if(j == 1) {
                        decryptionKeys[i][j] = BigInteger.valueOf(MODULO).subtract(encrKeys[8-i][2]);
                    }
                    else if(j == 2) {
                        decryptionKeys[i][j] = BigInteger.valueOf(MODULO).subtract(encrKeys[8-i][1]);
                    }
                    else if(j == 4 || j == 5) {
                        decryptionKeys[i][j] = encrKeys[7 - i][j];
                    }
                }
                else {
                    if(i != 8) {
                        decryptionKeys[i][j] = encrKeys[7 - i][j];
                    }
                }
            }
        }
    }


    public static void printBits(BitSet arr) {
        for(int i = 0; i < arr.length(); ++i) {
            if(arr.get(i)) {
                System.out.print("1");
            } else{
                System.out.print("0");
            }
            if(i%4 == 3){
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public void print(BigInteger[][] keyTable) {
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 6; ++j) {
                /*if(i != 8 && (j != 4 || j != 5)) {
                    System.out.print(keyTable[i][j] + " ");
                }*/
                if(keyTable[i][j] != null) {
                    System.out.print(keyTable[i][j].toString(2) + " ");
                }
            }
            System.out.println();
        }
        //System.out.println(new BigInteger(new byte[] {(byte)253}));
    }
}
