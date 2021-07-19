package Modes.SurvivalMode;

import Shooter.PlayerController.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {
    private final Player player;

    public UI(Player player) {
        this.player = player;
    }

    private void paintHealthBar(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(533, 675, 300, 30);
        g2d.setColor(new Color(127, 255, 0));
        g2d.fillRect(533, 675, (int) (300 * player.health / player.maxHealth), 30);
    }

    private void paintAmmoOrAmount(Graphics2D g2d) {
        Font font = new Font("Open Sans", Font.BOLD, 40);
        FontMetrics metrics = g2d.getFontMetrics(font);
        String weaponName = player.weaponInventory.weaponName;
        String text = switch (weaponName) {
            case "Rifle" -> "" + player.weaponInventory.rifle.mag;
            case "Shotgun" -> "" + player.weaponInventory.shotgun.mag;
            case "Grenade" -> "" + player.weaponInventory.grenadeManager.amount;
            case "C4" -> "" + player.weaponInventory.c4Manager.amount;
            case "C4 controller" -> "1";
            case "Rocket launcher" -> "" + player.weaponInventory.rocketLauncher.mag;
            default -> "";
        };
        // Determine the X coordinate for the text
        int x = 683 - metrics.stringWidth(text) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = 640 - metrics.getHeight() / 2 + metrics.getAscent();
        // Set the font
        g2d.setFont(font);
        // Draw the String
        g2d.setColor(Color.GRAY);
        g2d.drawString(text, x, y);
    }

    private void paintCent(Graphics2D g2d) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Items\\Cent.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2d.drawImage(image, 1200, 25, 35, 35, null);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("" + player.cent + "$", 1250, 55);
    }

    private void paintKill(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("Kill: " + player.kill, 1000, 55);
    }

    private void paintScore(Graphics2D g2d) {
        // Get the FontMetrics
        Font font = new Font("Open Sans", Font.BOLD, 50);
        FontMetrics metrics = g2d.getFontMetrics(font);
        String text = "" + player.score;
        // Determine the X coordinate for the text
        int x = 683 - metrics.stringWidth(text) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = 45 - metrics.getHeight() / 2 + metrics.getAscent();
        // Set the font
        g2d.setFont(font);
        // Draw the String
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
    }

    public void paint(Graphics2D g2d) {
        paintHealthBar(g2d);
        paintAmmoOrAmount(g2d);
        paintScore(g2d);
        paintCent(g2d);
        paintKill(g2d);
    }
}
