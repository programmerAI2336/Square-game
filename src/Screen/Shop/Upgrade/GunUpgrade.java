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

public class GunUpgrade implements MouseListener, MouseMotionListener {
    private final GameStats gameStats;
    private final WeaponUpgradeManager upgradeManager;
    private final String name;
    private final int x, y;
    private static final int SIZE = 100;
    public final Button button;
    private final int maxLevel;

    private final String filePathName;
    private final Data data;

    public GunUpgrade(GameStats gameStats, WeaponUpgradeManager upgradeManager, String name, BufferedImage image1, BufferedImage image2, int x, int y, int initialPrice, int maxLevel, String filePathName) {
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

    private void initUpgradeManager() {
        switch (name) {
            case "Pistol" -> {
                upgradeManager.pistolDamage = 25 + 2 * data.level;
                upgradeManager.pistolFireInterval = 80 - 6 * data.level;
                upgradeManager.pistolDeflection = 0.03 - 0.002 * data.level;
            }
            case "Rifle" -> {
                upgradeManager.rifleDamage = 25 + 2 * data.level;
                upgradeManager.rifleDeflection = 0.06 - 0.004 * data.level;
                upgradeManager.rifleFireInterval = 25 - data.level;
                upgradeManager.rifleMaxMag = 150 + 10 * data.level;
            }
            case "Shotgun" -> {
                upgradeManager.shotgunDamage = 25 + 2 * data.level;
                upgradeManager.shotgunMaxMag = 30 + 2 * data.level;
                upgradeManager.shotgunFireInterval = 175 - 10 * data.level;
            }
            case "Sniper" -> {
                upgradeManager.sniperDamage = 150 + 10 * data.level;
                upgradeManager.sniperMaxMag = 15 + data.level;
                upgradeManager.sniperFireInterval = 250 - 10 * data.level;
            }
            case "Gatling" -> {
                upgradeManager.gatlingDamage = 20 + 2 * data.level;
                upgradeManager.gatlingMaxMag = 150 + 10 * data.level;
                upgradeManager.gatlingFireInterval = 18 - data.level;
            }
            case "Railgun" -> {
                upgradeManager.railgunDamage = 200 + 40 * data.level;
                upgradeManager.railgunMaxMag = 5 + data.level;
                upgradeManager.railgunFireInterval = 250 - 10 * data.level;
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
                            case "Pistol" -> {
                                upgradeManager.pistolDamage += 2;
                                upgradeManager.pistolDeflection -= 0.002;
                                upgradeManager.pistolFireInterval -= 6;
                            }
                            case "Rifle" -> {
                                upgradeManager.rifleDamage += 2;
                                upgradeManager.rifleDeflection -= 0.004;
                                upgradeManager.rifleFireInterval -= 1;
                                upgradeManager.rifleMaxMag += 10;
                            }
                            case "Shotgun" -> {
                                upgradeManager.shotgunDamage += 2;
                                upgradeManager.shotgunMaxMag += 2;
                                upgradeManager.shotgunFireInterval -= 10;
                            }
                            case "Sniper" -> {
                                upgradeManager.sniperDamage += 10;
                                upgradeManager.sniperMaxMag += 1;
                                upgradeManager.sniperFireInterval -= 10;
                            }
                            case "Gatling" -> {
                                upgradeManager.gatlingDamage += 2;
                                upgradeManager.gatlingMaxMag += 10;
                                upgradeManager.gatlingFireInterval -= 1;
                            }
                            case "Railgun" -> {
                                upgradeManager.railgunDamage += 40;
                                upgradeManager.railgunMaxMag += 1;
                                upgradeManager.railgunFireInterval -= 10;
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
