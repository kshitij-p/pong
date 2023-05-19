package pong.Player;

import java.awt.event.KeyEvent;

public class PlayerControls {
    public int moveUpKey = KeyEvent.VK_UP;
    public int moveDownKey = KeyEvent.VK_DOWN;

    public PlayerControls() {
    }

    public PlayerControls(int moveUpKey, int moveDownKey) {
        this.moveUpKey = moveUpKey;
        this.moveDownKey = moveDownKey;
    }

}
