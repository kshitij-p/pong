package pong.Powerup;

import java.awt.Graphics2D;

import pong.Player.PlayerController;
import pong.util.Drawable;

public class Powerup implements Drawable {

    protected Drawable sprite;
    protected PowerupManager powerupManager;

    public static final int FIRST_POWERUP_TYPE = 0;

    public static final int SPEEDUP = 0;
    public static final int FIREBALL = 1;
    public static final int BUNGEE_GUM = 2;

    public static final int LAST_POWERUP_TYPE = 2;

    Powerup(Drawable sprite, PowerupManager powerupManager) {
        this.sprite = sprite;
        this.powerupManager = powerupManager;
    }

    public int getType() {
        return -1;
    }

    public static Powerup getPowerupFromType(int type, double x, double y, PowerupManager powerupManager) {
        switch (type) {

            case Powerup.SPEEDUP:
                return new Speedup(x, y, powerupManager);
            case Powerup.FIREBALL:
                return new Fireball(x, y, powerupManager);

            case Powerup.BUNGEE_GUM:
                return new BungeeGum(x, y, powerupManager);

            default:
                throw new Error("Invalid powerup type");
        }
    }

    public Drawable getSprite() {
        return sprite;
    }

    public void setSprite(Drawable sprite) {
        this.sprite = sprite;
    }

    public double getX() {
        return this.sprite.getX();
    }

    public double getY() {
        return this.sprite.getY();
    }

    public void setX(double x) {
        this.sprite.setX(x);
    }

    public void setY(double y) {
        this.sprite.setY(y);
    }

    public void draw(Graphics2D g2) {
        this.sprite.draw(g2);
    }

    public double getHeight() {
        return this.sprite.getHeight();
    }

    public double getWidth() {
        return this.sprite.getWidth();
    }

    public void applyPowerup(PlayerController playerController) {

    }

    public void removePowerup(PlayerController playerController) {

    }
}
