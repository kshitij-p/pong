package pong.Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import pong.GlobalConstants;
import pong.Powerup.Powerup;
import pong.util.Drawable;
import pong.util.PLAYER_ENUM;

public class Player implements Drawable {

    private double x, y;
    private double width = GlobalConstants.PLAYER_WIDTH;
    private double height = GlobalConstants.PLAYER_HEIGHT;
    private Color color;
    private PlayerController controller;
    private PLAYER_ENUM player_ENUM;
    public Powerup powerup = null;

    private double initX; // Used to reset pos
    private double initY; // Used to reset pos

    public Player(double x, double y, Color color, PLAYER_ENUM player_ENUM, PlayerController controller) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.player_ENUM = player_ENUM;

        this.controller = controller;
        this.controller.setPlayer(this).setPlayer_ENUM(player_ENUM);

        this.initX = x;
        this.initY = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PlayerController getController() {
        return controller;
    }

    public void setController(PlayerController controller) {
        this.controller = controller;
    }

    public void update(double deltaTime) {
        this.controller.update(deltaTime);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void reset() {
        this.x = this.initX;
        this.y = this.initY;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(new Rectangle2D.Double(x, y, width, height));
    }

    public PLAYER_ENUM getPlayer_ENUM() {
        return player_ENUM;
    }

}
