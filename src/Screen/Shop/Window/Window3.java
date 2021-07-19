package Screen.Shop.Window;

import Screen.Shop.Upgrade.LauncherUpgrade;
import Screen.Shop.UpgradeManager.WeaponUpgradeManager;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class Window3 implements MouseMotionListener, MouseListener {
    private final WeaponUpgradeManager upgradeManager;
    private final LauncherUpgrade[] upgrade;
    private final LauncherInformer launcherInformer = new LauncherInformer(533, 600);

    public Window3(GameStats gameStats, WeaponUpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
        upgrade = new LauncherUpgrade[3];
        try {
            upgrade[0] = new LauncherUpgrade(gameStats, upgradeManager, "Rocket launcher", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-rocket-launcher-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-rocket-launcher-2.png")), 408, 200, 75, 5, "Data\\Upgrades\\Launcher's upgrades\\Rocket launcher.txt");
            upgrade[1] = new LauncherUpgrade(gameStats, upgradeManager, "Missile launcher", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-missile-launcher-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-missile-launcher-2.png")), 658, 200, 75, 5, "Data\\Upgrades\\Launcher's upgrades\\Missile launcher.txt");
            upgrade[2] = new LauncherUpgrade(gameStats, upgradeManager, "Grenade launcher", ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-grenade-launcher-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Shop\\Weapon's upgrades\\Upgrade-grenade-launcher-2.png")), 908, 200, 50, 5, "Data\\Upgrades\\Launcher's upgrades\\Grenade launcher.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Open Sans", Font.BOLD, 60));
        g2d.drawString("Launcher's upgrades", 400, 100);
        launcherInformer.paint(g2d);
        for (LauncherUpgrade upgrade : upgrade) {
            upgrade.paint(g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (LauncherUpgrade upgrade : upgrade) {
            upgrade.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (LauncherUpgrade upgrade : upgrade) {
            upgrade.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (LauncherUpgrade upgrade : upgrade) {
            upgrade.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (LauncherUpgrade upgrade : upgrade) {
            upgrade.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (LauncherUpgrade upgrade : upgrade) {
            upgrade.mouseMoved(e);
        }
    }

    private void informLauncher() {
        if (upgrade[0].button.touched) {
            launcherInformer.informLauncher(upgrade[0].button.image2,
                    "Rocket launcher",
                    250 / upgradeManager.rocketLauncherFireInterval,
                    upgradeManager.rocketLauncherMaxMag,
                    upgradeManager.rocketLauncherDamage,
                    upgradeManager.rocketVelocity,
                    upgradeManager.rocketExplosionRadius);
        } else if (upgrade[1].button.touched) {
            launcherInformer.informLauncher(upgrade[1].button.image2,
                    "Missile launcher",
                    250 / upgradeManager.missileLauncherFireInterval,
                    upgradeManager.missileLauncherMaxMag,
                    upgradeManager.missileLauncherDamage,
                    upgradeManager.missileVelocity,
                    upgradeManager.missileExplosionRadius);
        } else if (upgrade[2].button.touched) {
            launcherInformer.informLauncher(upgrade[2].button.image2,
                    "Grenade launcher",
                    250 / upgradeManager.grenadeLauncherFireInterval,
                    upgradeManager.grenadeLauncherMaxMag,
                    upgradeManager.grenadeLauncherDamage,
                    4,
                    upgradeManager.grenadeExplosionRadius);
        } else {
            launcherInformer.informLauncher(LauncherInformer.DEFAULT_IMAGE,
                    null,
                    LauncherInformer.DEFAULT_FIRE_RATE,
                    LauncherInformer.DEFAULT_MAX_MAG,
                    LauncherInformer.DEFAULT_DAMAGE,
                    LauncherInformer.DEFAULT_VELOCITY,
                    LauncherInformer.DEFAULT_EXPLOSION_RADIUS);
            launcherInformer.resetValue();
        }
    }

    public void update() {
        for (LauncherUpgrade upgrade : upgrade) {
            upgrade.update();
        }
        informLauncher();
        launcherInformer.update();
    }
}
