package Weapon.Gun.GrenadeLauncher;

import Map.Map;
import Shooter.Actor;
import Weapon.Gun.Gun;

import java.awt.*;
import java.util.ArrayList;

public class GrenadeLauncher extends Gun {
    public int ammoExplosionRadius;
    public int ammoPower;
    public final ArrayList<ExplosiveAmmo> explosiveAmmo = new ArrayList<>();

    public GrenadeLauncher(Map map, Actor actor, double width, double length, int fireInterval, double deflection, int amount, double recoil, int damage, int maxMag) {
        super(map, actor, width, length, fireInterval, deflection, amount, recoil, damage, maxMag);
    }

    public void initAmmo(Color color, double size, double vel, int explosionRadius, int power,String soundPathName) {
        super.initAmmo(color, size, vel,soundPathName);
        this.ammoExplosionRadius = explosionRadius;
        this.ammoPower = power;
    }

    public void reset(Map map) {
        this.map = map;
        explosiveAmmo.clear();
        if (mag != INFINITE_AMMO) {
            mag = 0;
        }
        angle = 0;
    }

    public void removeAmmo() {
        explosiveAmmo.removeIf(ammo -> !ammo.appear &&
                ammo.timer > fireInterval &&
                ammo.pieceEmitter.particle.isEmpty() &&
                ammo.explosionFireEmitter.particle.isEmpty() &&
                ammo.explosionSmokeEmitter.particle.isEmpty());
    }

    public void shoot() {
        if (mag > 0 || mag == INFINITE_AMMO) {
            if (explosiveAmmo.isEmpty() || explosiveAmmo.get(explosiveAmmo.size() - 1).timer > fireInterval) {
                //reduce mag
                if (mag > 0) {
                    mag--;
                }
                //add new ammo
                for (int i = 0; i < amount; i++) {
                    explosiveAmmo.add(new ExplosiveAmmo(map, ammoColor, ammoSize, ammoVel, ammoExplosionRadius, ammoPower));
                }
            }
            double[] allAngle = new double[(int) (200 * deflection + 1)];
            for (int j = 0; j < allAngle.length; j++) {
                allAngle[j] = (float) j / 100 - deflection;
            }
            //fly new ammo
            for (int i = explosiveAmmo.size() - amount; i <= explosiveAmmo.size() - 1; i++) {
                if (explosiveAmmo.get(i).timer == 0) {
                    int index = random.nextInt(allAngle.length);
                    explosiveAmmo.get(i).fly(actor.x + actor.size / 2, actor.y + actor.size / 2, angle + allAngle[index],soundPathName);
                }
            }
        }
    }

    public void update() {
        for(ExplosiveAmmo ammo : explosiveAmmo){
            ammo.update();
        }
        removeAmmo();
    }

    public void paintAmmo(Graphics2D g2d) {
        for (int i = 0; i < explosiveAmmo.size(); i++) {
            explosiveAmmo.get(i).paint(g2d);
        }
    }
}
