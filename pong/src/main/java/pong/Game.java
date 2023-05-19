package pong;

/**
 * Hello world!
 *
 */
public class Game {
    public static void main(String[] args) {
        GameWindow window = new GameWindow();
        Thread t1 = new Thread(window);
        t1.start();
    }
}
