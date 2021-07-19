package Shooter.Enemy.GunEnemy;

import Map.Map;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.GunInventory;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;

import java.awt.*;

public class TankEnemy extends Enemy {
    public TankEnemy(Map map, Player player, double x, double y, String[] itemName) {
        super(map, player, x, y, itemName);
        size = 50;
        color = Color.RED;
        moveVel = 0.6;
        jumpVel = 1.2;
        maxHealth = 200;
        health = maxHealth;

        score = 200;
        closestDistanceToPlayer = 100;
        shootRange = 400;
        canAvoidAmmo = true;
        gun = new GunInventory(map, this).getRandomGun();
        initItem(itemName);
        initDeadEffectEmitter();
        scoreInformer = new ScoreInformer(map, this);
        this.player.weaponInventory.missileLauncher.allTarget.add(this);
    }
}
