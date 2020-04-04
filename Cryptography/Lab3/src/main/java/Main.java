import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] arr = Ripemd160.getHash("".getBytes());
        System.out.println(Ripemd160.bytesToHex(arr));
    }


}
