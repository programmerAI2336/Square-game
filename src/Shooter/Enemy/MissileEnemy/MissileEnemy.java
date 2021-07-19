package Shooter.Enemy.MissileEnemy;

import Map.Map;
import ParticleSystem.ParticleEmitter;
import Shooter.Enemy.Enemy;
import Shooter.Enemy.ScoreInformer;
import Shooter.PlayerController.Player;
import Audio.Sound;
import Map.*;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MissileEnemy extends Enemy {
    private double angle = Math.toRadians(90);
    private final double velocity;
    private final int explosionRadius;
    private final int damage;
    private final int power;
    private ParticleEmitter fireEmitter;
    private ParticleEmitter smokeEmitter;
    private ParticleEmitter flySmokeEmitter;

    public MissileEnemy(Map map, Player player, double x, double y, String[] itemName) {
        super(map, player, x, y, itemName);
        size = 40;
        color = Color.LIGHT_GRAY;
        velocity = 1.5;
        explosionRadius = 125;
        damage = 150;
        power = 3;
        score = 300;
        maxHealth = 100;
        health = maxHealth;
        initFireEmitter();
        initSmokeEmitter();
        initFlySmokeEmitter();
        initDeadEffectEmitter();
        initItem(itemName);
        scoreInformer = new ScoreInformer(map, this);
        Sound.play("Assets\\Sounds\\Launch.wav");
        player.weaponInventory.missileLauncher.allTarget.add(this);
    }

    private void initFireEmitter() {
        fireEmitter = new ParticleEmitter(map);
        fireEmitter.setAge(1250, 750);
        fireEmitter.setAngle(360, 0);
        fireEmitter.setAmountOfParticle(50);
        fireEmitter.setParticleColor(new Color(0xFF9900));
        fireEmitter.setSizeBound(30, 5);
        fireEmitter.setParticleShape("Circle");
        fireEmitter.setVelocity(4, 1);
    }

    private void initSmokeEmitter() {
        smokeEmitter = new ParticleEmitter(map);
        smokeEmitter.setAge(2500, 1250);
        smokeEmitter.setAngle(360, 0);
        smokeEmitter.setAmountOfParticle(50);
        smokeEmitter.setParticleColor(new Color(0x4DFFFFFF, true));
        smokeEmitter.setSizeBound(40, 20);
        smokeEmitter.setParticleShape("Circle");
        smokeEmitter.setVelocity(2, 1);
    }

    private void initFlySmokeEmitter() {
        flySmokeEmitter = new ParticleEmitter(map);
        flySmokeEmitter.setAge(2500, 1250);
        flySmokeEmitter.setAmountOfParticle(5);
        flySmokeEmitter.setParticleColor(new Color(0x4DFFFFFF, true));
        flySmokeEmitter.setSizeBound(20, 10);
        flySmokeEmitter.setParticleShape("Circle");
        flySmokeEmitter.setVelocity(2, 0.5);
    }

    protected void followPlayer() {
        double flyAngle = 0;
        if (health > 0 && player.health > 0) {
            double angle = Math.atan((player.y + player.size / 2 - y - size / 2) / (player.x + player.size / 2 - x - size / 2));
            if (player.x + player.size / 2 > x + size / 2) {
                if (player.y + player.size / 2 > y + size / 2) {
                    flyAngle = angle;
                } else {
                    flyAngle = angle + Math.toRadians(360);
                }
            } else if (player.x + player.size / 2 < x + size / 2) {
                flyAngle = angle + Math.toRadians(180);
            }

            double rotateVel = velocity / 100;
            if (Math.abs(flyAngle - this.angle) < Math.toRadians(180)) {
                if (flyAngle > this.angle) {
                    this.angle += rotateVel;
                }
                if (flyAngle < this.angle) {
                    this.angle -= rotateVel;
                }
            } else {
                if (flyAngle < Math.toRadians(180) && this.angle > Math.toRadians(180)) {
                    this.angle += rotateVel;
                    if (this.angle > Math.toRadians(360)) {
                        this.angle -= Math.toRadians(360);
                    }
                }
                if (flyAngle > Math.toRadians(180) && this.angle < Math.toRadians(180)) {
                    this.angle -= rotateVel;
                    if (this.angle < 0) {
                        this.angle += Math.toRadians(360);
                    }
                }
            }

            velX = velocity * Math.cos(this.angle);
            velY = velocity * Math.sin(this.angle);
        }
    }

    private void appear() {
        for (Obstacle obstacle : map.obstacle) {
            if (obstacle.getBound().intersects(new Rectangle((int) (x - 1), (int) (y - 1), (int) (size + 2), (int) (size + 2)))) {
                health = 0;
            }
        }
        if (player.getBound().intersects(getBound())) {
            health = 0;
        }
    }

    private int timer;

    private void releaseSmoke() {
        if (health > 0) {
            flySmokeEmitter.setPosition(x + size / 2 - size / 2 * Math.cos(angle), y + size / 2 - size / 2 * Math.sin(angle));
            flySmokeEmitter.setAngle((int) Math.toDegrees(angle) - 170, (int) Math.toDegrees(angle) - 190);
            flySmokeEmitter.spreadRepeatly(true, 25);
        }
    }

    private void explode() {
        if (health <= 0) {
            timer++;
        }
        if (timer == 1) {
            fireEmitter.setPosition(x + size / 2, y + size / 2);
            fireEmitter.spread();
            smokeEmitter.setPosition(x + size / 2, y + size / 2);
            smokeEmitter.spread();
            deadEffectEmitter.setPosition(x + size / 2, y + size / 2);
            deadEffectEmitter.spread();
            Sound.play("Assets\\Sounds\\Explosion.wav");
        }
    }

    public void damagePlayer() {
        if (player.health > 0 && timer == 1) {
            double distanceToPlayer = Math.sqrt(Math.pow(player.x + player.size / 2 - x - size / 2, 2) + Math.pow(player.y + player.size / 2 - y - size / 2, 2));
            if (distanceToPlayer <= explosionRadius) {
                player.takeDamage((int) (damage * (1 - distanceToPlayer / explosionRadius)));

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
            if (player.health <= 0) {
                player.deadEffectEmitter.setPosition(player.x + player.size / 2, player.y + player.size / 2);
                player.deadEffectEmitter.spread();
            }
        }
    }

    public void update() {
        followPlayer();
        explode();
        damagePlayer();
        appear();
        releaseSmoke();
        super.update();
        fireEmitter.update();
        smokeEmitter.update();
        flySmokeEmitter.update();
    }

    public Rectangle getBound() {
        return new Rectangle((int) x, (int) y, (int) size, (int) size);
    }

    public void paint(Graphics2D g2d) {
        scoreInformer.paint(g2d);
        if (health > 0) {
            AffineTransform body = new AffineTransform();
            g2d.rotate(angle, map.x + x + size / 2, map.y + y + size / 2);
            g2d.setColor(color);
            g2d.fillPolygon(new int[]{(int) (map.x + x), (int) (map.x + x + size), (int) (map.x + x)}, new int[]{(int) (map.y + y), (int) (map.y + y + size / 2), (int) (map.y + y + size)}, 3);
            g2d.setTransform(body);
        }
        if (item != null) {
            item.paint(g2d);
        }
        fireEmitter.paint(g2d);
        smokeEmitter.paint(g2d);
        flySmokeEmitter.paint(g2d);
        deadEffectEmitter.paint(g2d);
    }
}
