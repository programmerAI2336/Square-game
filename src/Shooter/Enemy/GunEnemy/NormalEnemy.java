package Shooter.Enemy.GunEnemy;

import Map.Map;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.GunInventory;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;

import java.awt.*;

public class NormalEnemy extends Enemy {
    public NormalEnemy(Map map, Player player, double x, double y, String[] itemName) {
        super(map, player, x, y, itemName);
        size = 36;
        color = Color.PINK;
        moveVel = 0.7;
        jumpVel = 1.2;
        maxHealth = 100;
        health = maxHealth;
        closestDistanceToPlayer = 200;
        shootRange = 500;
        score = 100;
        canAvoidAmmo = true;
        gun = new GunInventory(map, this).getRandomGun();
        initItem(itemName);
        initDeadEffectEmitter();
        scoreInformer = new ScoreInformer(map, this);
        this.player.weaponInventory.missileLauncher.allTarget.add(this);
    }
}
