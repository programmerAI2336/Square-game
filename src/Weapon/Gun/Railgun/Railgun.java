package Weapon.Gun.Railgun;

import Map.Map;
import Shooter.Actor;
import Weapon.Gun.Gun;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Railgun extends Gun {
    public final ArrayList<Laser> laser = new ArrayList<>();

    public Railgun(Map map, Actor actor, double width, double length, int fireInterval, int amount, int damage, int maxMag) {
        super(map, actor, width, length, fireInterval, 0, amount, 0, damage, maxMag);
    }

    public void reset(Map map) {
        this.map = map;
        laser.clear();
        if (mag != INFINITE_AMMO) {
            mag = 0;
        }
        angle = 0;
    }

    public void shoot() {
        if (mag > 0 || mag == INFINITE_AMMO) {
            if (laser.isEmpty() || laser.get(laser.size() - 1).timer > fireInterval) {
                //reduce mag
                if (mag > 0) {
                    mag--;
                }
                //add new ammo
                for (int i = 0; i < amount; i++) {
                    laser.add(new Laser(map, actor.x + actor.size / 2, actor.y + actor.size / 2, angle));
                }
            }
        }
    }

    private void removeLaser() {
        laser.removeIf(laser -> laser.opacity == 0 && laser.timer > fireInterval);
    }

    public void update() {
        for (Laser laser : laser) {
            laser.update();
        }
        removeLaser();
    }

    public void paintAmmo(Graphics2D g2d) {
        for (int i = 0; i < laser.size(); i++) {
            laser.get(i).paint(g2d);
        }
    }
}
