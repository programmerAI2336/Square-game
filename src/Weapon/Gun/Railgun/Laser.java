package Weapon.Gun.Railgun;

import Map.Map;
import Audio.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Laser {
    public final Map map;
    public final double x, y;
    public static final int RANGE = 1200;
    public int opacity = 255;
    public final double angle;
    public int timer;

    public Laser(Map map, double x, double y, double angle) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void update() {
        timer++;
        if (timer == 1) {
            Sound.play("Assets\\Sounds\\Laser.wav");
        }
        if (timer <= 128) {
            opacity = 256 - timer * 2;
        }
    }

    public void paint(Graphics2D g2d) {
        AffineTransform laser = new AffineTransform();
        g2d.rotate(angle, map.x + x, map.y + y);
        g2d.setColor(new Color(Color.MAGENTA.getRed(), Color.MAGENTA.getGreen(), Color.MAGENTA.getBlue(), opacity));
        g2d.fillRect((int) (map.x + x), (int) (map.y + y - 2), RANGE, 4);
        g2d.setTransform(laser);
    }
}
