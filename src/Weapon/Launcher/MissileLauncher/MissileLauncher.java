package Weapon.Launcher.MissileLauncher;

import Map.Map;
import Shooter.Actor;
import Weapon.Launcher.RocketLauncher.RocketLauncher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MissileLauncher extends RocketLauncher {
    public int missileSearchRange;
    public final ArrayList<Actor> allTarget = new ArrayList<>();
    public final ArrayList<Missile> missile = new ArrayList<>();

    public MissileLauncher(Map map, Actor actor, double width, double length, int amount, int freInterval, int damage, int maxMag) {
        super(map, actor, width, length, amount, freInterval, damage, maxMag);
        try {
            leftImage = ImageIO.read(new File("Assets\\2D models\\Weapons\\Launcher\\Left launcher\\Missile launcher.png"));
            rightImage = ImageIO.read(new File("Assets\\2D models\\Weapons\\Launcher\\Right launcher\\Missile launcher.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initMissile(double velocity, int explosionRadius, int power, int searchRange) {
        super.initRocket(velocity, explosionRadius, power);
        this.missileSearchRange = searchRange;
    }

    public void reset(Map map) {
        this.map = map;
        allTarget.clear();
        missile.clear();
        angle = 0;
        mag = 0;
    }

    public void shoot() {
        if (mag > 0) {
            if (missile.isEmpty() || missile.get(missile.size() - 1).timer > fireInterval) {
                mag--;
                for (int i = 0; i < amount; i++) {
                    missile.add(new Missile(map, rocketVelocity, rocketExplosionRadius, rocketPower, missileSearchRange));
                }
            }
            for (int i = missile.size() - amount; i <= missile.size() - 1; i++) {
                if (missile.get(i).timer == 0) {
                    missile.get(i).fly(actor.x + actor.size / 2, actor.y + actor.size / 2, angle);
                }
            }
        }
    }

    private void removeMissile() {
        missile.removeIf(missile -> !missile.appear && missile.timer > fireInterval && missile.explosionFireEmitter.particle.isEmpty() && missile.explosionSmokeEmitter.particle.isEmpty());
    }

    public void update() {
        for (Missile missile : missile) {
            missile.searchTarget(allTarget);
            missile.update();
        }
        removeMissile();
    }

    public void paintMissile(Graphics2D g2d) {
        for (int i = 0; i < missile.size(); i++) {
            missile.get(i).paint(g2d);
        }
    }
}
