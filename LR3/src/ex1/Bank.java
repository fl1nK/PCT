package ex1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private final ReentrantLock lock = new ReentrantLock();
    public Bank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }

    public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    public synchronized void transferSync(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    public synchronized void transferLock(int from, int to, int amount) {
        lock.lock();
        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }finally {
            lock.unlock();
        }
    }
    public synchronized void transferSyncWait(int from, int to, int amount){
        while (accounts[from] < amount) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        notifyAll();
        if (ntransacts % NTEST == 0)
            test();
    }


    public void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }

    public int size(){
        return accounts.length;
    }
}
//
//    public static final int NTEST = 100;
//
//    private final AtomicIntegerArray accounts;
//    private final AtomicLong ntransacts = new AtomicLong();
//    public Bank(int n, int initialBalance){
//        accounts = new AtomicIntegerArray(new int[n]);
//        int i;
//        for (i = 0; i < accounts.length(); i++)
//            accounts.set(i, initialBalance);
//        ntransacts.set(0);
//    }
//
//    public void transferAtomic(int from, int to, int amount) {
//        accounts.addAndGet(from, -amount);
//        accounts.addAndGet(to, amount);
//        ntransacts.incrementAndGet();
//        if (ntransacts.get() % NTEST == 0) test();
//    }
//
//    public void test(){
//        AtomicInteger sum = new AtomicInteger(0);
//        for (int i = 0; i < accounts.length(); i++)
//            sum.addAndGet(accounts.get(i));
//        System.out.println("Transactions:" + ntransacts.get() + " Sum: " + sum.get());
//    }
//
//    public int size(){
//        return accounts.length();
//    }
//}
