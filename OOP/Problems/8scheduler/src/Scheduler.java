import java.util.Calendar;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Scheduler {

    private static final int CAPACITY = 10;//ємність

    private final BlockingQueue<TimedTask> queue = new PriorityBlockingQueue<>(CAPACITY,
            Comparator.comparing(TimedTask::getScheduledTime));

    private final Object lock = new Object();
    private volatile boolean running = true;

    public void start() throws InterruptedException {
        running = true;
        while (running) {
            waitForNextTask();
            TimedTask task = queue.take();
            if (task != null) {
                new Thread(task::run).start();
            }
        }
    }

    private void waitForNextTask() throws InterruptedException {
        synchronized (lock) {
            TimedTask nextTask = queue.peek();
            while (nextTask == null || !nextTask.shouldRunNow()) {
                if (nextTask == null) {
                    lock.wait();
                } else {
                    lock.wait(nextTask.runFromNow());
                }
                nextTask = queue.peek();
            }
        }
    }

    public void add(Runnable task) {
        add(task, 0);
    }

    public boolean contains(Runnable task) {
        return queue.contains(TimedTask.fromTask(task,0));
    }

    public void add(Runnable task, long delay) {
        synchronized (lock) {
            queue.offer(TimedTask.fromTask(task, delay));
            lock.notify();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }

    private static class TimedTask {
        private Runnable task;
        private Calendar scheduledTime;

        public TimedTask(Runnable task, Calendar scheduledTime) {
            this.task = task;
            this.scheduledTime = scheduledTime;
        }

        public static TimedTask fromTask(Runnable task, long delayMs) {
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(now.getTimeInMillis() + delayMs);
            return new TimedTask(task, now);
        }

        public Calendar getScheduledTime() {
            return scheduledTime;
        }

        public long runFromNow() {
            return scheduledTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        }

        public boolean shouldRunNow() {
            return runFromNow() <= 0;
        }

        public void run() {
            task.run();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TimedTask)
                return this.task==((TimedTask)o).task;
            return false;
        }
    }
}
