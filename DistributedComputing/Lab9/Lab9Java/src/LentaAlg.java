import java.util.Random;
import java.util.concurrent.*;

public class LentaAlg {
    public static void main(String[] argv) throws ExecutionException, InterruptedException {
        int n = 100;
        Double[][] A = makeRandomMatrix(n);
        Double[][] B = makeRandomMatrix(n);
        Double[][] C = new Double[n][n];

        long start = System.currentTimeMillis();

        new ForkJoinPool().invoke(new MatrixMultiplicator(A, B, C, 0));

        long stop = System.currentTimeMillis();

        System.out.println("Time of execution: " + (stop - start) + "ms");

        //printMatrix(C);
    }

    static class MatrixMultiplicator extends RecursiveTask<Double[][]>{
        Double[][] A, B, C;
        int line;

        public MatrixMultiplicator(Double[][] A, Double[][] B, Double[][] C, int line){
            this.A = A;
            this.B = B;
            this.C = C;
            this.line = line;
        }

        @Override
        protected Double[][] compute() {
            if (line != A.length){
                Double[] res = new Double[A.length];
                double sum;
                for (int j = 0; j < A.length; j++) {
                    sum = 0;
                    for (int k = 0; k < A.length; k++) {
                        sum += A[line][k] * B[k][j];
                    }
                    res[j] = sum;
                }
                C[line] = res;

                MatrixMultiplicator mult = new MatrixMultiplicator(A, B, C, line+1);
                mult.fork();

                C = mult.join();
            }

            return C;
        }
    }

    public static Double[][] makeRandomMatrix(int n){
        Random random = new Random();
        Double[][] matrix = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (double) random.nextInt(100);
            }
        }
        return matrix;
    }

    public static void printMatrix(Double[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print("[" + matrix[i][j] + "] ");
            }
            System.out.println();
        }
    }
}
