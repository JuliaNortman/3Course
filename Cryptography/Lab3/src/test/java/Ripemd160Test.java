import org.junit.Test;

import static org.junit.Assert.*;

public class Ripemd160Test {

    private String string1 = "The quick brown fox jumps over the lazy dog";
    private String string2 = "The quick brown fox jumps over the lazy cog";
    private String string3 = "";
    private String string4 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";
    private String string5 = "Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
            "laboris nisi ut aliquip ex ea commodo consequat";
    private String string6 = "Duis aute irure dolor in reprehenderit in voluptate velit " +
            "esse cillum dolore eu fugiat nulla pariatur.";

    @Test
    public void getHash() {
        assertEquals("37f332f68db77bd9d7edd4969571ad671cf9dd3b".toUpperCase(),
                Ripemd160.bytesToHex(Ripemd160.getHash(string1.getBytes())));

        assertEquals("132072df690933835eb8b6ad0b77e7b6f14acad7".toUpperCase(),
                Ripemd160.bytesToHex(Ripemd160.getHash(string2.getBytes())));

        assertEquals("9c1185a5c5e9fc54612808977ee8f548b2258d31".toUpperCase(),
                Ripemd160.bytesToHex(Ripemd160.getHash(string3.getBytes())));

        assertEquals("D6CD111FEA671AE872AA05154AEC2A4B8AAFC2D8".toUpperCase(),
                Ripemd160.bytesToHex(Ripemd160.getHash(string4.getBytes())));

        assertEquals("CD5D01C5E62051DC16B9543134F7270884685C77".toUpperCase(),
                Ripemd160.bytesToHex(Ripemd160.getHash(string5.getBytes())));

        assertEquals("3872FF2197A82C05D3BEBC0BB0BE18B76C4AA556".toUpperCase(),
                Ripemd160.bytesToHex(Ripemd160.getHash(string6.getBytes())));
    }
}