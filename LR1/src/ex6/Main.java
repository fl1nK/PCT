package ex6;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

//        Thread thread1 = new Thread(()->{
//            for (int i = 0; i < 1_000_000; i++) {
//                counter.incrementSync();
//            }
//        });
//
//        Thread thread2 = new Thread(()->{
//            for (int i = 0; i < 1_000_000; i++) {
//                counter.decrementSync();
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
        Thread thread3 = new Thread(()->{
            for (int i = 0; i < 1_000_000; i++) {
                counter.incrementSync();
            }
        });
        Thread thread4 = new Thread(()->{
            for (int i = 0; i < 1_000_000; i++) {
                counter.decrementSync();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println(counter.getCount());
    }
}
