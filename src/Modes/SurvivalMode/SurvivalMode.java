package Modes.SurvivalMode;

import Camera.Camera;
import Map.Map;
import Modes.MapGenerator;
import Screen.Customize.Customize;
import Screen.Shop.ShopManager;
import Shooter.Enemy.BombEnemy.BombEnemySpawner;
import Shooter.Enemy.EnemySpawner.GunEnemySpawner;
import Shooter.PlayerController.Player;
import Shooter.PlayerController.PlayerController;

import java.awt.*;
import java.awt.event.*;

public class SurvivalMode implements KeyListener, MouseMotionListener, MouseListener {
    private final Camera cam = new Camera();
    private final MapGenerator mapGenerator = new MapGenerator(cam);
    private final Player player;
    public final PlayerController playerController;
    private final GunEnemySpawner[] gunEnemySpawner;
    private final BombEnemySpawner bombEnemySpawner;
    private final UI ui;
    public boolean gameOver;

    public SurvivalMode() {
        Map randomMap = mapGenerator.getRandomMap();
        player = new Player(randomMap);
        playerController = new PlayerController(player);

        GunEnemySpawner normalEnemySpawner = new GunEnemySpawner(player, randomMap, 1000, "Normal enemy", "Survival");
        GunEnemySpawner tankEnemySpawner = new GunEnemySpawner(player, randomMap, 2500, "Tank enemy", "Survival");
        GunEnemySpawner flyEnemySpawner = new GunEnemySpawner(player, randomMap, 3750, "Fly enemy", "Survival");
        GunEnemySpawner sniperEnemySpawner = new GunEnemySpawner(player, randomMap, 10000, "Sniper enemy", "Survival");
        GunEnemySpawner droneEnemySpawner = new GunEnemySpawner(player, randomMap, 15000, "Drone enemy", "Survival");
        gunEnemySpawner = new GunEnemySpawner[]{normalEnemySpawner, tankEnemySpawner, flyEnemySpawner, sniperEnemySpawner, droneEnemySpawner};
        bombEnemySpawner = new BombEnemySpawner(player, randomMap, 6250, "Survival");

        ui = new UI(player);
    }

    public void reset(ShopManager shopManager, Customize customize) {
        gameOver = false;
        Map randomMap = mapGenerator.getRandomMap();

        //update player's attributes
        playerController.player.maxHealth = shopManager.playerUpgradeManager.playerMaxHealth;
        playerController.player.moveVel = shopManager.playerUpgradeManager.playerMoveVelocity;
        playerController.player.jumpVel = shopManager.playerUpgradeManager.playerJumpVelocity;
        playerController.player.maxJumpTime = shopManager.playerUpgradeManager.playerMaxJumpTime;
        playerController.player.healingSpeed = shopManager.playerUpgradeManager.playerHealingSpeed;
        playerController.reset(randomMap, new String[]{"Pistol", "Rifle", "Shotgun", "Grenade", "C4", "C4 controller", "Rocket launcher"}, customize.data.color);
        //update weapon's attributes

        //update pistol
        playerController.player.weaponInventory.pistol.damage = shopManager.weaponUpgradeManager.pistolDamage;
        playerController.player.weaponInventory.pistol.fireInterval = shopManager.weaponUpgradeManager.pistolFireInterval;
        playerController.player.weaponInventory.pistol.deflection = shopManager.weaponUpgradeManager.pistolDeflection;
        //update rifle
        playerController.player.weaponInventory.rifle.damage = shopManager.weaponUpgradeManager.rifleDamage;
        playerController.player.weaponInventory.rifle.deflection = shopManager.weaponUpgradeManager.rifleDeflection;
        playerController.player.weaponInventory.rifle.maxMag = shopManager.weaponUpgradeManager.rifleMaxMag;
        playerController.player.weaponInventory.rifle.fireInterval = shopManager.weaponUpgradeManager.rifleFireInterval;
        //update shotgun
        playerController.player.weaponInventory.shotgun.damage = shopManager.weaponUpgradeManager.shotgunDamage;
        playerController.player.weaponInventory.shotgun.maxMag = shopManager.weaponUpgradeManager.shotgunMaxMag;
        playerController.player.weaponInventory.shotgun.fireInterval = shopManager.weaponUpgradeManager.shotgunFireInterval;
        //update rocket launcher
        playerController.player.weaponInventory.rocketLauncher.damage = shopManager.weaponUpgradeManager.rocketLauncherDamage;
        playerController.player.weaponInventory.rocketLauncher.maxMag = shopManager.weaponUpgradeManager.rocketLauncherMaxMag;
        playerController.player.weaponInventory.rocketLauncher.fireInterval = shopManager.weaponUpgradeManager.rocketLauncherFireInterval;
        playerController.player.weaponInventory.rocketLauncher.rocketExplosionRadius = shopManager.weaponUpgradeManager.rocketExplosionRadius;
        playerController.player.weaponInventory.rocketLauncher.rocketVelocity = shopManager.weaponUpgradeManager.rocketVelocity;

        for (GunEnemySpawner spawner : gunEnemySpawner) {
            spawner.reset(randomMap);
        }
        bombEnemySpawner.reset(randomMap);
    }

    private void updateCamera() {
        double x = player.x + player.size / 2 - 683;
        double y;
        if (player.y + player.size / 2 + 374 <= player.map.length) {
            y = player.y + player.size / 2 - 374;
        } else {
            y = player.map.length - 748;
        }
        cam.setLocation(x, y);
    }

    public void update() {
        if (playerController.player.health <= 0) {
            gameOver = true;
        }
        playerController.update();
        updateCamera();
        mapGenerator.update();
        for (GunEnemySpawner spawner : gunEnemySpawner) {
            spawner.update();
        }
        bombEnemySpawner.update();
    }

    public void paint(Graphics2D g2d) {
        mapGenerator.paint(g2d);
        for (GunEnemySpawner spawner : gunEnemySpawner) {
            spawner.paint(g2d);
        }
        bombEnemySpawner.paint(g2d);
        playerController.paint(g2d);
        ui.paint(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        playerController.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerController.keyReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        playerController.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        playerController.mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        playerController.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        playerController.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        playerController.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
