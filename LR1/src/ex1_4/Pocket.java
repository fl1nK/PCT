package ex1_4;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pocket {
    private Component canvas;
    private final int XSIZE = 40;
    private final int YSIZE = 40;
    private int x;
    private int y;

    public Pocket(Component c, int x, int y){
        this.canvas = c;
        this.x = x;
        this.y = y;
    }

    public static void f(){
        int a = 0;
    }

    public void draw (Graphics2D g2){
        g2.setColor(Color.orange);
        g2.fill(new Ellipse2D.Double(x-40,y-40,80,80));
    }

    public int getXSIZE() {
        return XSIZE;
    }

    public int getYSIZE() {
        return YSIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
