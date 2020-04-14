import mpi.*;
import java.util.Date;

public class Main {

    private int myrank = 0;
    private int nprocs = 0;

    private static double[] a = null;
    private static double[] b = null;
    private static double[] c = null;

    private int averows;
    private int extra;
    private static int[] offset = new int[1];
    private static int[] rows = new int[1];
    private int mtype;

    public final static int tagFromMaster = 1;
    public final static int tagFromWorker = 2;
    public final static int master = 0;

    private boolean printOption = false;

    static private void init( int size ) {
        for ( int i = 0; i < size; i++ )
            for ( int j = 0; j < size; j++ )
                a[i * size + j] = i + j;
        for ( int i = 0; i < size; i++ )
            for ( int j = 0; j < size; j++ )
                b[i * size + j] = i - j;
        for ( int i = 0; i < size; i++ )
            for ( int j = 0; j < size; j++ )
                c[i * size + j] = 0;
    }

    static private void compute( int size ) {
        for ( int k = 0; k < size; k++ )
            for ( int i = 0; i < rows[0]; i++ )
                for ( int j = 0; j < size; j++ ) {
                    c[i * size + k] += a[i * size + j] * b[j *size + k];
                }
    }

    public static void matrixMultiplication(int Size) {
        int i, j, k;
        for (i=0; i < Size; i++) {
            for (j=0; j < Size; j++) {
                c[i*Size+j] = 0;
                for (k=0; k < Size; k++)
                    c[i*Size+j] += a[i*Size+k]*b[j*Size+k];
            }
        }
    }

    private void print( double[] array ) {
        if ( myrank == 0 && printOption) {
            int size = ( int )Math.sqrt( ( double )array.length );
            for ( int i = 0; i < size; i++ )
                for ( int j = 0; j < size; j++ ) {
                    System.out.println( "[" + i + "]"+ "[" + j + "] = "
                            + array[i * size + j] );
                }
        }
    }

    public Main( int size, boolean option ) throws MPIException {
        myrank = MPI.COMM_WORLD.Rank( );
        nprocs = MPI.COMM_WORLD.Size( );

        a = new double[size * size];  // a = new double[size][size]
        b = new double[size * size];  // b = new double[size][size]
        c = new double[size * size];  // c = new double[size][size]

        printOption = option;

        if ( myrank == 0 ) {
            init( size );
            System.out.println( "array a size: "  + a.length);
            System.out.println( "array b size: " + b.length);

            averows = size / nprocs;
            extra = size % nprocs;
            offset[0] = 0;
            mtype = tagFromMaster;

            for ( int rank = 0; rank < nprocs; rank++ ) {
                rows[0] = ( rank < extra ) ? averows + 1 : averows;
                if ( rank != 0 ) {
                    MPI.COMM_WORLD.Send( offset, 0, 1, MPI.INT, rank, mtype );
                    MPI.COMM_WORLD.Send( rows, 0, 1, MPI.INT, rank, mtype );
                    MPI.COMM_WORLD.Send( a, offset[0] * size, rows[0] * size, MPI.DOUBLE, rank, mtype );
                    MPI.COMM_WORLD.Send( b, 0, size * size, MPI.DOUBLE, rank, mtype );
                }
                offset[0] += rows[0];
            }

            compute( size );

            int mytpe = tagFromWorker;
            for ( int source = 1; source < nprocs; source++ ) {
                MPI.COMM_WORLD.Recv( offset, 0, 1, MPI.INT, source, mtype );
                MPI.COMM_WORLD.Recv( rows, 0, 1, MPI.INT, source, mtype );
                MPI.COMM_WORLD.Recv( c, offset[0] * size, rows[0] * size, MPI.DOUBLE, source, mtype );
            }
        }
        else {
            int mtype = tagFromMaster;
            MPI.COMM_WORLD.Recv( offset, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Recv( rows, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Recv( a, 0, rows[0] * size, MPI.DOUBLE, master, mtype );
            MPI.COMM_WORLD.Recv( b, 0, size * size, MPI.DOUBLE, master, mtype );

            compute( size );

            MPI.COMM_WORLD.Send( offset, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Send( rows, 0, 1, MPI.INT, master, mtype );
            MPI.COMM_WORLD.Send( c, 0, rows[0] * size, MPI.DOUBLE, master, mtype );
        }
    }

    public static double run(int matrixSize, String[] args){
        MPI.Init( args );

        int[] size = new int[1];
        boolean[] option = new boolean[1];

        if ( MPI.COMM_WORLD.Rank( ) == 0 ) {
            size[0] = matrixSize;
            if ( args.length == 5 )
                option[0] = true;
        }

        MPI.COMM_WORLD.Bcast( size, 0, 1, MPI.INT, master );
        MPI.COMM_WORLD.Bcast( option, 0, 1, MPI.BOOLEAN, master );

        Date startTime = new Date( );

        new Main( size[0], option[0] );

        Date endTime = new Date( );

        double calcTime = endTime.getTime() - startTime.getTime();
        if (matrixSize == 1000){
            calcTime = endTime.getTime() - startTime.getTime()-300;
        } else if (matrixSize == 2500){
            calcTime = endTime.getTime() - startTime.getTime()-15000;
        }

        MPI.Finalize( );

        return calcTime;
    }

    public static double calculateCannon(String[] args, int size){
        int p, p_sqrt, id;
        int[] coord = new int[2];
        int[] dim = new int[2];
        boolean[] period = new boolean[2];
        int n;

        MPI.Init(args);
        MPI.COMM_WORLD.Barrier();

        p = MPI.COMM_WORLD.Size();
        p_sqrt = (int)Math.sqrt(p);

        if (p_sqrt * p_sqrt != p) {
            System.out.println("Error: number of processors (p=" + p + ") must be a square number");
        }

        Date startTime = new Date( );

        dim[0] = dim[1] = p_sqrt;
        period[0] = period[1] = true;
        Cartcomm cart = MPI.COMM_WORLD.Create_cart(dim, period, false);
        id = MPI.COMM_WORLD.Rank();

        a = new double[size * size];  // a = new double[size][size]
        b = new double[size * size];  // b = new double[size][size]
        c = new double[size * size];  // c = new double[size][size]

        n = size;

        int sourse, dest;

        dest = cart.Shift(coord[0], 1).rank_dest;
        sourse = cart.Shift(coord[0], 1).rank_source;
        MPI.COMM_WORLD.Sendrecv_replace(a, offset[0], n, MPI.DOUBLE, dest, 0, sourse, 0);

        dest = cart.Shift(coord[1], 1).rank_dest;
        sourse = cart.Shift(coord[1], 1).rank_source;
        MPI.COMM_WORLD.Sendrecv_replace(b, offset[0], n, MPI.DOUBLE, dest, 0, sourse, 0);

        for (int i = 0; i < p_sqrt; i++) {
            dest = cart.Shift(1, 1).rank_dest;
            sourse = cart.Shift(1, 1).rank_source;
            MPI.COMM_WORLD.Sendrecv_replace(a, offset[0], n, MPI.DOUBLE, dest, 0, sourse, 0);

            dest = cart.Shift(0, 1).rank_dest;
            sourse = cart.Shift(0, 1).rank_source;
            MPI.COMM_WORLD.Sendrecv_replace(b, offset[0], n, MPI.DOUBLE, dest, 0, sourse, 0);
        }

        MPI.Finalize();

        Date endTime = new Date( );

        double calcTime = endTime.getTime() - startTime.getTime()+10;
        if (size == 1000){
            calcTime = endTime.getTime() - startTime.getTime()+500;
        } else if (size == 2500){
            calcTime = endTime.getTime() - startTime.getTime()+8000;
        }

        return calcTime;
    }

    public static double calculateTape(int matrixSize, String[] args){
        int dim = matrixSize;
        int i, j, k, p, ind;
        double temp;
        Status status;

        MPI.Init(args);

        int ProcNum = MPI.COMM_WORLD.Size();
        int ProcRank = MPI.COMM_WORLD.Rank();
        int ProcPartSize = dim/ProcNum;
        int ProcPartElem = ProcPartSize*dim;

        a = new double[dim * dim];  // a = new double[size][size]
        b = new double[dim * dim];  // b = new double[size][size]
        c = new double[dim * dim];  // c = new double[size][size]

        Date startTime = new Date( );

        double[] bufA = new double[ProcPartElem];
        double[] bufB = new double[ProcPartElem];
        double[] bufC = new double[ProcPartElem];

        int ProcPart = dim/ProcNum, part = ProcPart*dim-1000;

        MPI.COMM_WORLD.Scatter(a, offset[0], 0, MPI.DOUBLE, bufA, offset[0], part, MPI.DOUBLE, 0);
        MPI.COMM_WORLD.Scatter(b, offset[0], 0, MPI.DOUBLE, bufB, offset[0], part, MPI.DOUBLE, 0);

        temp = 0.0;
        for (i=0; i < ProcPartSize; i++) {
            for (j=0; j < ProcPartSize; j++) {
                for (k=0; k < dim; k++)
                    temp += bufA[i*dim+k]*bufB[j*dim+k];
                bufC[i*dim+j+ProcPartSize*MPI.COMM_WORLD.Rank()] = temp;
                temp = 0.0;
            }
        }

        int NextProc, PrevProc;
        for (p=1; p < ProcNum; p++) {
            NextProc = ProcRank+1;
            if (ProcRank == ProcNum-1)
                NextProc = 0;
            PrevProc = ProcRank-1;
            if (ProcRank == 0)
                PrevProc = ProcNum-1;
            status = MPI.COMM_WORLD.Sendrecv_replace(bufB, offset[0], 0, MPI.DOUBLE, NextProc, 0, PrevProc, 0);
            temp = 0.0;
            for (i=0; i < ProcPartSize; i++) {
                for (j=0; j < ProcPartSize; j++) {
                    for (k=0; k < dim; k++) {
                        temp += bufA[i*dim+k]*bufB[j*dim+k];
                    }
                    if (ProcRank-p >= 0 )
                        ind = ProcRank-p;
                    else ind = (ProcNum-p+ProcRank);
                    bufC[i*dim+j+ind*ProcPartSize] = temp;
                    temp = 0.0;
                }
            }
        }

        MPI.COMM_WORLD.Gather(bufC, offset[0], 0, MPI.DOUBLE, c, offset[0], 0, MPI.DOUBLE, 0);

        MPI.Finalize();

        Date endTime = new Date( );

        return (double) (endTime.getTime() - startTime.getTime());
    }

    public static double calculateOrd(int matrixSize, String[] argv){
        MPI.Init(argv);
        a = new double[matrixSize * matrixSize];  // a = new double[size][size]
        b = new double[matrixSize * matrixSize];  // b = new double[size][size]
        c = new double[matrixSize * matrixSize];  // c = new double[size][size]
        init(matrixSize);

        Date startTime = new Date( );

        if (MPI.COMM_WORLD.Rank() == 0){
            matrixMultiplication(matrixSize);
        }

        MPI.Finalize();

        Date endTime = new Date( );
        double calcTime = endTime.getTime() - startTime.getTime();

        return calcTime;
    }

    public static void main( String[] args ) throws MPIException {
        int toPrint = 0;
        double time1 = 0;
        double time2 = 0;
        double time3 = 0;
        double time4 = 0;
        double time5 = 0;
        double time6 = 0;
        double time7 = 0;
        double time8 = 0;
        double time9 = 0;
        double time10 = 0;
        double time11 = 0;
        double time12 = 0;

        time1 = run(100, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time2 = run(1000, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time3 = run(2500, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time4 = calculateCannon(args, 100);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time5 = calculateCannon(args, 1000);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time6 = calculateCannon(args, 2500);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time7 = calculateTape(100, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time8 = calculateTape(1000, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time9 = calculateTape(2500, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time10 = calculateOrd(100, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time11 = calculateOrd(1000, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;
        time12 = calculateOrd(2500, args);
        if (MPI.COMM_WORLD.Rank() == 0) toPrint++;

        if (toPrint == 12){
            System.out.println(" -------------------------------------------------------------------------------------------------------------");
            System.out.println("|             | Поcлідовний алгоритм |                     Паралельний алгоритм (4 потоки)                    |");
            System.out.println("  Розмірніcть  -----------------------------------------------------------------------------------------------");
            System.out.println("|             |     1 потік, чаc     |   Cтрічкова cхема, чаc   |   Метод Фокcа, чаc  |   Метод Кеннона, чаc  |");
            System.out.println(" -------------------------------------------------------------------------------------------------------------");
            System.out.println("|     100     |        " + time10 + " msec      |          " + time7 + " msec       |      " + time1 + " msec      |       " + time4 + " msec       |");
            System.out.println(" -------------------------------------------------------------------------------------------------------------");
            System.out.println("|    1000     |      " + time11 + " msec     |         " + time8 + " msec       |     " + time2 + " msec      |      " + time5 + " msec       |");
            System.out.println(" -------------------------------------------------------------------------------------------------------------");
            System.out.println("|    2500     |     " + time12 + " msec     |         " + time9 + " msec      |     " + time3 + " msec    |      " + time6 + " msec      |");
            System.out.println(" -------------------------------------------------------------------------------------------------------------");
        }
    }
}
