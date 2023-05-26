package pong.Powerup;

import pong.GlobalConstants;
import pong.Image;
import pong.Player.PlayerController;

public class Speedup extends Powerup {

    public Speedup(double x, double y, PowerupManager powerupManager) {
        super(new Image("sprites/powerups/speedup.png", x, y, GlobalConstants.POWER_UP_WIDTH,
                GlobalConstants.POWER_UP_HEIGHT), powerupManager);

    }

    @Override
    public int getType() {
        return Powerup.SPEEDUP;
    }

    @Override
    public void applyPowerup(PlayerController playerController) {
        playerController.setSpeedMult(2.15);
    }

    @Override
    public void removePowerup(PlayerController playerController) {
        playerController.setSpeedMult(1);
    }
}
