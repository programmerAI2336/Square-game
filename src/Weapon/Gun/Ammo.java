package Weapon.Gun;

import Map.*;
import ParticleSystem.ParticleEmitter;
import Audio.Sound;

import java.awt.*;

public class Ammo {
    public final Map map;
    public double x;
    public double y;
    public final double size;
    public double angle;
    private double velX;
    public double velY;
    private final double vel;
    private final Color color;
    public int timer;
    public boolean appear = true;
    public ParticleEmitter pieceEmitter;

    public Ammo(Map map, Color color, double size, double vel) {
        this.map = map;
        this.color = color;
        this.size = size;
        this.vel = vel;
        initEmitter();
    }

    private void initEmitter() {
        pieceEmitter = new ParticleEmitter(map);
        pieceEmitter.setAmountOfParticle(10);
        pieceEmitter.setSizeBound(6, 3);
        pieceEmitter.setAge(250, 125);
        pieceEmitter.setParticleColor(color);
        pieceEmitter.setVelocity(1.5, 0.5);
        pieceEmitter.setAngle(360, 0);
        pieceEmitter.setParticleShape("Rectangle");
    }

    public void update() {
        x += velX;
        y += velY;
        timer++;
        handleAppearance();
        pieceEmitter.update();
    }

    private void handleAppearance() {
        for (Obstacle obstacle : map.obstacle) {
            if (getBound().intersects(obstacle.getBound())) {
                appear = false;
            }
        }
        if (!appear) {
            if (velX != 0 || velY != 0) {
                pieceEmitter.setPosition(x + size / 2, y + size / 2);
                pieceEmitter.spread();
                velX = 0;
                velY = 0;
            }
        }
    }

    public void fly(double xPos, double yPos, double angle, String soundPathName) {
        if (timer == 0) {
            this.angle = angle;
            x = xPos - size / 2;
            y = yPos - size / 2;
            velX = vel * Math.cos(angle);
            velY = vel * Math.sin(angle);
            if (!soundPathName.equals("")) {
                Sound.play(soundPathName);
            }
        }
    }

    public Rectangle getBound() {
        return new Rectangle((int) x, (int) y, (int) size, (int) size);
    }

    public void paint(Graphics2D g2d) {
        if (appear) {
            g2d.setColor(color);
            g2d.fillOval((int) (map.x + x), (int) (map.y + y), (int) size, (int) size);
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
            g2d.fillOval((int) (map.x + x - size), (int) (map.y + y - size), (int) size * 3, (int) (size * 3));
        }
        pieceEmitter.paint(g2d);
    }
}
