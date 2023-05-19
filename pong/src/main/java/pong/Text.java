package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Text {
    public Font font = new Font("Times New Roman", Font.PLAIN, 24);
    public double x, y;
    public String text;
    public Color color = Color.WHITE;

    public Text(String text) {
        this.text = text;
    }

    public Text(String text, double x, double y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public Text(String text, Font font, double x, double y) {
        this(text, x, y);
        this.font = font;
    }

    protected Text center(Graphics2D g2, String centerAround) {

        FontMetrics metrics = g2.getFontMetrics(font);
        // Determine the X coordinate for the text
        x = 0 + (GlobalConstants.WINDOW_WIDTH - metrics.stringWidth(centerAround)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java
        // 2d 0 is top of the screen)
        y = 0 + ((GlobalConstants.WINDOW_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
        return this;
    }

    public Text center(Graphics2D g2) {

        return center(g2, text);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setFont(font);
        g2.drawString(text, (float) x, (float) y);
    }
}
