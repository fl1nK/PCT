package ex1_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final int N_BLUE_BALLS = 1000;
    private static final int N_RED_BALLS = 1;
    public static int numberOfBalls = 0;
    public static JLabel labelBalls;
    public static JLabel labelScore;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("ex1_4.Bounce programm");
        this.canvas = new BallCanvas();

        Pocket p1 = new Pocket(canvas, 0,0);
        canvas.add(p1);
        Pocket p2 = new Pocket(canvas, WIDTH,0);
        canvas.add(p2);
        Pocket p3 = new Pocket(canvas, 0,HEIGHT-80);
        canvas.add(p3);
        Pocket p4 = new Pocket(canvas, WIDTH,HEIGHT-80);
        canvas.add(p4);

        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonStart = new JButton("Start");
        JButton buttonEx3 = new JButton("Ex3");
        JButton buttonEx4 = new JButton("Ex4");
        JButton buttonStop = new JButton("Stop");

        labelBalls = new JLabel("Кількість шарів:");
        labelScore = new JLabel("У лузі: ");

        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas, Color.GRAY);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " + thread.getName());

                labelBalls.setText("Кількість: " + ++numberOfBalls);
            }
        });

        buttonEx3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = Color.BLUE;
                for (int i = 0; i < N_BLUE_BALLS; i++) {
                    createBall(color, 1, WIDTH / 2, HEIGHT / 2);
                    labelBalls.setText("Кількість: " + ++numberOfBalls);
                }

                color = Color.RED;
                for (int i = 0; i < N_RED_BALLS; i++) {
                    createBall(color, 10, WIDTH / 2, HEIGHT / 2);
                    labelBalls.setText("Кількість: " + ++numberOfBalls);
                }
            }
        });

        buttonEx4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                createBallJoin(Color.BLUE, Thread.MIN_PRIORITY, WIDTH / 2, HEIGHT / 2);

                createBallJoin(Color.RED, Thread.MIN_PRIORITY, WIDTH / 2, HEIGHT / 2);

            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonEx3);
        buttonPanel.add(buttonEx4);
        buttonPanel.add(buttonStop);

        buttonPanel.add(labelBalls);
        buttonPanel.add(labelScore);

        content.add(buttonPanel, BorderLayout.SOUTH);


    }

    private void createBall(Color color, int priority, int x, int y) {
        Ball b = new Ball(canvas, color, x, y);
        canvas.add(b);

        BallThread thread = new BallThread(b);
        thread.setPriority(priority);
        thread.start();

        System.out.println("Thread name = " + thread.getName());
    }

    private void createBallJoin(Color color, int priority, int x, int y) {
        Ball b = new Ball(canvas, color, x, y);
        canvas.add(b);

        BallJoinThread thread = new BallJoinThread(b);
        thread.setPriority(priority);
        thread.start();
        System.out.println("Thread name = " + thread.getName());
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}