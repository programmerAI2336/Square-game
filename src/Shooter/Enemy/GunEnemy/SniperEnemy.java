package Shooter.Enemy.GunEnemy;

import Map.Map;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.GunInventory;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SniperEnemy extends Enemy {
    public SniperEnemy(Map map, Player player, double x, double y,String[] itemName) {
        super(map, player, x, y,itemName);
        size = 40;
        color = Color.GREEN;
        moveVel = 0;
        jumpVel = 0;
        maxHealth = 150;
        health = maxHealth;

        score = 500;
        closestDistanceToPlayer = 0;
        shootRange = 1000;

        gun = new GunInventory(map, this).getSniper();
        initItem(itemName);
        initDeadEffectEmitter();
        scoreInformer = new ScoreInformer(map, this);
        this.player.weaponInventory.missileLauncher.allTarget.add(this);
    }

    private void paintLaze(Graphics2D g2d){
        if(health > 0){
            AffineTransform laze2 = new AffineTransform();
            g2d.rotate(gun.angle, map.x + x + size / 2, map.y + y + size / 2);
            g2d.setColor(Color.RED);
            g2d.fillRect((int) (map.x + x + size / 2), (int) (map.y + y + size / 2), 300, 2);
            g2d.setTransform(laze2);
        }
    }

    public void paint(Graphics2D g2d){
        paintLaze(g2d);
        super.paint(g2d);
    }
}
