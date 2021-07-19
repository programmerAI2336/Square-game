package Shooter.Enemy.GunEnemy;

import Map.Map;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.GunInventory;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DroneEnemy extends Enemy {
    private BufferedImage pinwheel;

    public DroneEnemy(Map map, Player player, double x, double y,String[] itemName) {
        super(map, player, x, y,itemName);
        size = 40;
        color = new Color(255, 102, 255);
        moveVel = 1;
        jumpVel = 0;
        maxHealth = 150;
        health = maxHealth;

        closestDistanceToPlayer = 600;
        shootRange = 1000;
        score = 800;

        gun = new GunInventory(map, this).getGatling();
        initItem(itemName);
        initDeadEffectEmitter();
        scoreInformer = new ScoreInformer(map, this);
        this.player.weaponInventory.missileLauncher.allTarget.add(this);
    }

    {
        try {
            pinwheel = ImageIO.read(new File("Assets\\2D models\\Equipments\\Pinwheel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void followPlayer() {
        super.followPlayer();
        double distanceToPlayer = Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2));
        if (distanceToPlayer <= closestDistanceToPlayer) {
            if (velY >= -0.5) {
                velY -= 0.05;
            }
        }
    }

    public void update() {
        followPlayer();
        super.update();
    }

    private void paintPinwheel(Graphics2D g2d) {
        if (health > 0) {
            g2d.drawImage(pinwheel, (int) (map.x + x - (pinwheel.getWidth() - size) / 2), (int) (map.y + y - 16), pinwheel.getWidth(), pinwheel.getHeight(), null);
        }
    }

    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        paintPinwheel(g2d);
    }
}
