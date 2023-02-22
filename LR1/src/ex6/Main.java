package ex6;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

//        Thread thread1 = new Thread(()->{
//            for (int i = 0; i < 1_000_000; i++) {
//                counter.increment();
//            }
//        });
//
//        Thread thread2 = new Thread(()->{
//            for (int i = 0; i < 1_000_000; i++) {
//                counter.decrement();
//            }
//        });
//
//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();


        Thread thread1 = new Thread(()->{
            for (int i = 0; i < 1_000_000; i++) {
                counter.incrementSync();
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i = 0; i < 1_000_000; i++) {
                counter.decrementSync();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter.getCount());
    }
}
