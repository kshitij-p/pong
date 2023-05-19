package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import pong.Ball.Ball;
import pong.Player.Player;
import pong.Player.PlayerController;
import pong.Player.PlayerControls;

public class GameWindow extends JFrame implements Runnable {

    Graphics2D g2;
    KbListener keyListener;

    Player player1;
    PlayerControls player1Controls;

    Player player2;
    PlayerControls player2Controls;

    Ball ball;

    public GameWindow() {
        this.setSize(GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);
        this.setTitle(GlobalConstants.WINDOW_TITLE);
        this.setResizable(GlobalConstants.WINDOW_RESIZEABLE);
        this.setVisible(GlobalConstants.WINDOW_VISIBLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GlobalConstants.INSETS = this.getInsets();

        this.g2 = (Graphics2D) this.getGraphics();
        this.keyListener = new KbListener();

        this.player1Controls = new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S);
        this.player1 = new Player(GlobalConstants.PLAYER_WIDTH + 20, 100, Color.BLUE,
                new PlayerController(keyListener, player1Controls));

        this.player2Controls = new PlayerControls();
        this.player2 = new Player(GlobalConstants.WINDOW_WIDTH - GlobalConstants.PLAYER_WIDTH - 20, 100, Color.RED,
                new PlayerController(keyListener, player2Controls));

        this.ball = new Ball((GlobalConstants.WINDOW_WIDTH / 2) + (GlobalConstants.BALL_WIDTH / 2),
                (GlobalConstants.WINDOW_HEIGHT / 2) + (GlobalConstants.BALL_HEIGHT / 2), player1, player2);

        this.addKeyListener(keyListener);
    }

    public void update(double deltaTime) {

        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        player1.update(deltaTime);
        player2.update(deltaTime);
        ball.update(deltaTime);

    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);

        player1.draw(g2);
        player2.draw(g2);

        ball.draw(g2);
    }

    public void run() {
        //
        double prevTime = 0.0;
        while (true) {
            double time = Time.getTime();
            double deltaTime = time - prevTime;
            prevTime = time;

            update(deltaTime);
        }
    }
}
