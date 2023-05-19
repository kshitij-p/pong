package pong;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class GameWindow extends JFrame implements Runnable {

    Graphics2D g2;

    public GameWindow() {
        this.setSize(GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);
        this.setTitle(GlobalConstants.WINDOW_TITLE);
        this.setResizable(GlobalConstants.WINDOW_RESIZEABLE);
        this.setVisible(GlobalConstants.WINDOW_VISIBLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g2 = (Graphics2D) this.getGraphics();
    }

    public void update(double dt) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);
    }

    public void run() {
        //
        int counter = 0;
        double prevTime = 0.0;
        while (counter < 100000) {
            double time = Time.getTime();
            double deltaTime = time - prevTime;
            prevTime = time;

            update(deltaTime);

            try {
                Thread.sleep(16);
            } catch (Exception e) {

            }
            counter++;
        }
    }
}
