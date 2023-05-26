package pong.util;

import java.awt.Graphics2D;

public interface Drawable {

    public void draw(Graphics2D g2);

    public double getX();

    public double getY();

    public void setX(double x);

    public void setY(double y);

    public double getWidth();

    public double getHeight();

}
