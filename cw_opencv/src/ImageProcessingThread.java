import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessingThread extends Thread{

    private final BufferedImage image;
    private final int row;

    public ImageProcessingThread(BufferedImage image, int row) {
        this.image = image;
        this.row = row;
    }

    @Override
    public void run() {
        for (int x = 0; x < image.getWidth(); x++) {
            //Отримуємо значення RGB даного пікселя
            int rgb = image.getRGB(x, row);
            Color color = new Color(rgb);

            // Створення нового кольору з чорно-білими компонентами
            int brightness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
            Color newColor = new Color(brightness, brightness, brightness);
            image.setRGB(x, row, newColor.getRGB());
        }
    }
}
