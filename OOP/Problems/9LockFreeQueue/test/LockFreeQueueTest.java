
import org.junit.Test;

public class LockFreeQueueTest {

    @Test
    public void LockFreeQueueTest() throws InterruptedException {
        LockFreeQueue Queue = new LockFreeQueue();

        Thread[] thread = new Thread[8];
        for (int i = 0; i < thread.length; i++) {
            thread[i] = new Thread(() -> {
                int t = (int) (Math.random() * 100);
                Queue.enq(t);
            });
        }

        for (Thread thread1 : thread) {
            thread1.start();
        }

        for (Thread thread1 : thread) {
            thread1.join();
        }

        Queue.show(Queue);

        for (int i = 0; i < thread.length; i++) {
            thread[i] = new Thread(() -> {
                Queue.deq();
            });
        }

        for (Thread thread1 : thread) {
            thread1.start();
        }

        for (Thread thread1 : thread) {
            thread1.join();
        }
        Queue.show(Queue);
    }
}
