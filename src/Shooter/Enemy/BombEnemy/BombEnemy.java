package Shooter.Enemy.BombEnemy;

import Map.Map;
import ParticleSystem.ParticleEmitter;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;
import Audio.Sound;

import java.awt.*;

public class BombEnemy extends Enemy {
    private final int explosionRadius;
    private final int damage;
    private final int power;
    private ParticleEmitter fireEmitter;
    private ParticleEmitter smokeEmitter;

    public BombEnemy(Map map, Player player, double x, double y, int explosionRadius, int damage, int power, String[] itemName) {
        super(map, player, x, y, itemName);
        this.explosionRadius = explosionRadius;
        this.damage = damage;
        this.power = power;
        size = 30;
        color = new Color(0x80FF00);
        moveVel = 0.75;
        jumpVel = 1;
        maxHealth = 100;
        health = maxHealth;
        score = 300;
        initItem(itemName);
        initDeadEffectEmitter();
        initFireEmitter();
        initSmokeEmitter();
        scoreInformer = new ScoreInformer(map, this);
        player.weaponInventory.missileLauncher.allTarget.add(this);
    }

    private void initFireEmitter() {
        fireEmitter = new ParticleEmitter(map);
        fireEmitter.setAge(1250, 750);
        fireEmitter.setAngle(360, 0);
        fireEmitter.setAmountOfParticle(50);
        fireEmitter.setParticleColor(new Color(0x4CFF00));
        fireEmitter.setSizeBound(40, 20);
        fireEmitter.setParticleShape("Circle");
        fireEmitter.setVelocity(3, 1);
    }

    private void initSmokeEmitter() {
        smokeEmitter = new ParticleEmitter(map);
        smokeEmitter.setAge(2500, 1250);
        smokeEmitter.setAngle(360, 0);
        smokeEmitter.setAmountOfParticle(50);
        smokeEmitter.setParticleColor(new Color(0x4DFFFFFF, true));
        smokeEmitter.setSizeBound(40, 20);
        smokeEmitter.setParticleShape("Circle");
        smokeEmitter.setVelocity(1, 0.5);
    }

    protected void followPlayer() {
        if (player.health > 0 && health > 0) {
            if (player.x + player.size / 2 > x + size / 2) {
                move(0.04f);
            }
            if (player.x + player.size / 2 < x + size / 2) {
                move(-0.04f);
            }
            if (player.y < y - player.size) {
                jump("");
            }
            if (velY >= 0.5) {
                jumpTime = 0;
            }
        }
    }

    private int timer;
    private int explosionTimer;
    private boolean explode;

    private void explode() {
        timer++;
        if ((timer == 2500 && !explode && health > 0) || (getBound().intersects(player.getBound()) && !explode && health > 0) || health <= 0) {
            health = 0;
            explode = true;
        }
        if (explode) {
            explosionTimer++;
        }
        if (explosionTimer == 1) {
            fireEmitter.setPosition(x + size / 2, y + size / 2);
            fireEmitter.spread();
            smokeEmitter.setPosition(x + size / 2, y + size / 2);
            smokeEmitter.spread();
        }
    }

    public void damagePlayer() {
        if (explode && explosionTimer == 1) {
            Sound.play("Assets\\Sounds\\Explosion.wav");
            double distanceToPlayer = Math.sqrt(Math.pow(player.x + player.size / 2 - x - size / 2, 2) + Math.pow(player.y + player.size / 2 - y - size / 2, 2));
            if (distanceToPlayer <= explosionRadius) {
                player.takeDamage((int) (damage * (1 - (distanceToPlayer / explosionRadius))));

                double fallBackAngle = 0;
                double angle = Math.atan((y + size / 2 - player.y - player.size / 2) / (x + size / 2 - player.x - player.size / 2));
                //calculating fall back angle
                if (player.x + player.size / 2 > x + size / 2) {
                    if (player.y + player.size / 2 > y + size / 2) {
                        fallBackAngle = angle;
                    } else {
                        fallBackAngle = angle + Math.toRadians(360);
                    }
                } else if (player.x + player.size / 2 < x + size / 2) {
                    fallBackAngle = angle + Math.toRadians(180);
                }

                //fall back
                player.velX = power * (1 - distanceToPlayer / explosionRadius) * Math.cos(fallBackAngle);
                player.velY = power * (1 - distanceToPlayer / explosionRadius) * Math.sin(fallBackAngle);
            }
        }
    }

    public void update() {
        followPlayer();
        super.update();
        damagePlayer();
        explode();
        smokeEmitter.update();
        fireEmitter.update();
    }

    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        if (item != null) {
            item.paint(g2d);
        }
        fireEmitter.paint(g2d);
        smokeEmitter.paint(g2d);
    }
}
