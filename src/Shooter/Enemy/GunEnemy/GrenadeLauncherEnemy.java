package Shooter.Enemy.GunEnemy;

import Map.Map;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;
import Weapon.Gun.GrenadeLauncher.ExplosiveAmmo;
import Weapon.Gun.GrenadeLauncher.GrenadeLauncher;
import Weapon.Gun.Gun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GrenadeLauncherEnemy extends Enemy {
    private GrenadeLauncher grenadeLauncher;

    public GrenadeLauncherEnemy(Map map, Player player, double x, double y, String[] itemName) {
        super(map, player, x, y, itemName);
        color = Color.ORANGE;
        size = 40;
        maxHealth = 150;
        health = maxHealth;
        moveVel = 0.6;
        jumpVel = 1;
        closestDistanceToPlayer = 600;
        score = 500;
        canAvoidAmmo = true;
        initDeadEffectEmitter();
        initItem(itemName);
        try {
            grenadeLauncher = new GrenadeLauncher(map, this, 50, 17, 500, 0, 1, 1, 150, Gun.INFINITE_AMMO);
            grenadeLauncher.initDisToRotateCenter(15, 7);
            grenadeLauncher.initAmmo(this.color, 10, 3.5, 100, 3, "");
            grenadeLauncher.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Grenade-launcher.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Grenade-launcher.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scoreInformer = new ScoreInformer(map, this);
        player.weaponInventory.missileLauncher.allTarget.add(this);
    }

    public void shootPlayer() {
        if (health > 0 && player.health > 0) {
            double D = Math.abs(player.x + player.size / 2 - x - size / 2);
            double H = y + size / 2 - player.y - player.size / 2;

            double a = (Math.pow(D, 2) + Math.pow(H, 2)) / Math.pow(grenadeLauncher.ammoVel, 2);
            double b = Math.pow(0.015, 2) / (4 * Math.pow(grenadeLauncher.ammoVel, 2));
            double c = 1 - 0.015 * H / Math.pow(grenadeLauncher.ammoVel, 2);

            double delta = Math.pow(c, 2) - 4 * a * b;
            if (delta >= 0) {
                double t = Math.sqrt((c - Math.sqrt(delta)) / (2 * b));
                double sin = (H + 1.0 / 2.0 * 0.015 * Math.pow(t, 2)) / (grenadeLauncher.ammoVel * t);
                if (player.x + player.size / 2 > x + size / 2) {
                    grenadeLauncher.angle = Math.toRadians(360) - Math.asin(sin);
                } else {
                    grenadeLauncher.angle = Math.toRadians(180) + Math.asin(sin);
                }
                grenadeLauncher.shoot();
            }
        }
    }

    public void damagePlayer() {
        if (player.health > 0) {
            for (ExplosiveAmmo ammo : grenadeLauncher.explosiveAmmo) {
                if (ammo.getBound().intersects(player.getBound())) {
                    ammo.appear = false;
                }
                if (ammo.explosionTimer == 1) {
                    double distanceToAmmo = Math.sqrt(Math.pow(player.x + player.size / 2 - ammo.x - ammo.size / 2, 2) + Math.pow(player.y + player.size / 2 - ammo.y - ammo.size / 2, 2));

                    if (distanceToAmmo <= ammo.explosionRadius) {

                        player.takeDamage((int) (grenadeLauncher.damage * (1 - distanceToAmmo / ammo.explosionRadius)));

                        double fallBackAngle = 0;
                        double angle = Math.atan((player.y + player.size / 2 - ammo.y - ammo.size / 2) / (player.x + player.size / 2 - ammo.x - ammo.size / 2));
                        //calculating fall back angle
                        if (player.x + player.size / 2 > ammo.x + ammo.size / 2) {
                            if (player.y + player.size / 2 > ammo.y + ammo.size / 2) {
                                fallBackAngle = angle;
                            } else {
                                fallBackAngle = angle + Math.toRadians(360);
                            }
                        } else if (player.x + player.size / 2 < ammo.x + ammo.size / 2) {
                            fallBackAngle = angle + Math.toRadians(180);
                        }

                        player.velX = ammo.power * (1 - distanceToAmmo / ammo.explosionRadius) * Math.cos(fallBackAngle);
                        player.velY = ammo.power * (1 - distanceToAmmo / ammo.explosionRadius) * Math.sin(fallBackAngle);
                    }
                }
            }
        }
    }

    public void update() {
        super.update();
        grenadeLauncher.update();
    }

    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        grenadeLauncher.paintAmmo(g2d);
        grenadeLauncher.paintGun(g2d);
    }
}
