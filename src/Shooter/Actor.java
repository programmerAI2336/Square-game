package Shooter;

import Map.*;
import ParticleSystem.ParticleEmitter;
import Audio.Sound;

import java.awt.*;

public class Actor {
    public Map map;

    public double x, y;
    public double size;
    public double velX, velY;
    public double moveVel;
    public Color color;

    public int maxJumpTime;
    public int jumpTime;
    public double jumpVel;

    public double health;
    public int maxHealth;
    public int score;
    public Physic physic;

    public ParticleEmitter deadEffectEmitter;

    public Actor(Map map) {
        this.map = map;
        physic = new Physic(this);
    }

    public void initDeadEffectEmitter() {
        deadEffectEmitter = new ParticleEmitter(map);
        deadEffectEmitter.setAmountOfParticle(50);
        deadEffectEmitter.setParticleColor(color);
        deadEffectEmitter.setAngle(360, 0);
        deadEffectEmitter.setSizeBound(20, 10);
        deadEffectEmitter.setAge(1250, 750);
        deadEffectEmitter.setParticleShape("Rectangle");
        deadEffectEmitter.setVelocity(1, 0.5);
    }

    public void jump(String soundPathName) {
        if (health > 0) {
            if (jumpTime < maxJumpTime) {
                velY = -jumpVel;
                jumpTime++;
                if (!soundPathName.equals("")) {
                    Sound.play("Assets\\Sounds\\Jump.wav");
                }
            }
        }
    }

    public void move(float acceleration) {
        if (health > 0) {
            if (Math.abs(velX) < moveVel) {
                velX += acceleration;
            } else {
                if (velX > 0) {
                    velX = moveVel;
                }
                if (velX < 0) {
                    velX = -moveVel;
                }
            }
        }
    }

    public void takeDamage(int damage) {
        if (health > 0) {
            health -= damage;
        }
    }

    private void dieByMap() {
        if (y >= map.length) {
            health = 0;
        }
        for (Obstacle obstacle : map.obstacle) {
            if (x > obstacle.x - size && x < obstacle.x + obstacle.width && y + size == obstacle.y && obstacle.isLava && health > 0) {
                deadEffectEmitter.setPosition(x + size / 2, y + size / 2);
                deadEffectEmitter.spread();
                health = 0;
                Sound.play("Assets\\Sounds\\Burn.wav");
                break;
            }
        }
    }

    public void update() {
        physic.handle();
        x += velX;
        y += velY;
        dieByMap();
        deadEffectEmitter.update();
    }

    public Rectangle getBound() {
        return new Rectangle((int) x, (int) y, (int) size, (int) size);
    }

    public void paint(Graphics2D g2d) {
        if (health > 0) {
            g2d.setColor(color);
            g2d.fillRect((int) (map.x + x), (int) (map.y + y), (int) size, (int) size);
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 25));
            g2d.fillOval((int) (map.x + x - size), (int) (map.y + y - size), (int) size * 3, (int) (size * 3));
        }
        deadEffectEmitter.paint(g2d);
    }
}
