import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import java.util.ArrayList;
import java.util.List;

public class ImageDuplicator {

    private static final String OUTPUT_PATH = "input/large_input/";

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Завантажити зображення
        Mat image = Imgcodecs.imread("input/image.png");

        // Вказати кількість разів, на яку треба продублювати зображення
        int n = 500;

        // Створити список для зберігання продубльованих зображень
        List<Mat> duplicatedImages = new ArrayList<>();

        // Продублювати зображення
        for (int i = 0; i < n; i++) {
            Mat duplicatedImage = new Mat();
            Core.repeat(image, 1, 1, duplicatedImage);
            duplicatedImages.add(duplicatedImage);
        }

        // Зберегти продубльовані зображення
        for (int i = 0; i < duplicatedImages.size(); i++) {
            String outputFileName = OUTPUT_PATH + String.format("duplicated_image_%d.png", i);
            Imgcodecs.imwrite(outputFileName, duplicatedImages.get(i));
        }

        // Звільнити ресурси
        image.release();
        for (Mat duplicatedImage : duplicatedImages) {
            duplicatedImage.release();
        }
    }
}
