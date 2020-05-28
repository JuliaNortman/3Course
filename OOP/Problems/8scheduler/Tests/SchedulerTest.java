import java.util.Calendar;
import java.util.logging.LogManager;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void start() {
        Scheduler scheduler = new Scheduler();
        new Thread(()->{
            try {
                scheduler.start();
            } catch (InterruptedException e) {
                LogManager.getLogManager().getLogger(SchedulerTest.class.getName()).severe(e.getMessage());
            }
        }).start();
        assertEquals(true, scheduler.isRunning());
        scheduler.stop();
    }

    static class MyTask implements Runnable {
        private String name;
        public long lastTimeRun = 0;

        public MyTask(String name) {
            this.name = name;
        }

        long getLastTimeRun() {
            return lastTimeRun;
        }

        @Override
        public void run() {
            lastTimeRun = Calendar.getInstance().getTimeInMillis();
        }
    }


    @org.junit.jupiter.api.Test
    void add() throws InterruptedException {
        Scheduler scheduler = new Scheduler();
        new Thread(()->{
            try {
                scheduler.start();
            }
            catch (InterruptedException e) {
                LogManager.getLogManager().getLogger(SchedulerTest.class.getName()).severe(e.getMessage());
            }
        }).start();
        MyTask task = new MyTask("task1");
        long currentTimeMs = Calendar.getInstance().getTimeInMillis();
        scheduler.add(task, 1000);
        assertTrue(scheduler.contains(task));
        while (Calendar.getInstance().getTimeInMillis() < currentTimeMs+1000)
            assertEquals(0, task.getLastTimeRun());
        sleep(500);
        assertNotEquals(0, task.getLastTimeRun());
        scheduler.stop();
    }

    @org.junit.jupiter.api.Test
    void stop() {
        Scheduler scheduler = new Scheduler();
        new Thread(()->{
            try {
                scheduler.start();
            }
            catch (InterruptedException e) {
                LogManager.getLogManager().getLogger(SchedulerTest.class.getName()).severe(e.getMessage());
            }
        }).start();
        scheduler.stop();
        assertFalse(scheduler.isRunning());
    }
}