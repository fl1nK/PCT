
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws IOException {

        String imageName = "image_4000x3000.jpg";
        System.out.println("Назва зображення " + imageName);

        // Відкриття зображення з файлу
        File inputFile = new File("input/" + imageName);
        BufferedImage image = ImageIO.read(inputFile);

        System.out.println("Послідовний алгоритм!");
        long start = System.currentTimeMillis();

        // Послідовний алгоритм
        SerialAlgorithm serialAlgorithm = new SerialAlgorithm(image);
        BufferedImage serialGrayImage = serialAlgorithm.processing();

        long serialTime = System.currentTimeMillis() - start;
        System.out.printf("Час: %d мілісекунд, виконання послідовного алгоритму.\n", serialTime);

        // Збереження зображення у чорно-білому форматі
        File outputSerialFile = new File("output/serialGrayImage.jpg");
        ImageIO.write(serialGrayImage, "jpg", outputSerialFile);

        //**************************************************************************************************************

        System.out.println("Паралельний алгоритм!");

        for (int i = 2; i <= 10; i++) {
            start = System.currentTimeMillis();

            // Паралельний алгоритм
            ParallelAlgorithm parallelAlgorithm = new ParallelAlgorithm(image, i);
            BufferedImage parallelGrayImage = parallelAlgorithm.processing();

            long parallelTime = System.currentTimeMillis() - start;
            System.out.printf("Процесів %d, Час: %d мілісекунд, виконання паралельного алгоритму.\n", i, parallelTime);
            System.out.printf("Прискорення: %.4f мілісекунд\n", (double) serialTime/parallelTime);

            // Збереження зображення у чорно-білому форматі
            File outputParallelFile = new File("output/parallelGrayImage.jpg");
            ImageIO.write(parallelGrayImage, "jpg", outputParallelFile);
        }

    }
}
