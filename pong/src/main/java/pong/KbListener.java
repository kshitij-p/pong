package pong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KbListener implements KeyListener {

    private boolean keyPressed[] = new boolean[128];

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyPressed[keyEvent.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        keyPressed[keyEvent.getKeyCode()] = false;
    }

    public boolean isPressed(int key) {
        return keyPressed[key];
    }
}
