package ex2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
//        String importantInfo[] = {
//                "Mares eat oats",
//                "Does eat oats",
//                "Little lambs eat ivy",
//                "A kid will eat ivy too"
//        };

        int[] importantInfo = new int[5000];
        for (int i = 0; i < importantInfo.length; i++) {
            importantInfo[i] = i + 1;
        }
        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {}
        }
        drop.put(0);
    }
}