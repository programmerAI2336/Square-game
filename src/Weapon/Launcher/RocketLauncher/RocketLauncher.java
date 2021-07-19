package Weapon.Launcher.RocketLauncher;

import Map.Map;
import Shooter.Actor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RocketLauncher {
    protected Map map;
    protected final Actor actor;
    protected double width;
    protected double length;
    public double angle;

    public static final double RECOIL = 1;
    public static final int INFINITE_AMMO = -1;

    public int damage;
    protected int amount;
    public int fireInterval;
    public int mag;
    public int maxMag;

    protected BufferedImage leftImage;
    protected BufferedImage rightImage;

    public double rocketVelocity;
    public int rocketExplosionRadius;
    public int rocketPower;

    public final ArrayList<Rocket> rocket = new ArrayList<>();

    public RocketLauncher(Map map, Actor actor, double width, double length, int amount, int freInterval, int damage, int maxMag) {
        this.map = map;
        this.actor = actor;
        this.width = width;
        this.length = length;
        this.amount = amount;
        this.fireInterval = freInterval;
        this.damage = damage;
        this.maxMag = maxMag;
        try {
            leftImage = ImageIO.read(new File("Assets\\2D models\\Weapons\\Launcher\\Left launcher\\Rocket launcher.png"));
            rightImage = ImageIO.read(new File("Assets\\2D models\\Weapons\\Launcher\\Right launcher\\Rocket launcher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initRocket(double velocity, int explosionRadius, int power) {
        this.rocketVelocity = velocity;
        this.rocketExplosionRadius = explosionRadius;
        this.rocketPower = power;
    }

    public void reset(Map map) {
        this.map = map;
        rocket.clear();
        mag = 0;
        angle = 0;
    }

    public void reload() {
        mag = maxMag;
    }

    public void shoot() {
        if (mag > 0) {
            if (rocket.isEmpty() || rocket.get(rocket.size() - 1).timer > fireInterval) {
                mag--;
                for (int i = 0; i < amount; i++) {
                    rocket.add(new Rocket(map, rocketVelocity, rocketExplosionRadius, rocketPower));
                }
            }
            for (int i = rocket.size() - amount; i <= rocket.size() - 1; i++) {
                if (rocket.get(i).timer == 0) {
                    rocket.get(i).fly(actor.x + actor.size / 2, actor.y + actor.size / 2, angle);
                }
            }
        }
    }

    private void removeRocket() {
        rocket.removeIf(rocket -> !rocket.appear && rocket.timer > fireInterval && rocket.explosionFireEmitter.particle.isEmpty() && rocket.explosionSmokeEmitter.particle.isEmpty());
    }

    public void update() {
        for (Rocket rocket : rocket) {
            rocket.update();
        }
        removeRocket();
    }

    public void paintLauncher(Graphics2D g2d) {
        double rotateCenterX = actor.x + actor.size / 2;
        double rotateCenterY = actor.y + actor.size / 2;
        if (actor.health > 0) {
            AffineTransform gun = new AffineTransform();
            if (angle >= Math.toRadians(270) || angle < Math.toRadians(90)) {
                g2d.rotate(angle, actor.map.x + rotateCenterX, actor.map.y + rotateCenterY);
                g2d.drawImage(rightImage, (int) (map.x + rotateCenterX - width / 2), (int) (map.y + rotateCenterY - length / 2), (int) (width), (int) length, null);
            } else {
                g2d.rotate(angle - Math.toRadians(180), actor.map.x + rotateCenterX, actor.map.y + rotateCenterY);
                g2d.drawImage(leftImage, (int) (map.x + rotateCenterX + -width / 2), (int) (map.y + rotateCenterY - length / 2), (int) (width), (int) length, null);
            }
            g2d.setTransform(gun);
        }
    }

    public void paintRocket(Graphics2D g2d) {
        for (int i = 0; i < rocket.size(); i++) {
            rocket.get(i).paint(g2d);
        }
    }
}
