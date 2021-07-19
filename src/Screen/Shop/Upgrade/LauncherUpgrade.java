package Screen.Shop.Upgrade;

import Audio.Sound;
import Screen.Button;
import Screen.Shop.UpgradeManager.WeaponUpgradeManager;
import Screen.StatsScreen.GameStatsData;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LauncherUpgrade implements MouseListener, MouseMotionListener {
    private final GameStats gameStats;
    private final WeaponUpgradeManager upgradeManager;
    private final String name;
    private final int x, y;
    private static final int SIZE = 100;
    public final Button button;
    private final int maxLevel;

    private final String filePathName;
    private final Data data;

    public LauncherUpgrade(GameStats gameStats, WeaponUpgradeManager upgradeManager, String name, BufferedImage image1, BufferedImage image2, int x, int y, int initialPrice, int maxLevel, String filePathName) {
        this.gameStats = gameStats;
        this.upgradeManager = upgradeManager;
        this.name = name;
        button = new Button(image1, image2, x, y, SIZE, SIZE);
        this.x = x;
        this.y = y;
        this.maxLevel = maxLevel;
        this.filePathName = filePathName;
        data = DataSaver.readData(filePathName);
        data.price = (int) (initialPrice * Math.pow(2, data.level));
        initUpgradeManager();
    }

    public void initUpgradeManager() {
        switch (name) {
            case "Rocket launcher" -> {
                upgradeManager.rocketLauncherDamage = 225 + 5 * data.level;
                upgradeManager.rocketLauncherFireInterval = 250 - 10 * data.level;
                upgradeManager.rocketLauncherMaxMag = 10 + 2 * data.level;
                upgradeManager.rocketVelocity = 4 + 0.2 * data.level;
                upgradeManager.rocketExplosionRadius = 100 + 5 * data.level;
            }
            case "Missile launcher" -> {
                upgradeManager.missileLauncherDamage = 275 + 5 * data.level;
                upgradeManager.missileLauncherFireInterval = 375 - 25 * data.level;
                upgradeManager.missileLauncherMaxMag = 10 + data.level;
                upgradeManager.missileVelocity = 2 + 0.2 * data.level;
                upgradeManager.missileExplosionRadius = 100 + data.level * 5;
            }
            case "Grenade launcher" -> {
                upgradeManager.grenadeLauncherDamage = 200 + 10 * data.level;
                upgradeManager.grenadeLauncherFireInterval = 125 - 10 * data.level;
                upgradeManager.grenadeLauncherMaxMag = 15 + 2 * data.level;
                upgradeManager.grenadeExplosionRadius = 100 + 5 * data.level;
            }
        }
    }

    public void paint(Graphics2D g2d) {
        button.paint(g2d);
        paintSoldOutIcon(g2d);
        paintPrice(g2d);
        paintLevelSquare(g2d);
    }

    private void paintPrice(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Open Sans", Font.ITALIC, 20));
        g2d.drawString("Cost: " + data.price + "$", x, y - 5);
    }

    private void paintLevelSquare(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < maxLevel; i++) {
            g2d.drawRect(x + 1 + i * 10, y + SIZE + 2, 8, 8);
        }
        for (int i = 0; i < data.level; i++) {
            g2d.fillRect(x + 1 + i * 10, y + SIZE + 2, 8, 8);
        }
    }

    private void paintSoldOutIcon(Graphics2D g2d) {
        if (data.level == maxLevel) {
            try {
                BufferedImage image = ImageIO.read(new File("Assets\\2D models\\Shop\\Sold-out.png"));
                g2d.drawImage(image, x, y, SIZE, SIZE, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

    private void buy() {
        int currentMoney = gameStats.cent;
        if (currentMoney - data.price >= 0) {
            if (data.level < maxLevel) {
                if (button.clicked) {
                    if (button.status == 0) {

                        switch (name) {
                            case "Rocket launcher" -> {
                                upgradeManager.rocketLauncherDamage += 5;
                                upgradeManager.rocketLauncherFireInterval -= 10;
                                upgradeManager.rocketLauncherMaxMag += 2;
                                upgradeManager.rocketVelocity += 0.2;
                                upgradeManager.rocketExplosionRadius += 5;
                            }
                            case "Missile launcher" -> {
                                upgradeManager.missileLauncherDamage += 5;
                                upgradeManager.missileLauncherFireInterval -= 25;
                                upgradeManager.missileLauncherMaxMag += 1;
                                upgradeManager.missileVelocity += 0.2;
                                upgradeManager.missileExplosionRadius += 5;
                            }
                            case "Grenade launcher" -> {
                                upgradeManager.grenadeLauncherDamage += 10;
                                upgradeManager.grenadeLauncherFireInterval -= 10;
                                upgradeManager.grenadeLauncherMaxMag += 2;
                                upgradeManager.grenadeExplosionRadius += 5;
                            }
                        }
                        //decrease money
                        gameStats.cent -= data.price;
                        GameStatsData.writeStats(gameStats);
                        data.level++;
                        data.price *= 2;
                        DataSaver.saveData(data, filePathName);
                        Sound.play("Assets\\Sounds\\Upgrade-weapon.wav");
                        button.status = 1;
                    }
                } else {
                    button.status = 0;
                }
            }
        }
    }

    public void update() {
        button.update();
        buy();
    }
}
