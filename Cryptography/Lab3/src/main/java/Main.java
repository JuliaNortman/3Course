import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] arr = Ripemd160.getHash("".getBytes());

        System.out.println(bytesToHex(arr));

        for(int i = 0; i < arr.length; ++i) {
            System.out.print((arr[i]&0xFF) + " ");
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
