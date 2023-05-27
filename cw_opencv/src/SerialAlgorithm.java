import java.awt.*;
import java.awt.image.BufferedImage;

public class SerialAlgorithm {
    private final BufferedImage image;

    public SerialAlgorithm(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage processing(){

        // Отримання ширини та висоти зображення
        int width = image.getWidth();
        int height = image.getHeight();

        // Прохід по кожному пікселю та зміна його кольору на відповідний чорно-білий колір
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //Отримуємо значення RGB даного пікселя
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);

                // Створення нового кольору з чорно-білими компонентами
                int brightness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color newColor = new Color(brightness, brightness, brightness);
                image.setRGB(x, y, newColor.getRGB());
            }
        }

        return image;
    }
}
