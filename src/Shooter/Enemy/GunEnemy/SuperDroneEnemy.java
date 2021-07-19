package Shooter.Enemy.GunEnemy;

import Map.Map;
import Shooter.Enemy.GunInventory;
import Shooter.Enemy.MissileEnemy.MissileEnemySpawner;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;

import java.awt.*;

public class SuperDroneEnemy extends DroneEnemy {
    private final MissileEnemySpawner missileSpawner;

    public SuperDroneEnemy(Map map, Player player, double x, double y, String[] itemName) {
        super(map, player, x, y, itemName);
        size = 60;
        color = Color.ORANGE;
        maxHealth = 400;
        health = maxHealth;
        score = 1500;
        initDeadEffectEmitter();
        initItem(itemName);
        missileSpawner = new MissileEnemySpawner(player, map, 1250);
        gun = new GunInventory(map, this).getGatling();
        scoreInformer = new ScoreInformer(map, this);
        player.weaponInventory.missileLauncher.allTarget.add(this);
    }

    public void update() {
        super.update();
        missileSpawner.setPosition(x + size / 2, y + size / 2);
        if (health > 0) {
            missileSpawner.spawnEnemy();
        }
        missileSpawner.update();
    }

    public void paint(Graphics2D g2d) {
        missileSpawner.paint(g2d);
        super.paint(g2d);
    }
}
