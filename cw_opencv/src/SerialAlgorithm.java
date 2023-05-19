import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class SerialAlgorithm {
    public int countImage;
    public String INPUT_PATH;
    public String OUTPUT_PATH;
    public SerialAlgorithm(int countImage, String INPUT_PATH, String OUTPUT_PATH) {
        this.countImage = countImage;
        this.INPUT_PATH = INPUT_PATH;
        this.OUTPUT_PATH = OUTPUT_PATH;
    }

    public void processing(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        int n = countImage;

        List<String> names = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String name = String.format("duplicated_image_%d.png", i);
            names.add(name);
        }

        //Реалізація послідовного алгоритму
        for (String name : names) {
            String inputFilePath = INPUT_PATH + name;
            String outputFilePath = OUTPUT_PATH + name;

            Mat im = Imgcodecs.imread(inputFilePath);

            Mat grayIm = new Mat();
            Imgproc.cvtColor(im, grayIm, Imgproc.COLOR_BGR2GRAY);

            Imgcodecs.imwrite(outputFilePath, grayIm);

            // Звільнити ресурси
            im.release();
            grayIm.release();
        }
    }
}
