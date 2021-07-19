package Weapon.ExplosiveWeapon.Grenade;

import Map.Map;
import Shooter.PlayerController.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GrenadeManager {
    public Map map;
    private final Player player;
    public final ArrayList<Grenade> grenade = new ArrayList<>();
    public int amount;
    public static int MAX_AMOUNT = 40;
    private BufferedImage image;

    public GrenadeManager(Map map, Player player) {
        this.map = map;
        this.player = player;
    }

    {
        try {
            image = ImageIO.read(new File("Assets\\2D models\\Weapons\\Explosive weapons\\Grenade.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset(Map map) {
        this.map = map;
        amount = 0;
        grenade.clear();
    }

    public void throwGrenade(double angle) {
        if (amount > 0) {
            grenade.add(new Grenade(map, player));
            grenade.get(grenade.size() - 1).thrown(angle);
            amount--;
        }
    }

    public void reload() {
        amount = MAX_AMOUNT;
    }

    private void removeGrenade() {
        grenade.removeIf(grenade -> grenade.fireEmitter.particle.isEmpty() && grenade.smokeEmitter.particle.isEmpty() && grenade.timer > Grenade.EXPLOSION_TIME + 1);
    }

    public void update() {
        for (Grenade grenade : grenade) {
            grenade.update();
        }
        removeGrenade();
    }

    public void paintGrenade(Graphics2D g2d) {
        if (player.health > 0) {
            g2d.drawImage(image, (int) (map.x + player.x + player.size / 2 - 6), (int) (map.y + player.y + player.size / 2 - 7), 13, 15, null);
        }
    }

    public void paint(Graphics2D g2d) {
        for(int i = 0 ; i < grenade.size() ; i ++){
            grenade.get(i).paint(g2d);
        }
    }
}
