package com.knu.ynortman;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class Cell {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    byte value = 0;
}
