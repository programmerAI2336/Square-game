package Weapon.Gun.GrenadeLauncher;

import Map.Map;
import ParticleSystem.ParticleEmitter;
import Audio.Sound;
import Weapon.Gun.Ammo;

import java.awt.*;

public class ExplosiveAmmo extends Ammo {
    public final int explosionRadius;
    public final int power;
    public ParticleEmitter explosionFireEmitter;
    public ParticleEmitter explosionSmokeEmitter;

    public ExplosiveAmmo(Map map, Color color, double size, double vel, int explosionRadius, int power) {
        super(map, color, size, vel);
        this.explosionRadius = explosionRadius;
        this.power = power;
        initExplosionFireEmitter();
        initExplosionSmokeEmitter();
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
        explosionFireEmitter.setParticleColor(new Color(0xFF7700));
        explosionFireEmitter.setSizeBound(40, 10);
        explosionFireEmitter.setParticleShape("Circle");
        explosionFireEmitter.setVelocity(3, 2);
    }

    public int explosionTimer;

    private void explode() {
        if (!appear) {
            explosionTimer++;
            if (explosionTimer == 1) {
                explosionFireEmitter.setPosition(x + size / 2, y + size / 2);
                explosionFireEmitter.spread();
                explosionSmokeEmitter.setPosition(x + size / 2, y + size / 2);
                explosionSmokeEmitter.spread();
                Sound.play("Assets\\Sounds\\Explosion.wav");
            }
        }
    }

    private void fall() {
        if (appear) {
            velY += 0.015;
        }
    }

    public void update() {
        super.update();
        explode();
        fall();
        explosionFireEmitter.update();
        explosionSmokeEmitter.update();
    }

    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        explosionSmokeEmitter.paint(g2d);
        explosionFireEmitter.paint(g2d);
    }
}
