package ex6;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private  int count = 0;

    private final ReentrantLock lock = new ReentrantLock();

    public void increment(){
        count++;
    }

    public void decrement(){
        count--;
    }

    public synchronized void incrementSync(){
        count++;
    }

    public synchronized void decrementSync(){
        count--;
    }

    public void incrementSynchronizedBlock() {
        synchronized (this) {
            count++;
        }
    }

    public void decrementSynchronizedBlock() {
        synchronized (this) {
            count--;
        }
    }

    public void incrementSynchronizedLock() {
        lock.lock();
        try {
            count++;
        }
        finally {
            lock.unlock();
        }
    }

    public void decrementSynchronizedLock() {
        lock.lock();
        try {
            count--;
        }
        finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
