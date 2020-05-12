package com.knu.ynortman;

public class ln {
    public static final String PROGRAM_NAME = "ln" ;

    public static void main(String[] args) {
        String in = "";
        String out = "";
        if (args.length == 2) {
            in = args[0];
            out = args[1];
        } else {
            System.err.println( PROGRAM_NAME + ": invalid number of arguments" ) ;
            System.exit(1);
        }

        try {
            Kernel.initialize();

            int res = Kernel.link( in , out ) ;
            if( res < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                System.err.println( PROGRAM_NAME + ": cannot to link file \"" +
                        in + "\"" ) ;
                Kernel.exit( 2 ) ;
            }

            Kernel.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}