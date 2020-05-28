package com;

public class ReadersWriterLockExample implements Runnable {
    private Integer val;
    private ReadersWriterLock readersWriterLock = new ReadersWriterLock();

    private final int WRITERS_NUMBER = 70;
    private final int READERS_NUMBER = 7;

    public void run() {

        val = 0;

        WriterThread[] writers = new WriterThread[WRITERS_NUMBER];

        for (int i = 0; i < WRITERS_NUMBER; ++i)
            writers[i] = new WriterThread(i);

        ReaderThread[] readers = new ReaderThread[READERS_NUMBER];

        for (int i = 0; i < READERS_NUMBER; ++i)
            readers[i] = new ReaderThread();

        for (int i = 0; i < WRITERS_NUMBER; ++i) {
            writers[i].run();
            for (int j = 0; j < READERS_NUMBER; ++j)
                readers[j].run();
        }
    }

    public class WriterThread extends Thread {

        int whatToWrite;

        WriterThread(int whatToWrite) {
            this.whatToWrite = whatToWrite;
        }

        @Override
        public void run() {
            readersWriterLock.startWrite();
            val = whatToWrite;
            readersWriterLock.endWrite();
            System.out.println("\nWrite " + whatToWrite);
        }
    }

    public class ReaderThread extends Thread {

        public int whereToRead;

        ReaderThread() {}

        @Override
        public void run() {
            readersWriterLock.startRead();
            whereToRead = val.intValue();
            System.out.print("  Read " + whereToRead);
            readersWriterLock.endRead();
        }
    }
}
