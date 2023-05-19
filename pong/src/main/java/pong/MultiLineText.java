package pong;

import java.awt.Font;
import java.awt.Graphics2D;

public class MultiLineText extends Text {

    private String[] lines;
    private int longestLineIdx = 0;
    private String text;

    public MultiLineText(String text) {
        super(text);
        calcLines(text);
    }

    public MultiLineText(String text, double x, double y) {
        super(text, x, y);
        calcLines(text);
    }

    public MultiLineText(String text, Font font, double x, double y) {
        super(text, font, x, y);
        calcLines(text);
    }

    public MultiLineText center(Graphics2D g2) {

        super.center(g2, lines[longestLineIdx]);
        return this;
    }

    protected void calcLines(String newText) {

        lines = newText.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > lines[longestLineIdx].length()) {
                longestLineIdx = i;
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        calcLines(text);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setFont(font);

        int lineHeight = g2.getFontMetrics(font).getHeight();

        for (int i = 0; i < lines.length; i++) {
            float offset = i * lineHeight;
            g2.drawString(lines[i], (float) x, (float) y + offset);
        }

    }
}
