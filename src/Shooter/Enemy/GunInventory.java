package Shooter.Enemy;

import Map.Map;
import Weapon.Gun.Gun;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GunInventory {
    private final Gun[] gun;
    private static final Random random = new Random();

    public GunInventory(Map map, Enemy enemy) {
        Gun pistol = new Gun(map, enemy, 20, 13, 500, 0.03, 1, 0.25, 15, Gun.INFINITE_AMMO);
        pistol.initDisToRotateCenter(3, 3);
        pistol.initAmmo(enemy.color, 8, 1.25,"");

        Gun rifle = new Gun(map, enemy, 45, 17, 250, 0.06, 1, 0.25, 15, Gun.INFINITE_AMMO);
        rifle.initDisToRotateCenter(10, 5);
        rifle.initAmmo(enemy.color, 8, 1.25,"");

        Gun shotgun = new Gun(map, enemy, 40, 19, 500, 0.08f, 6, 0.25, 10, Gun.INFINITE_AMMO);
        shotgun.initDisToRotateCenter(5, 4);
        shotgun.initAmmo(enemy.color, 8, 1.25,"");

        Gun sniper = new Gun(map, enemy, 70, 20, 500, 0, 1, 2, 75, Gun.INFINITE_AMMO);
        sniper.initDisToRotateCenter(25, 10);
        sniper.initAmmo(enemy.color, 12, 3,"");

        Gun gatling = new Gun(map, enemy, 60, 21, 100, 0.08, 1, 0.5, 10, Gun.INFINITE_AMMO);
        gatling.initDisToRotateCenter(15, 11);
        gatling.initAmmo(enemy.color, 10, 1.75,"");

        try {
            pistol.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Pistol.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Pistol.png")));
            rifle.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Rifle.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Rifle.png")));
            shotgun.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Shotgun.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Shotgun.png")));
            sniper.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Sniper.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Sniper.png")));
            gatling.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Gatling.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Gatling.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gun = new Gun[]{pistol, rifle, shotgun, sniper, gatling};
    }

    public Gun getRandomGun() {
        int num = random.nextInt(100);
        if (num <= 49) {
            return gun[0];
        } else if (num <= 73) {
            return gun[1];
        } else if (num <= 97) {
            return gun[2];
        } else {
            return gun[3];
        }
    }

    public Gun getSniper() {
        return gun[3];
    }

    public Gun getGatling() {
        return gun[4];
    }
}
