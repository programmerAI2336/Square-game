package Weapon.ExplosiveWeapon.Grenade;

import Map.Map;
import ParticleSystem.ParticleEmitter;
import Shooter.PlayerController.Player;
import Audio.Sound;
import Weapon.ExplosiveWeapon.ExplosiveWeapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class Grenade extends ExplosiveWeapon {
    private final Physic physic;
    public static final int EXPLOSION_TIME = 500;
    public int timer;
    public final ParticleEmitter fireEmitter;
    public final ParticleEmitter smokeEmitter;

    public Grenade(Map map, Player player) {
        super(map, player);
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Weapons\\Explosive weapons\\Grenade.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        physic = new Physic(this);
        width = 13;
        length = 15;
        explosionRadius = 100;
        damage = 200;
        power = 5;
        throwVel = 3;
        setLocation();

        fireEmitter = new ParticleEmitter(map);
        fireEmitter.setAge(1250, 750);
        fireEmitter.setAngle(360, 0);
        fireEmitter.setAmountOfParticle(50);
        fireEmitter.setParticleColor(new Color(0xFF9900));
        fireEmitter.setSizeBound(30, 5);
        fireEmitter.setParticleShape("Circle");
        fireEmitter.setVelocity(4, 1);

        smokeEmitter = new ParticleEmitter(map);
        smokeEmitter.setAge(2500, 1250);
        smokeEmitter.setAngle(360, 0);
        smokeEmitter.setAmountOfParticle(50);
        smokeEmitter.setParticleColor(new Color(0x4DFFFFFF, true));
        smokeEmitter.setSizeBound(40, 20);
        smokeEmitter.setParticleShape("Circle");
        smokeEmitter.setVelocity(2, 1);
    }

    private void explode() {
        if (thrown) {
            timer++;
            if (timer == EXPLOSION_TIME) {
                fireEmitter.setPosition((int) (x + width / 2), (int) (y + length / 2));
                fireEmitter.spread();
                smokeEmitter.setPosition((int) (x + width / 2), (int) (y + length / 2));
                smokeEmitter.spread();
                Sound.play("Assets\\Sounds\\Explosion.wav");
            }
        }
    }

    public void update() {
        physic.handle();
        super.update();
        fireEmitter.update();
        smokeEmitter.update();
        explode();
    }

    public void paint(Graphics2D g2d) {
        if (thrown) {
            if (timer < EXPLOSION_TIME) {
                // when grenade doesn't explode
                AffineTransform grenade = new AffineTransform();
                g2d.rotate(bodyAngle, map.x + x + width / 2, map.y + y + length / 2);
                g2d.setColor(Color.BLACK);
                g2d.drawImage(image, (int) (map.x + x), (int) (map.y + y), (int) width, (int) length, null);
                g2d.setTransform(grenade);
            }
            fireEmitter.paint(g2d);
            smokeEmitter.paint(g2d);
        }
    }
}
