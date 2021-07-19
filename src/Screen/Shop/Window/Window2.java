package Screen.Shop.Window;

import Screen.Shop.Upgrade.GunUpgrade;
import Screen.Shop.UpgradeManager.WeaponUpgradeManager;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class Window2 implements MouseMotionListener, MouseListener {
    private final WeaponUpgradeManager upgradeManager;
    private final GunUpgrade[] gunUpgrade;
    private final GunInformer gunInformer = new GunInformer(583, 600);

    public Window2(GameStats gameStats, WeaponUpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
        gunUpgrade = new GunUpgrade[6];
        try {
            gunUpgrade[0] = new GunUpgrade(gameStats, upgradeManager, "Pistol", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-pistol-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-pistol-2.png")), 408, 200, 25, 5, "Data\\Upgrades\\Gun's upgrades\\Pistol.txt");
            gunUpgrade[1] = new GunUpgrade(gameStats, upgradeManager, "Rifle", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-rifle-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-rifle-2.png")), 658, 200, 35, 5, "Data\\Upgrades\\Gun's upgrades\\Rifle.txt");
            gunUpgrade[2] = new GunUpgrade(gameStats, upgradeManager, "Shotgun", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-shotgun-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-shotgun-2.png")), 908, 200, 35, 5, "Data\\Upgrades\\Gun's upgrades\\Shotgun.txt");
            gunUpgrade[3] = new GunUpgrade(gameStats, upgradeManager, "Sniper", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-sniper-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-sniper-2.png")), 408, 400, 45, 5, "Data\\Upgrades\\Gun's upgrades\\Sniper.txt");
            gunUpgrade[4] = new GunUpgrade(gameStats, upgradeManager, "Gatling", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-gatling-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-gatling-2.png")), 658, 400, 70, 5, "Data\\Upgrades\\Gun's upgrades\\Gatling.txt");
            gunUpgrade[5] = new GunUpgrade(gameStats, upgradeManager, "Railgun", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-railgun-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-railgun-2.png")), 908, 400, 100, 5, "Data\\Upgrades\\Gun's upgrades\\Railgun.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Open Sans", Font.BOLD, 60));
        g2d.drawString("Gun's upgrades", 450, 100);
        gunInformer.paint(g2d);
        for (GunUpgrade upgrade : gunUpgrade) {
            upgrade.paint(g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (GunUpgrade upgrade : gunUpgrade) {
            upgrade.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GunUpgrade upgrade : gunUpgrade) {
            upgrade.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (GunUpgrade upgrade : gunUpgrade) {
            upgrade.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (GunUpgrade upgrade : gunUpgrade) {
            upgrade.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GunUpgrade upgrade : gunUpgrade) {
            upgrade.mouseMoved(e);
        }
    }

    private void informGun() {
        if (gunUpgrade[0].button.touched) {
            gunInformer.informGun(gunUpgrade[0].button.image2, "Pistol", 250 / upgradeManager.pistolFireInterval, upgradeManager.pistolDeflection, 0, upgradeManager.pistolDamage);
        } else if (gunUpgrade[1].button.touched) {
            gunInformer.informGun(gunUpgrade[1].button.image2, "Rifle", 250 / upgradeManager.rifleFireInterval, upgradeManager.rifleDeflection, upgradeManager.rifleMaxMag, upgradeManager.rifleDamage);
        } else if (gunUpgrade[2].button.touched) {
            gunInformer.informGun(gunUpgrade[2].button.image2, "Shotgun", 250 / upgradeManager.shotgunFireInterval, 0.08, upgradeManager.shotgunMaxMag, upgradeManager.shotgunDamage * 6);
        } else if (gunUpgrade[3].button.touched) {
            gunInformer.informGun(gunUpgrade[3].button.image2, "Sniper", 250 / upgradeManager.sniperFireInterval, 0, upgradeManager.sniperMaxMag, upgradeManager.sniperDamage);
        } else if (gunUpgrade[4].button.touched) {
            gunInformer.informGun(gunUpgrade[4].button.image2, "Gatling", 250 / upgradeManager.gatlingFireInterval, 0.08, upgradeManager.gatlingMaxMag, upgradeManager.gatlingDamage);
        } else if (gunUpgrade[5].button.touched) {
            gunInformer.informGun(gunUpgrade[5].button.image2, "Railgun", 250 / upgradeManager.railgunFireInterval, 0, upgradeManager.railgunMaxMag, upgradeManager.railgunDamage);
        } else {
            gunInformer.informGun(GunInformer.DEFAULT_IMAGE, null, GunInformer.DEFAULT_FIRE_RATE, GunInformer.DEFAULT_DEFLECTION, GunInformer.DEFAULT_MAX_MAG, GunInformer.DEFAULT_DAMAGE);
            gunInformer.resetValue();
        }
    }

    public void update() {
        for (GunUpgrade upgrade : gunUpgrade) {
            upgrade.update();
        }
        informGun();
        gunInformer.update();
    }
}
