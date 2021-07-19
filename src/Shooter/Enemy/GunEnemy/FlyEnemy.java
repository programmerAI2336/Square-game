package Shooter.Enemy.GunEnemy;

import Map.Map;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.GunInventory;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;

import java.awt.*;

public class FlyEnemy extends Enemy {
    private int colorAlpha = 255;

    public FlyEnemy(Map map, Player player, double x, double y,String[] itemName) {
        super(map, player, x, y,itemName);

        size = 25;
        color = new Color(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), colorAlpha);
        moveVel = 0.5;
        jumpVel = 0;
        maxHealth = 60;
        health = maxHealth;

        closestDistanceToPlayer = 50;
        shootRange = 400;
        score = 300;

        gun = new GunInventory(map, this).getRandomGun();
        initItem(itemName);
        initDeadEffectEmitter();
        scoreInformer = new ScoreInformer(map, this);
        this.player.weaponInventory.missileLauncher.allTarget.add(this);
    }

    protected void followPlayer() {
        if (health > 0 && player.health > 0) {
            double distanceToPlayer = Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2));
            if (distanceToPlayer > closestDistanceToPlayer) {
                if (player.x > x) {
                    move(0.04f);
                }
                if (player.x < x) {
                    move(-0.04f);
                }
                if (velY > 1 || y > player.y) {
                    int num = random.nextInt(100);
                    if (num >= 80) {
                        velY -= 0.1;
                    }
                }
            }
        }
    }

    private int flickerTimer;
    private static final int FLICKER_TIME = 250;
    private static final int DARK_TIME = 750;

    private void flicker() {
        if (health > 0) {
            flickerTimer++;
            if (flickerTimer <= FLICKER_TIME) {
                if (colorAlpha >= 255 / FLICKER_TIME) {
                    colorAlpha -= 255 / FLICKER_TIME;
                }
            } else if (flickerTimer >= FLICKER_TIME + DARK_TIME && flickerTimer <= FLICKER_TIME + DARK_TIME + FLICKER_TIME) {
                if (colorAlpha <= 255 - 255 / FLICKER_TIME) {
                    colorAlpha += 255 / FLICKER_TIME;
                }
            } else if (flickerTimer > FLICKER_TIME + DARK_TIME + FLICKER_TIME) {
                flickerTimer = 0;
            }
            color = new Color(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.yellow.getBlue(), colorAlpha);
        }
    }

    public void update() {
        followPlayer();
        super.update();
        flicker();
    }
}
