public class ln {
    public static final String PROGRAM_NAME = "ln" ;

    public static void main(String[] args) {
        String inputFileName = "";
        String outputFileName = "";
        if (args.length == 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        } else {
            System.err.println( PROGRAM_NAME + ": invalid number of arguments" ) ;
            System.exit(1);
        }

        try {
            Kernel.initialize();
            int res = Kernel.link( inputFileName , outputFileName ) ;
            if( res < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                System.err.println( PROGRAM_NAME + ": cannot link to the file \"" + inputFileName + "\"" ) ;
                Kernel.exit( 2 ) ;
            }
            Kernel.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}