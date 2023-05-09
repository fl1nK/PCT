package ex1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {


        List<String> words = getWords("LR4/testFolder/text");

        System.out.printf("Number of words: %d\n\n", words.size());

        long currTime = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(6);
        HashMap<String, Integer> res = pool.invoke(new WordTask(words));
        long currTimeForkJoin = System.nanoTime() - currTime;

        System.out.printf("Execution time (ForkJoin): %d\n", currTimeForkJoin);

        double aver = 0;
        for (Integer i : res.values()) {
            aver += i;
        }
        System.out.printf("Average word length: %f\n", aver / res.size());

        currTime = System.nanoTime();
        HashMap<String, Integer> res2 = SimpleWordCount.processing(words);
        long currTimeSimple = System.nanoTime() - currTime;

        System.out.printf("Execution time (Single Thread): %d\n", currTimeSimple);

        System.out.printf("SpeadUp = %.2f\n", (double) currTimeSimple / currTimeForkJoin);

    }
    public static List<String> getWords(String fileName) throws IOException {
        List<String> wordList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.trim().split("(\\s|\\p{Punct})+");
            wordList.addAll(Arrays.asList(words));
        }

        reader.close();
        return wordList;
    }
}

