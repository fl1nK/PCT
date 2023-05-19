import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

class ImageProcessingThread implements Runnable {
    private String inputPath;
    private String outputPath;
    private String imageName;

    public ImageProcessingThread(String inputPath, String outputPath, String imageName) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.imageName = imageName;
    }

    @Override
    public void run() {
        String inputFilePath = inputPath + imageName;
        String outputFilePath = outputPath + imageName;

        Mat im = Imgcodecs.imread(inputFilePath);
        Mat grayIm = new Mat();
        Imgproc.cvtColor(im, grayIm, Imgproc.COLOR_BGR2GRAY);

        Imgcodecs.imwrite(outputFilePath, grayIm);

        // Звільнити ресурси
        im.release();
        grayIm.release();
    }
}
