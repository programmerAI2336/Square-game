package Screen.Shop.Upgrade;


import Screen.StatsScreen.GameStatsData;
import Screen.Button;
import Screen.Shop.UpgradeManager.PlayerUpgradeManager;
import Audio.Sound;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerUpgrade implements MouseListener, MouseMotionListener {
    private final GameStats gameStats;
    private final PlayerUpgradeManager upgradeManager;
    private final String name;
    private final int x, y;
    private static final int SIZE = 100;
    private final Button button;
    private final int maxLevel;

    private final String filePathName;
    private final Data data;

    public PlayerUpgrade(GameStats gameStats, PlayerUpgradeManager upgradeManager, String name, BufferedImage image1, BufferedImage image2, int x, int y, int initialPrice, int maxLevel, String filePathName) {
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
            case "Extra health":
                this.upgradeManager.playerMaxHealth = 200 + 5 * data.level;
                break;
            case "Higher jump":
                this.upgradeManager.playerJumpVelocity = 1.5 + 0.05 * data.level;
                break;
            case "Faster speed":
                this.upgradeManager.playerMoveVelocity = 2 + 0.05 * data.level;
                break;
            case "Triple jump":
                if (data.level == this.maxLevel) {
                    this.upgradeManager.playerMaxJumpTime = 3;
                } else {
                    this.upgradeManager.playerMaxJumpTime = 2;
                }
                break;
            case "Faster healing":
                this.upgradeManager.playerHealingSpeed = 0.01 + 0.001 * data.level;
                break;
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
                            case "Extra health" -> upgradeManager.playerMaxHealth += 5;
                            case "Higher jump" -> upgradeManager.playerJumpVelocity += 0.05;
                            case "Faster speed" -> upgradeManager.playerMoveVelocity += 0.05;
                            case "Triple jump" -> upgradeManager.playerMaxJumpTime = 3;
                            case "Faster healing" -> upgradeManager.playerHealingSpeed += 0.001;
                        }
                        //decrease money
                        gameStats.cent -= data.price;
                        GameStatsData.writeStats(gameStats);
                        data.level++;
                        data.price *= 2;
                        DataSaver.saveData(data, filePathName);
                        button.status = 1;
                        Sound.play("Assets\\Sounds\\Powerup.wav");
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
