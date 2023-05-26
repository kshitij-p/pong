package pong.Powerup;

import pong.GlobalConstants;
import pong.Image;

public class BungeeGum extends Powerup {

    public BungeeGum(double x, double y, PowerupManager powerupManager) {
        super(new Image("sprites/powerups/bungeegum.png", x, y, GlobalConstants.POWER_UP_WIDTH,
                GlobalConstants.POWER_UP_HEIGHT), powerupManager);

    }

    public static double getSpeedMult() {
        return 1.25;
    }

    public static double getMaxAngle() {
        return 80.0;
    }

    @Override
    public int getType() {
        return Powerup.BUNGEE_GUM;
    }
}
