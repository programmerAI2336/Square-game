package Weapon.ExplosiveWeapon.C4;

import Map.Map;
import ParticleSystem.ParticleEmitter;
import Shooter.PlayerController.Player;
import Audio.Sound;
import Weapon.ExplosiveWeapon.ExplosiveWeapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class C4 extends ExplosiveWeapon {
    private final Physic physic;
    public boolean stick;
    public int timer;

    private static BufferedImage C4_LIGHT = null;
    private static BufferedImage C4_NO_LIGHT = null;
    private int lightTimer;

    public final ParticleEmitter fireEmitter;
    public final ParticleEmitter smokeEmitter;

    static {
        try {
            C4_LIGHT = ImageIO.read(new File("Assets\\2D models\\Weapons\\Explosive weapons\\C4-light.png"));
            C4_NO_LIGHT = ImageIO.read(new File("Assets\\2D models\\Weapons\\Explosive weapons\\C4-no-light.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public C4(Map map, Player player) {
        super(map, player);
        physic = new Physic(this);
        width = 30;
        length = 13;
        explosionRadius = 125;
        damage = 350;
        power = 7;
        throwVel = 2;
        setLocation();

        fireEmitter = new ParticleEmitter(map);
        fireEmitter.setAge(1250, 750);
        fireEmitter.setAngle(360, 0);
        fireEmitter.setAmountOfParticle(50);
        fireEmitter.setParticleColor(Color.ORANGE);
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
        if (explode) {
            timer++;
            if (timer == 1) {
                fireEmitter.setPosition((int) (x + width / 2), (int) (y + length / 2));
                fireEmitter.spread();
                smokeEmitter.setPosition((int) (x + width / 2), (int) (y + length / 2));
                smokeEmitter.spread();
                Sound.play("Assets\\Sounds\\Explosion.wav");
            }
        }
    }

    private void lighting() {
        lightTimer++;
        if (lightTimer <= 200) {
            image = C4_NO_LIGHT;
        } else if (lightTimer <= 250) {
            image = C4_LIGHT;
        } else {
            lightTimer = 0;
        }
    }

    public void update() {
        physic.handle();
        super.update();
        fireEmitter.update();
        smokeEmitter.update();
        explode();
        lighting();
    }

    public void paint(Graphics2D g2d) {
        if (thrown) {
            if (!explode) {
                AffineTransform c4 = new AffineTransform();
                g2d.rotate(bodyAngle, map.x + x + width / 2, map.y + y + length / 2);
                g2d.drawImage(image, (int) (map.x + x), (int) (map.y + y), (int) width, (int) length, null);
                g2d.setTransform(c4);
            }
            fireEmitter.paint(g2d);
            smokeEmitter.paint(g2d);
        }
    }
}
