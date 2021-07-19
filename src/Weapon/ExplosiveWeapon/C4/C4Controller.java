package Weapon.ExplosiveWeapon.C4;

import Map.Map;
import Shooter.PlayerController.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class C4Controller {
    private final Player player;
    private Map map;
    private static final int WIDTH = 10;
    private static final int LENGTH = 21;
    private static BufferedImage IMAGE;

    public double angle;
    public boolean activate;

    public C4Controller(Player player, Map map) {
        this.player = player;
        this.map = map;
    }

    static {
        try {
            IMAGE = ImageIO.read(new File("Assets\\2D models\\Weapons\\Explosive weapons\\Activation-controller.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset(Map map) {
        this.map = map;
        angle = 0;
        activate = false;
    }

    public void paint(Graphics2D g2d) {
        if (player.health > 0) {
            AffineTransform controller = new AffineTransform();
            g2d.rotate(angle + Math.toRadians(90), map.x + player.x + player.size / 2, map.y + player.y + player.size / 2);
            g2d.drawImage(IMAGE, (int) (map.x + player.x + player.size / 2 - WIDTH / 2), (int) (map.y + player.y + player.size / 2 - LENGTH / 2), WIDTH, LENGTH, null);
            g2d.setTransform(controller);
        }
    }
}
