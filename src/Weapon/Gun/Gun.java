package Weapon.Gun;

import Map.Map;
import Shooter.Actor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Gun {
    public Map map;
    public final Actor actor;
    private final double width, length;
    public double angle;

    private double disToRotateCenterX;
    private double disToRotateCenterY;

    public int fireInterval;
    public double deflection;
    public final int amount;
    public final double recoil;
    public int damage;

    public int mag;
    public int maxMag;

    public static final int INFINITE_AMMO = -1;

    public Color ammoColor;
    public double ammoSize;
    public double ammoVel;

    protected String soundPathName;

    private BufferedImage leftGun, rightGun;
    public final ArrayList<Ammo> ammo = new ArrayList<>();
    public static final Random random = new Random();

    public Gun(Map map, Actor actor, double width, double length, int fireInterval, double deflection, int amount, double recoil, int damage, int maxMag) {
        this.map = map;
        this.actor = actor;
        this.width = width;
        this.length = length;
        this.fireInterval = fireInterval;
        this.deflection = deflection;
        this.amount = amount;
        this.recoil = recoil;
        this.damage = damage;
        this.maxMag = maxMag;
        this.mag = maxMag;
    }


    public void initDisToRotateCenter(double disX, double disY) {
        disToRotateCenterX = disX;
        disToRotateCenterY = disY;
    }

    public void initAmmo(Color color, double size, double vel, String soundPathName) {
        ammoColor = color;
        ammoSize = size;
        ammoVel = vel;
        this.soundPathName = soundPathName;
    }

    public void initImage(BufferedImage leftGun, BufferedImage rightGun) {
        this.leftGun = leftGun;
        this.rightGun = rightGun;
    }

    public void reset(Map map) {
        this.map = map;
        ammo.clear();
        if (mag != INFINITE_AMMO) {
            mag = 0;
        }
        angle = 0;
    }

    public void reload() {
        mag = maxMag;
    }

    public void shoot() {
        if (mag > 0 || mag == INFINITE_AMMO) {
            if (ammo.isEmpty() || ammo.get(ammo.size() - 1).timer > fireInterval) {
                //reduce mag
                if (mag > 0) {
                    mag--;
                }
                //add new ammo
                for (int i = 0; i < amount; i++) {
                    ammo.add(new Ammo(map, ammoColor, ammoSize, ammoVel));
                }
            }
            double[] allAngle = new double[(int) (200 * deflection + 1)];
            for (int j = 0; j < allAngle.length; j++) {
                allAngle[j] = (float) j / 100 - deflection;
            }
            //fly new ammo
            for (int i = ammo.size() - amount; i <= ammo.size() - 1; i++) {
                if (ammo.get(i).timer == 0) {
                    int index = random.nextInt(allAngle.length);
                    ammo.get(i).fly(actor.x + actor.size / 2, actor.y + actor.size / 2, angle + allAngle[index],soundPathName);
                }
            }
        }
    }

    public void removeAmmo() {
        ammo.removeIf(ammo -> !ammo.appear && ammo.timer > fireInterval && ammo.pieceEmitter.particle.isEmpty());
    }

    public void update() {
        for (Ammo ammo : ammo) {
            ammo.update();
        }
        removeAmmo();
    }

    public void paintGun(Graphics2D g2d) {
        double rotateCenterX = actor.x + actor.size / 2;
        double rotateCenterY = actor.y + actor.size / 2;
        if (actor.health > 0) {
            AffineTransform gun = new AffineTransform();
            if (angle >= Math.toRadians(270) || angle < Math.toRadians(90)) {
                g2d.rotate(angle, actor.map.x + rotateCenterX, actor.map.y + rotateCenterY);
                g2d.drawImage(rightGun, (int) (map.x + rotateCenterX - disToRotateCenterX), (int) (map.y + rotateCenterY - disToRotateCenterY), (int) (width), (int) length, null);
            } else {
                g2d.rotate(angle - Math.toRadians(180), actor.map.x + rotateCenterX, actor.map.y + rotateCenterY);
                g2d.drawImage(leftGun, (int) (map.x + rotateCenterX + disToRotateCenterX - width), (int) (map.y + rotateCenterY - disToRotateCenterY), (int) (width), (int) length, null);
            }
            g2d.setTransform(gun);
        }
    }

    public void paintAmmo(Graphics2D g2d) {
        for (int i = 0; i < ammo.size(); i++) {
            ammo.get(i).paint(g2d);
        }
    }
}
