package pong.Powerup;

import java.awt.Graphics2D;

import pong.Player.PlayerController;
import pong.util.Drawable;

public class Powerup implements Drawable {

    protected Drawable sprite;
    protected PowerupManager powerupManager;

    Powerup(Drawable sprite, PowerupManager powerupManager) {
        this.sprite = sprite;
        this.powerupManager = powerupManager;
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
