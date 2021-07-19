package Weapon.ExplosiveWeapon.C4;

import Map.Map;
import Shooter.PlayerController.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class C4Manager {
    private Map map;
    private final Player player;
    public final ArrayList<C4> c4 = new ArrayList<>();
    public C4Controller controller;

    public int amount;
    public static int MAX_AMOUNT = 10;

    private BufferedImage image;

    public C4Manager(Map map, Player player, C4Controller controller) {
        this.map = map;
        this.player = player;
        this.controller = controller;
    }

    {
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Weapons\\Explosive weapons\\C4-no-light.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset(Map map) {
        this.map = map;
        amount = 0;
        c4.clear();
    }

    public void throwC4(double angle) {
        if (amount > 0) {
            c4.add(new C4(map, player));
            c4.get(c4.size() - 1).thrown(angle);
            amount--;
        }
    }

    public void reload() {
        amount = MAX_AMOUNT;
    }

    private void removeC4() {
        c4.removeIf(c4 -> c4.fireEmitter.particle.isEmpty() && c4.smokeEmitter.particle.isEmpty() && c4.timer > 1);
    }

    public void update() {
        for (C4 c4 : c4) {
            if (controller.activate && !c4.explode) {
                c4.explode = true;
            }
            c4.update();
        }
        removeC4();
    }

    public void paintC4(Graphics2D g2d) {
        if (player.health > 0) {
            g2d.drawImage(image, (int) (map.x + player.x + player.size / 2 - 15), (int) (map.y + player.y + player.size / 2 - 7), 30, 13, null);
        }
    }

    public void paint(Graphics2D g2d) {
        for (int i = 0; i < c4.size(); i++) {
            c4.get(i).paint(g2d);
        }
    }
}
