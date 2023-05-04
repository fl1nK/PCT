package ex1;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class WordTask extends RecursiveTask<HashMap<String, Integer>> {

    private final List<String> list;
    private final HashMap<String, Integer> wordCounts = new HashMap<>();
    private static final int THRESHOLD = 2000;

    public WordTask(List<String> list) {
        this.list = list;
    }

    @Override
    protected HashMap<String, Integer> compute() {
        if (this.list.size() <= THRESHOLD) {
            for (String word : list) {
                this.wordCounts.putIfAbsent(word, word.length());
            }
            return this.wordCounts;
        } else {
            List<WordTask> tasks = new ArrayList<>();
            tasks.add(new WordTask(list.subList(0, list.size() / 2)));
            tasks.add(new WordTask(list.subList(list.size() / 2, list.size())));

            ForkJoinTask.invokeAll(tasks);
            for (WordTask task : tasks) {
                this.wordCounts.putAll(task.join());
            }
            return this.wordCounts;
        }
    }
}
