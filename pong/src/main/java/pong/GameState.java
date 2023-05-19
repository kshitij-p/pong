package pong;

import pong.util.PLAYER_ENUM;

public class GameState {

    public static boolean isRunning = true;
    public static boolean gameOver = false;
    public static PLAYER_ENUM winner = null;

    public static int player1Score = 0;
    public static int player2Score = 0;

    public static void reset() {
        isRunning = true;
        gameOver = false;
        winner = null;

        player1Score = 0;
        player2Score = 0;
    }

}
