package pong.Player;

import java.awt.event.KeyEvent;

import pong.GlobalConstants;
import pong.KbListener;
import pong.util.util;

public class PlayerController {
    private Player player;
    private KbListener kbListener;

    public PlayerController(KbListener kbListener) {
        this.kbListener = kbListener;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void update(double deltaTime) {
        if (kbListener.isPressed(KeyEvent.VK_DOWN)) {
            double newY = player.getY() + GlobalConstants.PLAYER_SPEED * deltaTime;

            if (newY > (util.getScreenBottom() - player.getHeight()))
                return;
            player.setY(newY);
        } else if (kbListener.isPressed(KeyEvent.VK_UP)) {
            double newY = player.getY() - (GlobalConstants.PLAYER_SPEED * deltaTime);

            if (newY > util.getScreenTop()) {

                player.setY(newY);
            }
        }
    }

}
