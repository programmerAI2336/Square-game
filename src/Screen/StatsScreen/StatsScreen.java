package Screen.StatsScreen;

import Screen.Button;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatsScreen implements MouseListener, MouseMotionListener {
    private final GameStats gameStats;
    public Screen.Button back;

    public StatsScreen(GameStats gameStats) {
        this.gameStats = gameStats;
        try {
            back = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-2.png")), 100, 625, 150, 45);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2d) {
        try {
            BufferedImage background = ImageIO.read(new File("Assets\\2D models\\Background.png"));
            g2d.drawImage(background, 0, 0, 1366, 748, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2d.setFont(new Font("Open Sans", Font.BOLD, 50));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Highscore: " + gameStats.highScore, 400, 300);
        g2d.drawString("Total kills: " + gameStats.kill, 400, 375);
        int milli = (4 * gameStats.longestSurvivalTime) % 1000;
        int totalSecond = (4 * gameStats.longestSurvivalTime) / 1000;
        int second = totalSecond % 60;
        int minute = totalSecond / 60;
        g2d.drawString("Time: " + minute + "m " + second + "s " + milli + "ms", 400, 450);
        back.paint(g2d);
    }

    public void update() {
        back.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        back.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        back.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        back.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        back.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        back.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        back.mouseMoved(e);
    }

    public void reset() {
        back.clicked = false;
        back.touched = false;
    }
}
