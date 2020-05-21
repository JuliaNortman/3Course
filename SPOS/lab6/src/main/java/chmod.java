import java.util.ArrayList;

public class chmod {
    public static final String PROGRAM_NAME = "chmod" ;

    public static void main(String[] args) {
        Kernel.initialize();

        try {
            if (args.length % 2 != 0 && args.length < 2) {
                System.err.println(PROGRAM_NAME + " uses arguments: filename and mode");
                Kernel.exit(1);
                return;
            }
            for(int i = 0; i < args.length; ++i) {
                String filename = args[i];
                i++;
                short mode = -1;
                try {
                    mode = Short.parseShort(args[i], 8);
                    if(!checkMode(mode)) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.err.println(PROGRAM_NAME + " " + args[i] + " is not a correct mode value");
                    Kernel.exit(1);
                    return;
                }
                int result = Kernel.chmod(filename, mode);
                if(result != 0) {
                    System.err.println(PROGRAM_NAME + " cannot change mode for the file " + filename);
                    Kernel.exit(2);
                }
            }
            Kernel.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkMode(short mode) {
        if(mode < 0 || mode > 777) return false;

        short firstDigit = (short)(mode - (mode/10)*10);
        mode /= 10;
        short secondDigit = (short)(mode - (mode/10)*10);
        mode /= 10;
        short thirdDigit = (short)(mode - (mode/10)*10);

        if(firstDigit < 0 || firstDigit > 7) return false;
        if(secondDigit < 0 || secondDigit > 7) return false;

        return true;
    }
}
