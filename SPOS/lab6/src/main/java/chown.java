public class chown {
    public static final String PROGRAM_NAME = "chown" ;

    public static void main(String[] args) throws Exception {

        Kernel.initialize();

        if( args.length < 2 )
        {
            System.err.println( PROGRAM_NAME + ": too few arguments" ) ;
            Kernel.exit( 1 ) ;
            return;
        }
        short uid = -1;
        try {
            uid = Short.parseShort(args[0], 8);
        } catch(NumberFormatException ignored) { }
        if (uid < 0)
        {
            Kernel.perror( PROGRAM_NAME ) ;
            System.err.println( PROGRAM_NAME + " uid does not set" ) ;
            Kernel.exit( 2 ) ;
            return;
        }
        for( int i = 1 ; i < args.length ; i ++ )
        {
            String name = args[i] ;
            int res = Kernel.chown(name, uid, false) ;
            if( res < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                System.err.println( PROGRAM_NAME + ": cannot change owner of the file \"" + name + "\"" ) ;
                Kernel.exit( 3 );
                return;
            }
        }
        Kernel.exit(0);
    }
}