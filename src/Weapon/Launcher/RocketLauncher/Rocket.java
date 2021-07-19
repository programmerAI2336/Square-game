package Weapon.Launcher.RocketLauncher;


import Map.*;
import ParticleSystem.ParticleEmitter;
import Audio.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rocket {
    protected final Map map;
    public double x;
    public double y;
    public double width = 28, length = 9;
    protected double angle;
    protected double velX, velY;
    public int timer;
    public boolean appear = true;

    public final double velocity;
    public final int explosionRadius;
    public final int power;

    public int explosionTimer;

    public ParticleEmitter explosionFireEmitter;
    public ParticleEmitter explosionSmokeEmitter;
    private ParticleEmitter flySmokeEmitter;
    private ParticleEmitter flyFireEmitter;
    protected BufferedImage image;

    public Rocket(Map map, double velocity, int explosionRadius, int power) {
        this.map = map;
        this.velocity = velocity;
        this.explosionRadius = explosionRadius;
        this.power = power;
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Weapons\\Launcher\\Rocket.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initExplosionFireEmitter();
        initExplosionSmokeEmitter();
        initFlySmokeEmitter();
        initFlyFireEmitter();
    }

    private void initExplosionSmokeEmitter() {
        explosionSmokeEmitter = new ParticleEmitter(map);
        explosionSmokeEmitter.setAge(1000, 750);
        explosionSmokeEmitter.setAngle(360, 0);
        explosionSmokeEmitter.setAmountOfParticle(30);
        explosionSmokeEmitter.setParticleColor(new Color(0x4DFFFFFF, true));
        explosionSmokeEmitter.setSizeBound(40, 10);
        explosionSmokeEmitter.setParticleShape("Circle");
        explosionSmokeEmitter.setVelocity(2, 1);
    }

    private void initExplosionFireEmitter() {
        explosionFireEmitter = new ParticleEmitter(map);
        explosionFireEmitter.setAge(1250, 750);
        explosionFireEmitter.setAngle(360, 0);
        explosionFireEmitter.setAmountOfParticle(40);
        explosionFireEmitter.setParticleColor(new Color(0xFFAA00));
        explosionFireEmitter.setSizeBound(40, 10);
        explosionFireEmitter.setParticleShape("Circle");
        explosionFireEmitter.setVelocity(3, 2);
    }

    private void initFlySmokeEmitter() {
        flySmokeEmitter = new ParticleEmitter(map);
        flySmokeEmitter.setAge(1250, 250);
        flySmokeEmitter.setAmountOfParticle(1);
        flySmokeEmitter.setParticleColor(new Color(0x4DFFFFFF, true));
        flySmokeEmitter.setSizeBound(10, 5);
        flySmokeEmitter.setParticleShape("Rectangle");
        flySmokeEmitter.setVelocity(1, 0.5);
    }

    private void initFlyFireEmitter() {
        flyFireEmitter = new ParticleEmitter(map);
        flyFireEmitter.setAge(500, 250);
        flyFireEmitter.setAmountOfParticle(1);
        flyFireEmitter.setParticleColor(new Color(0xFF7700));
        flyFireEmitter.setSizeBound(10, 5);
        flyFireEmitter.setParticleShape("Rectangle");
        flyFireEmitter.setVelocity(0.1, 0);
    }

    private void explode() {
        if (!appear) {
            explosionTimer++;
        }
        if (explosionTimer == 1) {
            explosionFireEmitter.setPosition((int) (x + width / 2), (int) (y + length / 2));
            explosionFireEmitter.spread();
            explosionSmokeEmitter.setPosition((int) (x + width / 2), (int) (y + length / 2));
            explosionSmokeEmitter.spread();
        }
    }

    public void update() {
        x += velX;
        y += velY;
        timer++;
        appear();
        explode();
        explosionFireEmitter.update();
        explosionSmokeEmitter.update();
        flySmokeEmitter.update();
        flyFireEmitter.update();
    }

    private void appear() {
        if (appear) {
            flySmokeEmitter.setPosition(x + width / 2 - width / 2 * Math.cos(angle), y + length / 2 - width / 2 * Math.sin(angle));
            flySmokeEmitter.setAngle((int) Math.toDegrees(angle) - 160, (int) Math.toDegrees(angle) - 200);
            flySmokeEmitter.spreadRepeatly(true, 5);
            flyFireEmitter.setPosition(x + width / 2 - width / 2 * Math.cos(angle), y + length / 2 - width / 2 * Math.sin(angle));
            flyFireEmitter.setAngle((int) Math.toDegrees(angle) - 160, (int) Math.toDegrees(angle) - 200);
            flyFireEmitter.spreadRepeatly(true, 5);
        }
        for (Obstacle obstacle : map.obstacle) {
            if (getBound().intersects(obstacle.getBound())) {
                appear = false;
            }
        }
        if (!appear) {
            if(velX != 0 || velY != 0) {
                Sound.play("Assets\\Sounds\\Explosion1.wav");
                velX = 0;
                velY = 0;
            }
        }
    }

    public void fly(double xPos, double yPos, double angle) {
        this.angle = angle;
        x = xPos - width / 2;
        y = yPos - length / 2;
        velX = velocity * Math.cos(angle);
        velY = velocity * Math.sin(angle);
        Sound.play("Assets\\Sounds\\Launch.wav");
    }

    public Rectangle getBound() {
        return new Rectangle((int) x, (int) y, (int) width, (int) length);
    }

    public void paint(Graphics2D g2d) {
        if (appear) {
            AffineTransform ammo = new AffineTransform();
            g2d.rotate(angle, map.x + x + width / 2, map.y + y + length / 2);
            g2d.drawImage(image, (int) (map.x + x), (int) (map.y + y), (int) width, (int) length, null);
            g2d.setTransform(ammo);
        }
        flySmokeEmitter.paint(g2d);
        flyFireEmitter.paint(g2d);
        explosionFireEmitter.paint(g2d);
        explosionSmokeEmitter.paint(g2d);
    }
}
