package Screen.Shop.Window;

import Screen.Shop.UpgradeManager.PlayerUpgradeManager;
import Screen.Shop.Upgrade.PlayerUpgrade;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class Window1 implements MouseMotionListener, MouseListener {
    private final PlayerUpgrade[] upgrade;

    public Window1(GameStats gameStats, PlayerUpgradeManager upgradeManager) {

        upgrade = new PlayerUpgrade[5];
        try {
            upgrade[0] = new PlayerUpgrade(gameStats, upgradeManager, "Extra health", ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Extra-health-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Extra-health-2.png")), 333, 200, 10, 10,"Data\\Upgrades\\Player's upgrades\\Extra health.txt");
            upgrade[1] = new PlayerUpgrade(gameStats, upgradeManager, "Higher jump", ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Higher-jump-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Higher-jump-2.png")), 533, 200, 20, 10,"Data\\Upgrades\\Player's upgrades\\Higher jump.txt");
            upgrade[2] = new PlayerUpgrade(gameStats, upgradeManager, "Faster speed", ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Faster-speed-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Faster-speed-2.png")), 733, 200, 25, 10,"Data\\Upgrades\\Player's upgrades\\Faster speed.txt");
            upgrade[3] = new PlayerUpgrade(gameStats, upgradeManager, "Triple jump", ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Triple-jump-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Triple-jump-2.png")), 933, 200, 100, 1,"Data\\Upgrades\\Player's upgrades\\Triple jump.txt");
            upgrade[4] = new PlayerUpgrade(gameStats, upgradeManager, "Faster healing", ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Faster-healing-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Player's upgrades\\Faster-healing-2.png")), 333, 400, 30, 10,"Data\\Upgrades\\Player's upgrades\\Faster healing.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Open Sans", Font.BOLD, 60));
        g2d.drawString("Player's upgrades", 425, 100);
        for (PlayerUpgrade upgrade : upgrade) {
            upgrade.paint(g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (PlayerUpgrade upgrade : upgrade) {
            upgrade.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (PlayerUpgrade upgrade : upgrade) {
            upgrade.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (PlayerUpgrade upgrade : upgrade) {
            upgrade.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (PlayerUpgrade upgrade : upgrade) {
            upgrade.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (PlayerUpgrade upgrade : upgrade) {
            upgrade.mouseMoved(e);
        }
    }

    public void update() {
        for (PlayerUpgrade upgrade : upgrade) {
            upgrade.update();
        }
    }
}
