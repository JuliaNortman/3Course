public class find {

    private static String PROGRAM_NAME = "find";

    public static void main (String[] args) throws Exception {
        Kernel.initialize();
        if (args.length != 1) {
            System.err.println(PROGRAM_NAME + " invalid argument number");
            Kernel.exit( 1 );
            return;
        }
        tryFind(args[0]);
        Kernel.exit(0);
    }


    static private void tryFind(String path) throws Exception {

        String name = path;
        int status = 0 ;
        int count = 0;

        Stat stat = new Stat() ;
        status = Kernel.stat( name , stat ) ;
        if(status < 0) {
            System.err.println("Cannot init stat");
            return;
        }
        short type = (short)( stat.getMode() & Kernel.S_IFMT ) ;

        if( type == Kernel.S_IFREG )
        {
            System.out.println("Does not directory") ;
        }

        if( type == Kernel.S_IFDIR )
        {
            int fd = Kernel.open( name , Kernel.O_RDONLY ) ;
            if( fd < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                System.err.println( PROGRAM_NAME +
                        ": unable to open \"" + name + "\" for reading" ) ;
                Kernel.exit(1) ;
            }

            DirectoryEntry directoryEntry = new DirectoryEntry() ;

            while( true )
            {
                status = Kernel.readdir( fd , directoryEntry ) ;

                if( status <= 0 ) {
                    break;
                }
                String entryName = directoryEntry.getName() ;
                if (count > 1) {
                    print(name + "/" + entryName);
                    tryFind(name + "/" + entryName);
                }
                count += 1;
            }
        }
    }

    private static void print( String name)
    {
        StringBuffer s = new StringBuffer() ;
        s.append( name ) ;
        System.out.println( s.toString() ) ;
    }
}