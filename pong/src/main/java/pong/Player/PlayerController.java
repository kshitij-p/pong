package pong.Player;

import pong.GlobalConstants;
import pong.KbListener;
import pong.util.PLAYER_ENUM;
import pong.util.util;

public class PlayerController {
    private Player player;
    private KbListener kbListener;
    private PlayerControls playerControls;
    public PLAYER_ENUM playerEnum;

    private double speedMult = 1;

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

    public PlayerController setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public PlayerController setPlayer_ENUM(PLAYER_ENUM player_ENUM) {
        this.playerEnum = player_ENUM;
        return this;
    }

    public void update(double deltaTime) {

        if (kbListener.isPressed(playerControls.moveDownKey)) {
            double newY = player.getY() + GlobalConstants.PLAYER_SPEED * speedMult * deltaTime;

            if (newY > (util.getScreenBottom() - player.getHeight()))
                return;
            player.setY(newY);
        } else if (kbListener.isPressed(playerControls.moveUpKey)) {
            double newY = player.getY() - (GlobalConstants.PLAYER_SPEED * speedMult * deltaTime);

            if (newY > util.getScreenTop()) {

                player.setY(newY);
            }
        }
    }

    public double getSpeedMult() {
        return speedMult;
    }

    public void setSpeedMult(double speedMult) {
        this.speedMult = speedMult;
    }

}
