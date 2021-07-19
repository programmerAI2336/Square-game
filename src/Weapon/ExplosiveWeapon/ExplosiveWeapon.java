package Weapon.ExplosiveWeapon;

import Map.Map;
import Shooter.PlayerController.Player;

import java.awt.image.BufferedImage;

public class ExplosiveWeapon {
    public Map map;
    protected final Player player;
    public double x;
    public double y;
    public double width;
    public double length;
    public double velX;
    public double velY;
    protected double flyAngle;

    public boolean thrown;
    public int throwVel;

    public int explosionRadius;
    public boolean explode;

    public int damage;
    public int power;
    /* this attribute represents fall back speed. Fall back speed of shooter equals to power when center of shooter is center of grenade*/

    public double bodyAngle;
    public double bodyRotateVel;

    protected BufferedImage image;

    public ExplosiveWeapon(Map map, Player player) {
        this.map = map;
        this.player = player;
    }

    protected void setLocation() {
        if (!thrown) {
            x = player.x + player.size / 2 - width / 2;
            y = player.y + player.size / 2 - length / 2;
        }
    }

    public void update() {
        x += velX;
        y += velY;
        bodyAngle += bodyRotateVel;
        if (velX == 0 && velY == 0) {
            bodyRotateVel = 0;
        }
    }

    public void thrown(double angle) {
        if (!thrown) {
            flyAngle = angle;
            velX = throwVel * Math.cos(flyAngle);
            velY = throwVel * Math.sin(flyAngle);
            if (flyAngle >= Math.toRadians(90) && flyAngle < Math.toRadians(270)) {
                bodyRotateVel = Math.toRadians(-3);
            } else {
                bodyRotateVel = Math.toRadians(3);
            }
            thrown = true;
        }
    }
}
