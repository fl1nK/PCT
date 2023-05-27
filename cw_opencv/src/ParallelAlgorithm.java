import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelAlgorithm {
    private final BufferedImage image;
    private final int nThread;

    public ParallelAlgorithm(BufferedImage image, int nThread) {
        this.image = image;
        this.nThread = nThread;
    }

    public BufferedImage processing(){

        ExecutorService executor = Executors.newFixedThreadPool(nThread);
        List<ImageProcessingThread> tasks = new ArrayList<>();

        // Прохід по кожному рядку зображення та надсилання його обробки потокам пулу
        for (int row = 0; row < image.getHeight(); row++) {
            ImageProcessingThread task = new ImageProcessingThread(image,row);
            tasks.add(task);
            executor.submit(task);
        }

        // Завершення роботи пулу потоків
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Очікування завершення всіх потоків
        }

        return image;
    }
}
