import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String INPUT_PATH = "input/large_input/";
    private static final String OUTPUT_PATH = "output/large_output/";

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        int n = 500;
        System.out.printf("Кількість зображень %d\n", n);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String name = String.format("duplicated_image_%d.png", i);
            names.add(name);
        }

        //Реалізація послідовного алгоритму
        long start = System.currentTimeMillis();

        SerialAlgorithm serialAlgorithm = new SerialAlgorithm(n, INPUT_PATH, OUTPUT_PATH);
        serialAlgorithm.processing();

        double serialTime = (System.currentTimeMillis() - start) / 1000.0;
        System.out.printf("Час: %.4f секунд, виконання послідовного алгоритму.\n", serialTime);

        //Реалізація паралельного алгоритму
        for (int nProcesses = 2; nProcesses <= 50; nProcesses++) {
            start = System.currentTimeMillis();

            ExecutorService executor = Executors.newFixedThreadPool(nProcesses);
            List<ImageProcessingThread> tasks = new ArrayList<>();

            for (String name : names) {
                ImageProcessingThread task = new ImageProcessingThread(INPUT_PATH, OUTPUT_PATH, name);
                tasks.add(task);
                executor.execute(task);
            }

            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double parallelTime = (System.currentTimeMillis() - start) / 1000.0;
            System.out.printf("Час: %.4f секунд, виконання паралельного алгоритму з %d процесами. Прискорення %.4f секунд.\n",
                    parallelTime, nProcesses, serialTime / parallelTime);
        }
    }
}
