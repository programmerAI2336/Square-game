package Screen.Customize;

import StatsManager.GameStats;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import Screen.Button;

import javax.imageio.ImageIO;

public class ColorSkin implements MouseMotionListener, MouseListener {
    public final GameStats gameStats;
    public final Button button;
    public final Color color;
    public final int killRequirement;
    public boolean chosen;

    public ColorSkin(GameStats gameStats, BufferedImage image1, BufferedImage image2, int x, int y, Color color, int killRequirement) {
        this.gameStats = gameStats;
        button = new Button(image1, image2, x, y, 120, 120);
        this.color = color;
        this.killRequirement = killRequirement;
    }

    public void paint(Graphics2D g2d) {
        button.paint(g2d);
        paintLockIcon(g2d);
        paintChosenIcon(g2d);
        paintRequirement(g2d);
    }

    private void paintLockIcon(Graphics2D g2d) {
        if (gameStats.kill < killRequirement) {
            try {
                BufferedImage lockIcon = ImageIO.read(new File("Assets\\2D models\\Lock-icon.png"));
                g2d.drawImage(lockIcon, button.x + 42, button.y + 36, 36, 48, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void paintChosenIcon(Graphics2D g2d) {
        if (chosen) {
            g2d.setColor(new Color(70, 70, 70, 178));
            g2d.fillRect(button.x, button.y, button.width, button.length);
        }
    }

    private void paintRequirement(Graphics2D g2d) {
        if (button.touched) {
            g2d.setFont(new Font("Open Sans", Font.BOLD, 30));
            g2d.setColor(Color.WHITE);
            g2d.drawString("You need " + killRequirement + " kills to unlock this", 450, 550);
        }
    }

    public void update() {
        button.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        button.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        button.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        button.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        button.mouseMoved(e);
    }
}
