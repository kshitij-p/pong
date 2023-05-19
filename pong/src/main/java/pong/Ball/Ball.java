package pong.Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import pong.GameState;
import pong.GlobalConstants;
import pong.Text;
import pong.Player.Player;
import pong.util.Coord;
import pong.util.PLAYER_ENUM;
import pong.util.util;

public class Ball {

    private double x;
    private double y;

    private double velX = GlobalConstants.BALL_INIT_VELX;
    private double velY = GlobalConstants.BALL_INIT_VELY;

    private double initX;
    private double initY;

    private double width = GlobalConstants.BALL_WIDTH;
    private double height = GlobalConstants.BALL_HEIGHT;
    private Color color = Color.WHITE;

    private Text player1Score;
    private Text player2Score;

    Player player1;
    Player player2;

    public Ball(double x, double y, Player player1, Player player2, Text player1Score, Text player2Score) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.width = GlobalConstants.BALL_WIDTH;
        this.height = GlobalConstants.BALL_HEIGHT;
        this.player1 = player1;
        this.player2 = player2;

        this.player1Score = player1Score;
        this.player2Score = player2Score;
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

    public void reset() {
        x = initX;
        y = initY;
        velX = GlobalConstants.BALL_INIT_VELX;
        velY = GlobalConstants.BALL_INIT_VELY;

    }

    public void handleScore(PLAYER_ENUM scoredBy) {
        if (scoredBy == PLAYER_ENUM.ONE) {
            GameState.player1Score++;
            player1Score.text = String.valueOf(GameState.player1Score);
        } else {
            GameState.player2Score++;
            player2Score.text = String.valueOf(GameState.player2Score);
        }
        player1.reset();
        player2.reset();
        reset();

        if (GameState.player1Score >= GlobalConstants.SCORE_TO_WIN
                || GameState.player2Score >= GlobalConstants.SCORE_TO_WIN) {
            GameState.gameOver = true;
            GameState.winner = GameState.player1Score >= GlobalConstants.SCORE_TO_WIN ? PLAYER_ENUM.ONE
                    : PLAYER_ENUM.TWO;
        }
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

            } else if (x < (playerLeftOffset - GlobalConstants.GOAL_PADDING)) {

                handleScore(PLAYER_ENUM.TWO);
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

            } else if (x > (playerLeftOffset + GlobalConstants.GOAL_PADDING)) {
                handleScore(PLAYER_ENUM.ONE);
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
