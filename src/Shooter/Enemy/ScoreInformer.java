package Shooter.Enemy;

import Map.Map;

import java.awt.*;

public class ScoreInformer {
    private final Map map;
    private final Enemy enemy;
    private final int score;
    private double x, y;
    private int opacity = 255;

    public ScoreInformer(Map map, Enemy enemy) {
        this.map = map;
        this.enemy = enemy;
        this.score = enemy.score;
        x = enemy.x + enemy.size / 2;
        y = enemy.y + enemy.size / 2;
    }

    public void update() {
        if (enemy.health <= 0) {
            y -= 0.5;
            if (opacity > 0) {
                opacity -= 1;
            } else {
                opacity = 0;
            }
        } else {
            x = enemy.x + enemy.size / 2;
            y = enemy.y + enemy.size / 2;
        }
    }

    public void paint(Graphics2D g2d) {
        if (enemy.health <= 0) {
            Font font = new Font("Open Sans", Font.BOLD, 20);
            FontMetrics metrics = g2d.getFontMetrics(font);
            String text = "" + score;
            // Determine the X coordinate for the text
            int x = (int) (this.x - metrics.stringWidth(text) / 2);
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
            int y = (int) (this.y - metrics.getHeight() / 2 + metrics.getAscent());
            // Set the font
            g2d.setFont(font);
            // Draw the String
            g2d.setColor(new Color(255, 255, 0, opacity));
            g2d.drawString(text, (int) map.x + x, (int) map.y + y);
        }
    }
}
