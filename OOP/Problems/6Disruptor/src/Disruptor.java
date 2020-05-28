
public class Disruptor implements Runnable {

    private static final long ITERATIONS = 10L * 1000L * 1000L;
    private static volatile long s1, s2;

    @Override
    public void run() {
        long value = s2;
        while (true) {
            while (value == s1) {
                // Выполнение итерации
            }
            value = ++s2;
        }
    }


    public static void main(String[] args) {
        Thread t = new Thread(new Disruptor());
        t.setDaemon(true);
        t.start();

        long start = System.nanoTime(), value = s1;
        while (s1 < ITERATIONS) {
            while (s2 != value) {
                // busy spin
            }
            value = ++s1;
        }
        long duration = System.nanoTime() - start;

        System.out.println("Duration: " + duration + " nanoseconds");
        System.out.println("Nanoseconds per operation: " + duration / (ITERATIONS * 2));
        System.out.println("Operations per second: " + (ITERATIONS * ITERATIONS * 2 * 2) / duration);
        System.out.println("s1 = " + s1 + ", s2 = " + s2);

    }
}
