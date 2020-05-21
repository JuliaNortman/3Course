public class umask {
    public static final String PROGRAM_NAME = "umask" ;

    public static void main(String[] args) throws Exception {
        Kernel.initialize();
        if(args.length != 1) {
            Kernel.exit(1);
            System.err.println(PROGRAM_NAME + " invalid argument number");
            return;
        }
        try {
            short umask = Short.parseShort(args[0], 8);
            if(!chmod.checkMode(umask)) throw new NumberFormatException();
            Kernel.umask(umask);
        } catch(NumberFormatException e) {
            Kernel.exit(2);
            System.err.println(PROGRAM_NAME + " invalid argument number");
            return;
        }
        Kernel.exit(0);
    }
}
