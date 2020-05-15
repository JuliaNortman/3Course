public class chown {
    public static final String PROGRAM_NAME = "chown" ;

    public static void main(String[] args) throws Exception {

        //initialize simulator kernel
        Kernel.initialize();

        if( args.length < 2 )
        {
            System.err.println( PROGRAM_NAME + ": too few arguments" ) ;
            Kernel.exit( 1 ) ;
        }

        short uid = Short.parseShort(args[0]);

        if (uid < 0)
        {
            Kernel.perror( PROGRAM_NAME ) ;
            System.err.println( PROGRAM_NAME + ": incorrect uid " ) ;
            Kernel.exit( 2 ) ;
        }


        // for each filename specified
        for( int i = 1 ; i < args.length ; i ++ )
        {
            String name = args[i] ;

            int res = Kernel.chown(name, uid, (short) -1) ;
            if( res < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                System.err.println( PROGRAM_NAME + ": cannot to change owner of the file \"" + name + "\"" ) ;
                Kernel.exit( 3 ) ;
            }
        }

        Kernel.exit(0);
    }
}