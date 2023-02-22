package ex1_4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Pocket> pockets = new ArrayList<Pocket>();
    private int score;
    public void add(Ball b){
        this.balls.add(b);
    }
    public void add(Pocket p){
        this.pockets.add(p);
    }
    public void remove(Ball b) {
        BounceFrame.labelScore.setText("У лузі: " + ++score);
        BounceFrame.labelBalls.setText("Кількість: " + --BounceFrame.numberOfBalls);
        this.balls.remove(b);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(int i=0; i<balls.size();i++){
            for(int j=0; j<pockets.size();j++){
                Ball b = balls.get(i);
                Pocket p = pockets.get(j);
                b.draw(g2);
                if (b.isInPool(p)) {
                    b.isInPool = true;
                    remove(b);
                    break;
                }
            }
        }

        for (Pocket p: pockets) {
            p.draw(g2);
        }
    }
}