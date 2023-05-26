package pong.Powerup;

import pong.GlobalConstants;
import pong.Image;
import pong.Player.PlayerController;

public class Fireball extends Powerup {
    public Fireball(double x, double y, PowerupManager powerupManager) {
        super(new Image("sprites/powerups/fireball.png", x, y, GlobalConstants.POWER_UP_WIDTH,
                GlobalConstants.POWER_UP_HEIGHT), powerupManager);

    }

    @Override
    public int getType() {
        return Powerup.FIREBALL;
    }

    public static double getMultiplier() {
        return 1.75;
    }

    public void applyPowerup(PlayerController playerController) {

    }

    public void removePowerup(PlayerController playerController) {
    }
}
