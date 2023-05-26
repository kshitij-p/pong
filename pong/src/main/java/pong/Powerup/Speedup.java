package pong.Powerup;

import pong.Text;
import pong.Player.PlayerController;

public class Speedup extends Powerup {

    public Speedup(double x, double y, PowerupManager powerupManager) {
        super(new Text("SPEED", x, y), powerupManager);
    }

    @Override
    public void applyPowerup(PlayerController playerController) {
        playerController.setSpeedMult(2);
    }

    @Override
    public void removePowerup(PlayerController playerController) {
        playerController.setSpeedMult(1);
    }
}
