package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

import pong.Player.Player;
import pong.Player.PlayerController;

public class GameWindow extends JFrame implements Runnable {

    Graphics2D g2;
    KbListener keyListener;
    Player player1;
    Player player2;

    public GameWindow() {
        this.setSize(GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);
        this.setTitle(GlobalConstants.WINDOW_TITLE);
        this.setResizable(GlobalConstants.WINDOW_RESIZEABLE);
        this.setVisible(GlobalConstants.WINDOW_VISIBLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GlobalConstants.INSETS = this.getInsets();

        this.g2 = (Graphics2D) this.getGraphics();
        this.keyListener = new KbListener();

        player1 = new Player(100, 100, Color.BLUE, new PlayerController(keyListener));

        this.addKeyListener(keyListener);
    }

    public void update(double deltaTime) {

        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        player1.update(deltaTime);

    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);

        player1.draw(g2);
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
