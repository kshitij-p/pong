package pong.Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import pong.GlobalConstants;
import pong.Player.Player;

public class Ball {

    private double x;
    private double y;
    private double width = GlobalConstants.BALL_WIDTH;
    private double height = GlobalConstants.BALL_HEIGHT;
    private Color color = Color.WHITE;
    Player player1;
    Player player2;

    public Ball(double x, double y, Player player1, Player player2) {
        this.x = x;
        this.y = y;
        this.width = GlobalConstants.BALL_WIDTH;
        this.height = GlobalConstants.BALL_HEIGHT;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(new Rectangle2D.Double(x, y, width, height));
    }

    public void update(double deltaTime) {

    }

}
