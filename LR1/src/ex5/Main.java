package ex5;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread1 thread1 = new Thread1("-");
        Thread1 tread2 = new Thread1("|");

        thread1.start();
        tread2.start();

//        Sync sync = new Sync();
//        Thread2 thread1 = new Thread2(sync, true, "-");
//        Thread2 thread2 = new Thread2(sync, false, "|");
//
//        thread1.start();
//        thread2.start();

    }
}

