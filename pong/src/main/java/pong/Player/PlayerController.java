package pong.Player;

import pong.GlobalConstants;
import pong.KbListener;
import pong.util.util;

public class PlayerController {
    private Player player;
    private KbListener kbListener;
    private PlayerControls playerControls;

    public PlayerController(KbListener kbListener) {
        this.kbListener = kbListener;
        this.playerControls = new PlayerControls();
    }

    public PlayerController(KbListener kbListener, PlayerControls playerControls) {
        this.kbListener = kbListener;
        this.playerControls = playerControls;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void update(double deltaTime) {
        if (kbListener.isPressed(playerControls.moveDownKey)) {
            double newY = player.getY() + GlobalConstants.PLAYER_SPEED * deltaTime;

            if (newY > (util.getScreenBottom() - player.getHeight()))
                return;
            player.setY(newY);
        } else if (kbListener.isPressed(playerControls.moveUpKey)) {
            double newY = player.getY() - (GlobalConstants.PLAYER_SPEED * deltaTime);

            if (newY > util.getScreenTop()) {

                player.setY(newY);
            }
        }
    }

}
