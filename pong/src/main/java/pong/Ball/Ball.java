package pong.Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import pong.GlobalConstants;
import pong.Player.Player;
import pong.util.Coord;
import pong.util.util;

public class Ball {

    private double x;
    private double y;

    private double velX = -150;
    private double velY = 10;

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

    public Coord getVelAfterBounce(Player player) {
        // Calculates how much to shift the ball by based on how close to the center it
        // hits.

        double playerCenter = player.getY() + (player.getHeight() / 2.0);
        double ballCenter = y + (height / 2.0);

        // Calculates how close to the center the ball is on a scale of -1 to 1 (to get
        // it on -1 to 1 scale we divide by player.height / 2)
        double normalisedDiff = (playerCenter - ballCenter) / (player.getHeight() /
                2.0);
        double thetaAngle = Math.toRadians(normalisedDiff * GlobalConstants.BALL_MAX_ANGLE);

        // Modifier between 0.75 to 1 based on whether ball hits the edge or not. If
        // ball hits center, 0.75 and if edge, 1.0
        double speedAngleModifier = Math.min(Math.max(0.75, Math.abs(normalisedDiff)), 1.0);

        double finalSpeed = GlobalConstants.BALL_SPEED * speedAngleModifier;

        // Use trignometry to calc new velX and velY
        double newVelx = Math.abs(Math.cos(thetaAngle) * finalSpeed);
        double newVely = (-Math.sin(thetaAngle)) * finalSpeed;

        newVelx = newVelx * (Math.signum(velX) * -1.0);

        return new Coord(newVelx, newVely);
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

            double ballBot = y + height;

            // Check if ball collides with player1
            if (x >= playerLeftOffset && x <= playerRightOffset && ballBot >= playerTopOffset
                    && y <= playerBotOffset) {

                Coord newVels = getVelAfterBounce(player1);

                velX = newVels.x;
                velY = newVels.y;

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

            double ballBot = y + height;

            // Check if ball collides with player2 i.e. right side
            if (ballRight >= playerLeftOffset && ballRight <= playerRightOffset && ballBot >= playerTopOffset
                    && y <= playerBotOffset) {
                Coord newVels = getVelAfterBounce(player2);

                velX = newVels.x;
                velY = newVels.y;
            } else if (x > playerLeftOffset) {
                System.out.println("player2 lost a point");
            }
        }

        // Make ball bounce off the screen edges

        if (velY < 0) {
            double ballTop = y;
            if (ballTop <= util.getScreenTop()) {
                velY *= -1;
            }
        } else {
            double ballBot = y + height;
            if (ballBot >= util.getScreenBottom()) {
                velY *= -1;
            }
        }

        x += velX * deltaTime;
        y += velY * deltaTime;
    }

}
