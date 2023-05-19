package pong.Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import pong.GlobalConstants;
import pong.Player.Player;
import pong.util.util;

public class Ball {

    private double x;
    private double y;

    private double velX = 50;
    private double velY = 250;

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

        // Check which side the ball is going to: velX < 0 means its goin to Player1
        // i.e. left side
        if (velX < 0) {
            double playerLeftOffset = player1.getX();
            double playerRightOffset = playerLeftOffset + player1.getWidth();
            double playerTopOffset = player1.getY();
            double playerBotOffset = player1.getY() + player1.getHeight();

            // Check if ball collides with player1
            if (x >= playerLeftOffset && x <= playerRightOffset && y >= playerTopOffset
                    && y <= playerBotOffset) {
                velX *= -1;
                velY *= -1;
            } else if (x < playerLeftOffset) {
                System.out.println("player1 lost a point");
            }

            // velX > 0 means ball is going to Player2
        } else {
            double playerLeftOffset = player2.getX();
            double playerRightOffset = playerLeftOffset + player2.getWidth();
            double playerTopOffset = player2.getY();
            double playerBotOffset = player2.getY() + player2.getHeight();

            double ballRight = x + width;

            // Check if ball collides with player2 i.e. right side
            if (ballRight >= playerLeftOffset && ballRight <= playerRightOffset && y >= playerTopOffset
                    && y <= playerBotOffset) {
                velX *= -1;
                velY *= -1;
            } else if (x > playerLeftOffset) {
                System.out.println("player2 lost a point");
            }
        }

        // Make ball bounce off the screen edges

        if (velY < 0) {
            double ballTop = y;
            if (ballTop <= util.getScreenTop()) {
                velX *= -1;
                velY *= -1;
            }
        } else {
            double ballBot = y + height;
            if (ballBot >= util.getScreenBottom()) {
                velX *= -1;
                velY *= -1;
            }
        }

        x += velX * deltaTime;
        y += velY * deltaTime;
    }

}
