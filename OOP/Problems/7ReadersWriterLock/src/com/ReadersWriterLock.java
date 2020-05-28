package com;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogManager;
import java.util.logging.Logger;

class ReadersWriterLock {

    private Logger logger = LogManager.getLogManager().getLogger(ReadersWriterLock.class.getName());
    private AtomicInteger writerReaders = new AtomicInteger(0);

    private static final AtomicInteger WRITER_BIT = new AtomicInteger(1 << 30);

    ReadersWriterLock() {}

    AtomicInteger getWriterReaders() {
        return writerReaders;
    }

    public synchronized void startRead() {
        if(writerReaders.incrementAndGet() >= WRITER_BIT.get()) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                logger.severe(e.getMessage());
            }
        }
    }

    public synchronized void startWrite() {
        while( writerReaders.compareAndSet(0, WRITER_BIT.get() ) != true ) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                logger.severe(e.getMessage());
            }
        }
    }

    public void endRead() {
        writerReaders.decrementAndGet();
    }

    public synchronized void endWrite() {
        writerReaders.set(writerReaders.get() - WRITER_BIT.get());
        notifyAll();
    }

};