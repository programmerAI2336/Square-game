package Screen.DeadScreen;

import Screen.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {
    private final DeadScreen deadScreen;

    public UI(DeadScreen deadScreen) {
        this.deadScreen = deadScreen;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(new Color(0x99000000, true));
        g2d.fillRect(0, 0, 1366, 768);
        for (Button button : deadScreen.button) {
            button.paint(g2d);
        }
        paintStats(g2d);
    }

    private void paintStats(Graphics2D g2d) {
        paintOriginalScore(g2d);
        paintGameOver(g2d);
        g2d.setFont(new Font("Open Sans", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        paintScore(g2d);
        paintKills(g2d);
        paintCents(g2d);
        paintSurvivalTime(g2d);
        paintLevelBar(g2d);
        paintCent(g2d);
        deadScreen.sparkEmitter.paint(g2d);
    }

    private void paintScore(Graphics2D g2d) {
        int x = 508;
        int y = 225;
        g2d.drawString("Score: " + deadScreen.score, x, y);
    }

    private void paintKills(Graphics2D g2d) {
        int x = 508;
        int y = 250;
        g2d.drawString("Kills: " + deadScreen.kill, x, y);
    }

    private void paintCents(Graphics2D g2d) {
        int x = 508;
        int y = 275;
        g2d.drawString("Cent: " + deadScreen.cent, x, y);
    }

    private void paintSurvivalTime(Graphics2D g2d) {
        int x = 508;
        int y = 300;
        int milli = (4 * deadScreen.survivalTime) % 1000;
        int totalSecond = (4 * deadScreen.survivalTime) / 1000;
        int second = totalSecond % 60;
        int minute = totalSecond / 60;
        g2d.drawString("Time: " + minute + "m " + second + "s " + milli + "ms", x, y);
    }

    private void paintLevelBar(Graphics2D g2d) {
        g2d.setColor(new Color(0x805A5A5A, true));
        g2d.fillRect(508, 320, 400, 30);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(508, 320, 400 * deadScreen.gameStats.currentLevelPoint / deadScreen.gameStats.requiredLevelPoint, 30);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Lv " + deadScreen.gameStats.level, 513 + 400 * deadScreen.gameStats.currentLevelPoint / deadScreen.gameStats.requiredLevelPoint, 345);
    }

    private void paintCent(Graphics2D g2d) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Items\\Cent.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2d.drawImage(image, 508, 375, 35, 35, null);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("" + deadScreen.gameStats.cent + "$", 558, 405);
    }

    private void paintGameOver(Graphics2D g2d) {
        Font font = new Font("Open Sans", Font.BOLD, 60);
        FontMetrics metrics = g2d.getFontMetrics(font);
        String text = "" + "game over";
        // Determine the X coordinate for the text
        int x = 683 - metrics.stringWidth(text) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = 60 - metrics.getHeight() / 2 + metrics.getAscent();
        // Set the font
        g2d.setFont(font);
        // Draw the String
        g2d.setColor(new Color(255, 51, 51));
        g2d.drawString(text, x, y);
    }

    private void paintOriginalScore(Graphics2D g2d) {
        // Get the FontMetrics
        Font font = new Font("Open Sans", Font.BOLD, 60);
        FontMetrics metrics = g2d.getFontMetrics(font);
        String text = "";
        if (!deadScreen.highscore) {
            text = "score: " + deadScreen.originalScore;
        } else {
            text = "Highscore: " + deadScreen.originalScore;
        }
        // Determine the X coordinate for the text
        int x = 683 - metrics.stringWidth(text) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = 150 - metrics.getHeight() / 2 + metrics.getAscent();
        // Set the font
        g2d.setFont(font);
        // Draw the String
        g2d.setColor(Color.YELLOW);
        g2d.drawString(text, x, y);
    }
}
