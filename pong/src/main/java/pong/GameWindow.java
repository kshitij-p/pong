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
import pong.Powerup.PowerupManager;
import pong.util.PLAYER_ENUM;

public class GameWindow extends JFrame implements Runnable {

    Graphics2D g2;
    KbListener keyListener;

    Player player1;
    PlayerControls player1Controls;

    Player player2;
    PlayerControls player2Controls;

    Text player1ScoreText;
    Text player2ScoreText;

    PowerupManager powerupManager;

    MultiLineText gameOverText;

    Ball ball;

    public GameWindow() {
        this.setSize(GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);
        this.setTitle(GlobalConstants.WINDOW_TITLE);
        this.setResizable(GlobalConstants.WINDOW_RESIZEABLE);
        this.setVisible(GlobalConstants.WINDOW_VISIBLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GlobalConstants.INSETS = this.getInsets();

        double screenCenterX = (GlobalConstants.WINDOW_WIDTH / 2);
        double screenCenterY = (GlobalConstants.WINDOW_HEIGHT / 2);

        double playerInitPosY = screenCenterY - (GlobalConstants.PLAYER_HEIGHT / 2);

        this.g2 = (Graphics2D) this.getGraphics();
        this.keyListener = new KbListener();

        this.player1Controls = new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S);
        this.player1 = new Player(GlobalConstants.PLAYER_WIDTH + 20, playerInitPosY, Color.BLUE,
                PLAYER_ENUM.ONE,
                new PlayerController(keyListener, player1Controls));

        this.player2Controls = new PlayerControls();
        this.player2 = new Player(GlobalConstants.WINDOW_WIDTH - GlobalConstants.PLAYER_WIDTH - 20, playerInitPosY,
                Color.RED,
                PLAYER_ENUM.TWO,
                new PlayerController(keyListener, player2Controls));

        double scoreXOffset = 150;
        this.player1ScoreText = new Text(String.valueOf(GameState.player1Score), scoreXOffset, 100);
        this.player2ScoreText = new Text(String.valueOf(GameState.player2Score),
                GlobalConstants.WINDOW_WIDTH - scoreXOffset, 100);

        this.powerupManager = new PowerupManager(player1, player2);

        this.ball = new Ball(screenCenterX - (GlobalConstants.BALL_WIDTH / 2),
                screenCenterY - (GlobalConstants.BALL_HEIGHT / 2), player1, player2, player1ScoreText,
                player2ScoreText, powerupManager);

        this.gameOverText = new MultiLineText(
                String.format("GAME OVER\nPlayer %s wins", GameState.winner == PLAYER_ENUM.ONE ? "1" : "2"),
                screenCenterX, screenCenterY).center(g2);

        this.addKeyListener(keyListener);
    }

    public void update(double deltaTime) {

        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        if (!GameState.gameOver) {
            player1.update(deltaTime);
            player2.update(deltaTime);
            ball.update(deltaTime);
        }

    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, GlobalConstants.WINDOW_WIDTH, GlobalConstants.WINDOW_HEIGHT);

        powerupManager.drawPowerups(g2);

        player1.draw(g2);
        player2.draw(g2);

        ball.draw(g2);

        player1ScoreText.draw(g2);
        player2ScoreText.draw(g2);

        if (GameState.gameOver) {

            gameOverText.draw(g2);
        }
    }

    public void run() {
        //
        double prevTime = 0.0;

        while (GameState.isRunning) {
            double time = Time.getTime();
            double deltaTime = time - prevTime;
            prevTime = time;

            update(deltaTime);
        }
        update(Time.getTime() - prevTime);
    }
}
