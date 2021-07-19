package Shooter.PlayerController;

import Map.Map;
import Weapon.ExplosiveWeapon.C4.C4Controller;
import Weapon.ExplosiveWeapon.C4.C4Manager;
import Weapon.ExplosiveWeapon.Grenade.GrenadeManager;
import Weapon.Gun.GrenadeLauncher.GrenadeLauncher;
import Weapon.Gun.Gun;
import Weapon.Gun.Railgun.Railgun;
import Weapon.Launcher.MissileLauncher.MissileLauncher;
import Weapon.Launcher.RocketLauncher.RocketLauncher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WeaponInventory {
    public String weaponName = "";
    public final Gun pistol;
    public final Gun rifle;
    public final Gun shotgun;
    public final Gun sniper;
    public final Gun gatling;
    public final GrenadeLauncher grenadeLauncher;
    public final GrenadeManager grenadeManager;
    public final C4Manager c4Manager;
    public final C4Controller c4Controller;
    public final RocketLauncher rocketLauncher;
    public final MissileLauncher missileLauncher;
    public final Railgun railgun;

    public WeaponInventory(Map map, Player player) {
        pistol = new Gun(map, player, 20, 13, 80, 0.03, 1, 0.5, 20, Gun.INFINITE_AMMO);
        pistol.initDisToRotateCenter(3, 3);
        pistol.initAmmo(player.color, 8, 4,"Assets\\Sounds\\Shoot1.wav");

        rifle = new Gun(map, player, 45, 17, 25, 0.06, 1, 0.25, 30, 150);
        rifle.initDisToRotateCenter(10, 5);
        rifle.initAmmo(player.color, 8, 4.5,"Assets\\Sounds\\Shoot1.wav");

        shotgun = new Gun(map, player, 40, 19, 175, 0.08f, 6, 1.5, 25, 30);
        shotgun.initDisToRotateCenter(5, 4);
        shotgun.initAmmo(player.color, 8, 3.5,"Assets\\Sounds\\Shoot1.wav");

        sniper = new Gun(map, player, 70, 20, 250, 0, 1, 2, 150, 15);
        sniper.initDisToRotateCenter(25, 10);
        sniper.initAmmo(player.color, 12, 6,"Assets\\Sounds\\Shoot1.wav");

        gatling = new Gun(map, player, 60, 21, 18, 0.08, 1, 0.2, 20, 200);
        gatling.initDisToRotateCenter(15, 11);
        gatling.initAmmo(player.color, 10, 3.5,"Assets\\Sounds\\Shoot1.wav");

        grenadeLauncher = new GrenadeLauncher(map, player, 50, 17, 125, 0.05, 1, 1, 200, 15);
        grenadeLauncher.initDisToRotateCenter(15, 7);
        grenadeLauncher.initAmmo(player.color, 10, 4, 100, 4,"Assets\\Sounds\\Shoot1.wav");

        railgun = new Railgun(map, player, 70, 18, 250, 1, 200, 5);
        railgun.initDisToRotateCenter(20, 8);

        try {
            pistol.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Pistol.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Pistol.png")));
            rifle.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Rifle.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Rifle.png")));
            shotgun.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Shotgun.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Shotgun.png")));
            sniper.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Sniper.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Sniper.png")));
            gatling.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Gatling.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Gatling.png")));
            grenadeLauncher.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Grenade-launcher.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Grenade-launcher.png")));
            railgun.initImage(ImageIO.read(new File("Assets\\2D models\\Weapons\\Left guns\\Railgun.png")), ImageIO.read(new File("Assets\\2D models\\Weapons\\Right guns\\Railgun.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        grenadeManager = new GrenadeManager(map, player);
        c4Controller = new C4Controller(player, map);
        c4Manager = new C4Manager(map, player, c4Controller);

        rocketLauncher = new RocketLauncher(map, player, 60, 20, 1, 250, 225, 10);
        rocketLauncher.initRocket(4, 100, 4);
        missileLauncher = new MissileLauncher(map, player, 65, 24, 1, 375, 275, 10);
        missileLauncher.initMissile(2, 125, 3, 300);
    }

    public void reset(Map map) {
        pistol.reset(map);
        rifle.reset(map);
        shotgun.reset(map);
        sniper.reset(map);
        gatling.reset(map);
        grenadeLauncher.reset(map);
        grenadeManager.reset(map);
        c4Controller.reset(map);
        c4Manager.reset(map);
        rocketLauncher.reset(map);
        missileLauncher.reset(map);
        railgun.reset(map);
    }

    public void paint(Graphics2D g2d) {
        Gun[] gun = new Gun[]{pistol, rifle, shotgun, sniper, gatling, grenadeLauncher, railgun};
        for (Gun g : gun) {
            g.paintAmmo(g2d);
        }
        rocketLauncher.paintRocket(g2d);
        missileLauncher.paintMissile(g2d);
        grenadeManager.paint(g2d);
        c4Manager.paint(g2d);

        switch (weaponName) {
            case "Pistol" -> pistol.paintGun(g2d);
            case "Rifle" -> rifle.paintGun(g2d);
            case "Shotgun" -> shotgun.paintGun(g2d);
            case "Sniper" -> sniper.paintGun(g2d);
            case "Gatling" -> gatling.paintGun(g2d);
            case "Grenade launcher" -> grenadeLauncher.paintGun(g2d);
            case "Grenade" -> grenadeManager.paintGrenade(g2d);
            case "C4" -> c4Manager.paintC4(g2d);
            case "C4 controller" -> c4Controller.paint(g2d);
            case "Rocket launcher" -> rocketLauncher.paintLauncher(g2d);
            case "Missile launcher" -> missileLauncher.paintLauncher(g2d);
            case "Railgun" -> railgun.paintGun(g2d);
        }

    }

    public void update() {
        Gun[] gun = new Gun[]{pistol, rifle, shotgun, sniper, gatling, grenadeLauncher, railgun};
        for (Gun g : gun) {
            g.update();
        }
        grenadeManager.update();
        c4Manager.update();
        rocketLauncher.update();
        missileLauncher.update();
    }
}
