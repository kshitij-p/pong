package pong;

import java.awt.Insets;

public class GlobalConstants {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String WINDOW_TITLE = "Pong";
    public static final boolean WINDOW_RESIZEABLE = false;
    public static final boolean WINDOW_VISIBLE = true;

    public static Insets INSETS;

    public static final double PLAYER_WIDTH = 20;
    public static final double PLAYER_HEIGHT = 80;
    public static final double PLAYER_SPEED = 175;

    public static final double BALL_INIT_VELX = -250;
    public static final double BALL_INIT_VELY = 10;

    public static final double BALL_HEIGHT = 10;
    public static final double BALL_WIDTH = 10;
    public static final double BALL_SPEED = 350;
    public static final double BALL_MAX_ANGLE = 45.0;

    // Distance past the player the ball has to travel to count as a goal - to
    // smoothen point scoring
    public static final double GOAL_PADDING = 25;

    public static int SCORE_TO_WIN = 5;
}
