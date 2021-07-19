package StatsManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatsUI {
    private final GameStats gameStats;

    public StatsUI(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    public void paint(Graphics2D g2d) {
        paintLevel(g2d);
        paintCent(g2d);
    }

    private void paintLevel(Graphics2D g2d) {
        g2d.setFont(new Font("Open Sans", Font.BOLD, 40));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Lv: " + gameStats.level, 25, 110);
    }

    private void paintCent(Graphics2D g2d) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Items\\Cent.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2d.drawImage(image, 25, 25, 35, 35, null);
        g2d.setFont(new Font("Open Sans", Font.BOLD, 40));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("" + gameStats.cent + "$", 70, 55);
    }
}
