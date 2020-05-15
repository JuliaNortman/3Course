import java.util.ArrayList;

public class chmod {
    public static final String PROGRAM_NAME = "chmod" ;

    public static void main(String[] args) {
        ArrayList<String> filename = new ArrayList<>();
        ArrayList<Short> mode = new ArrayList<>();
        if (args.length % 2 == 0 && args.length != 0) {
            for (int i = 0; i < args.length; i+=2) {
                filename.add(args[i]);
                if (args[i+1].length() != 3) {
                    System.out.println("Error \"" + PROGRAM_NAME + "\": need correct mode arguments, not " + args[i+1].length());
                    System.exit(1);
                }
                mode.add(Short.parseShort(args[i+1], 8));
            }
        } else {
            System.out.println("Error \"" + PROGRAM_NAME + "\": need correct count arguments");
            System.exit(2);
        }

        try {
            Kernel.initialize();

            for (int i = 0; i < filename.size(); i++) {
                int res = Kernel.chmod( filename.get(i) , mode.get(i) ) ;
                if( res < 0 )
                {
                    Kernel.perror( PROGRAM_NAME ) ;
                    System.err.println( PROGRAM_NAME + ": cannot to change mode file \"" + filename.get(i) + "\"" ) ;
                    Kernel.exit( 3 ) ;
                }
            }

            Kernel.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
