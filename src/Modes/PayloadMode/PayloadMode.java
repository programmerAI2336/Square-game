package Modes.PayloadMode;

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

public class PayloadMode implements MouseListener, MouseMotionListener, KeyListener {
    private final Camera cam = new Camera();
    private final MapGenerator mapGenerator = new MapGenerator(cam);
    private final Player player;
    public final PlayerController playerController;
    private final GunEnemySpawner[] gunEnemySpawner;
    private final BombEnemySpawner bombEnemySpawner;
    public boolean gameOver;
    private final UI ui;

    public PayloadMode() {
        Map randomMap = mapGenerator.getRandomMap();
        player = new Player(randomMap);
        playerController = new PlayerController(player);

        GunEnemySpawner tankEnemySpawner = new GunEnemySpawner(player, randomMap, 1750, "Tank enemy", "Payload");
        GunEnemySpawner sniperEnemySpawner = new GunEnemySpawner(player, randomMap, 3750, "Sniper enemy", "Payload");
        GunEnemySpawner superDroneEnemySpawner = new GunEnemySpawner(player, randomMap, 15000, "Super drone enemy", "Payload");
        GunEnemySpawner grenadeLauncherEnemySpawner = new GunEnemySpawner(player, randomMap, 10000, "Grenade launcher enemy", "Payload");
        bombEnemySpawner = new BombEnemySpawner(player, randomMap, 6250, "Payload");
        gunEnemySpawner = new GunEnemySpawner[]{tankEnemySpawner, sniperEnemySpawner, superDroneEnemySpawner, grenadeLauncherEnemySpawner};

        ui = new UI(playerController.player);
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
        playerController.reset(randomMap, new String[]{"Pistol", "Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun"}, customize.data.color);
        //update pistol
        playerController.player.weaponInventory.pistol.damage = shopManager.weaponUpgradeManager.pistolDamage;
        playerController.player.weaponInventory.pistol.fireInterval = shopManager.weaponUpgradeManager.pistolFireInterval;
        playerController.player.weaponInventory.pistol.deflection = shopManager.weaponUpgradeManager.pistolDeflection;
        //update gatling
        playerController.player.weaponInventory.gatling.damage = shopManager.weaponUpgradeManager.gatlingDamage;
        playerController.player.weaponInventory.gatling.fireInterval = shopManager.weaponUpgradeManager.gatlingFireInterval;
        playerController.player.weaponInventory.gatling.maxMag = shopManager.weaponUpgradeManager.gatlingMaxMag;
        //update sniper
        playerController.player.weaponInventory.sniper.damage = shopManager.weaponUpgradeManager.sniperDamage;
        playerController.player.weaponInventory.sniper.fireInterval = shopManager.weaponUpgradeManager.sniperFireInterval;
        playerController.player.weaponInventory.sniper.maxMag = shopManager.weaponUpgradeManager.sniperMaxMag;
        //update rocket launcher
        playerController.player.weaponInventory.rocketLauncher.damage = shopManager.weaponUpgradeManager.rocketLauncherDamage;
        playerController.player.weaponInventory.rocketLauncher.maxMag = shopManager.weaponUpgradeManager.rocketLauncherMaxMag;
        playerController.player.weaponInventory.rocketLauncher.fireInterval = shopManager.weaponUpgradeManager.rocketLauncherFireInterval;
        playerController.player.weaponInventory.rocketLauncher.rocketExplosionRadius = shopManager.weaponUpgradeManager.rocketExplosionRadius;
        playerController.player.weaponInventory.rocketLauncher.rocketVelocity = shopManager.weaponUpgradeManager.rocketVelocity;
        //update missile launcher
        playerController.player.weaponInventory.missileLauncher.damage = shopManager.weaponUpgradeManager.missileLauncherDamage;
        playerController.player.weaponInventory.missileLauncher.maxMag = shopManager.weaponUpgradeManager.missileLauncherMaxMag;
        playerController.player.weaponInventory.missileLauncher.fireInterval = shopManager.weaponUpgradeManager.missileLauncherFireInterval;
        playerController.player.weaponInventory.missileLauncher.rocketExplosionRadius = shopManager.weaponUpgradeManager.missileExplosionRadius;
        playerController.player.weaponInventory.missileLauncher.rocketVelocity = shopManager.weaponUpgradeManager.missileVelocity;
        //update missile launcher
        playerController.player.weaponInventory.grenadeLauncher.damage = shopManager.weaponUpgradeManager.grenadeLauncherDamage;
        playerController.player.weaponInventory.grenadeLauncher.maxMag = shopManager.weaponUpgradeManager.grenadeLauncherMaxMag;
        playerController.player.weaponInventory.grenadeLauncher.fireInterval = shopManager.weaponUpgradeManager.grenadeLauncherFireInterval;
        playerController.player.weaponInventory.grenadeLauncher.ammoExplosionRadius = shopManager.weaponUpgradeManager.grenadeExplosionRadius;
        //update railgun
        playerController.player.weaponInventory.railgun.damage = shopManager.weaponUpgradeManager.railgunDamage;
        playerController.player.weaponInventory.railgun.maxMag = shopManager.weaponUpgradeManager.railgunMaxMag;
        playerController.player.weaponInventory.railgun.fireInterval = shopManager.weaponUpgradeManager.railgunFireInterval;

        for (GunEnemySpawner spawner : gunEnemySpawner) {
            spawner.reset(randomMap);
        }
        bombEnemySpawner.reset(randomMap);
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
