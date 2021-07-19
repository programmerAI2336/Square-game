package Weapon.Launcher.MissileLauncher;

import Map.*;
import Shooter.Actor;
import Weapon.Launcher.RocketLauncher.Rocket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Missile extends Rocket {
    private final int searchRange;
    private double flyAngleToEnemy;

    public Missile(Map map, double velocity, int explosiveRadius, int power, int searchRange) {
        super(map, velocity, explosiveRadius, power);
        this.searchRange = searchRange;
        width = 30;
        length = 15;
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Weapons\\Launcher\\Missile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchTarget(ArrayList<Actor> enemy) {
        if (appear) {
            ArrayList<Actor> enemyInBound = new ArrayList<>();
            for (Actor e : enemy) {
                if (e.health > 0) {
                    double distanceToEnemy = Math.sqrt(Math.pow(e.x + e.size / 2 - x - width / 2, 2) + Math.pow(e.y + e.size / 2 - y - length / 2, 2));
                    if (distanceToEnemy <= searchRange) {
                        enemyInBound.add(e);
                    }
                }
            }

            if (!enemyInBound.isEmpty()) {
                Actor closestEnemy = enemyInBound.get(0);
                for (Actor e : enemyInBound) {
                    double d1 = Math.sqrt(Math.pow(closestEnemy.x + closestEnemy.size / 2 - x - width / 2, 2) + Math.pow(closestEnemy.y + closestEnemy.size / 2 - y - length / 2, 2));
                    double d2 = Math.sqrt(Math.pow(e.x + e.size / 2 - x - width / 2, 2) + Math.pow(e.y + e.size / 2 - y - length / 2, 2));
                    if (d2 < d1) {
                        closestEnemy = e;
                    }
                }

                double angleToEnemy = Math.atan((closestEnemy.y + closestEnemy.size / 2 - y - length / 2) / (closestEnemy.x + closestEnemy.size / 2 - x - width / 2));

                if (closestEnemy.x + closestEnemy.size / 2 > x + width / 2) {
                    if (closestEnemy.y + closestEnemy.size / 2 > y + length / 2) {
                        flyAngleToEnemy = angleToEnemy;
                    } else {
                        flyAngleToEnemy = angleToEnemy + Math.toRadians(360);
                    }
                } else if (closestEnemy.x + closestEnemy.size / 2 < x + width / 2) {
                    flyAngleToEnemy = angleToEnemy + Math.toRadians(180);
                }

                double rotateVel = velocity / 100;
                if (Math.abs(flyAngleToEnemy - angle) < Math.toRadians(180)) {
                    if (flyAngleToEnemy > angle) {
                        angle += rotateVel;
                    }
                    if (flyAngleToEnemy < angle) {
                        angle -= rotateVel;
                    }
                } else {
                    if (flyAngleToEnemy < Math.toRadians(180) && angle > Math.toRadians(180)) {
                        angle += rotateVel;
                        if (angle > Math.toRadians(360)) {
                            angle -= Math.toRadians(360);
                        }
                    }
                    if (flyAngleToEnemy > Math.toRadians(180) && angle < Math.toRadians(180)) {
                        angle -= rotateVel;
                        if (angle < 0) {
                            angle += Math.toRadians(360);
                        }
                    }
                }

                velX = velocity * Math.cos(angle);
                velY = velocity * Math.sin(angle);
            }
        }
    }

    private void render(Graphics2D g2d) {
        if (appear) {
            g2d.setColor(Color.MAGENTA);
            g2d.drawOval((int) (map.x + x + width / 2 - searchRange), (int) (map.y + y + length / 2 - searchRange), searchRange * 2, searchRange * 2);

            AffineTransform a1 = new AffineTransform();
            g2d.rotate(flyAngleToEnemy, map.x + x + width / 2, map.y + y + length / 2);
            g2d.setColor(Color.GREEN);
            g2d.drawLine((int) (map.x + x + width / 2), (int) (map.y + y + length / 2), (int) (map.x + x + width / 2 + searchRange), (int) (map.y + y + length / 2));
            g2d.setTransform(a1);

            AffineTransform a2 = new AffineTransform();
            g2d.rotate(angle, map.x + x + width / 2, map.y + y + length / 2);
            g2d.setColor(Color.RED);
            g2d.drawLine((int) (map.x + x + width / 2), (int) (map.y + y + length / 2), (int) (map.x + x + width / 2 + searchRange), (int) (map.y + y + length / 2));
            g2d.setTransform(a2);
        }
    }

    public void paint(Graphics2D g2d) {
        //render(g2d);
        super.paint(g2d);
    }
}
