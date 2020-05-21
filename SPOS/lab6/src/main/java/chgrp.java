public class chgrp {
    public static final String PROGRAM_NAME = "chgrp" ;

    public static void main(String[] args) throws Exception {
        Kernel.initialize();
        if( args.length < 2 )
        {
            System.err.println( PROGRAM_NAME + ": invalid arguments number" ) ;
            Kernel.exit( 1 ) ;
            return;
        }
        short gid = -1;
        try {
            gid = Short.parseShort(args[0]);
        } catch(NumberFormatException e) { }

        if (gid < 0)
        {
            Kernel.perror( PROGRAM_NAME ) ;
            System.err.println( PROGRAM_NAME + ": incorrect gid " ) ;
            Kernel.exit( 2 );
            return;
        }
        for( int i = 1 ; i < args.length ; i ++ )
        {
            String name = args[i] ;
            int res = Kernel.chown(name,gid, true) ;
            if( res < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                System.err.println( PROGRAM_NAME + ": cannot to change group ownership of the file \"" + name + "\"" ) ;
                Kernel.exit( 3 ) ;
                return;
            }
        }

        Kernel.exit(0);
    }
}