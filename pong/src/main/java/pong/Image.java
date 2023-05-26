package pong;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pong.util.Drawable;

public class Image implements Drawable {

    private double x = 0;
    private double y = 0;

    private BufferedImage img;

    public static BufferedImage resize(BufferedImage img, double newW, double newH) {
        java.awt.Image tmp = img.getScaledInstance((int) newW, (int) newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage((int) newW, (int) newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public Image(String path) {
        try {

            this.img = ImageIO.read(new File(System.getProperty("user.dir") + "/pong/src/main/resources/" + path));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Image(String path, double x, double y) {
        this(path);
        this.x = x;
        this.y = y;
    }

    public Image(String path, double x, double y, double width, double height) {
        this(path, x, y);
        if (this.img.getWidth() != width || this.img.getHeight() != height) {
            this.img = resize(img, width, height);
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(img, (int) x, (int) y, null);

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return img.getWidth(null);
    }

    public double getHeight() {
        return img.getHeight(null);
    }

}
