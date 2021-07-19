package Shooter.PlayerController;

import Weapon.Gun.Gun;
import Weapon.Launcher.MissileLauncher.MissileLauncher;
import Weapon.Launcher.RocketLauncher.RocketLauncher;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MouseController implements MouseMotionListener, MouseListener {
    private final Player player;
    private int mX, mY;
    private double mouseAngle;

    private boolean mousePressed;
    private int mouseStatus;

    public MouseController(Player player) {
        this.player = player;
    }

    public void reset() {
        mousePressed = false;
        mouseStatus = 0;
    }

    private void setMouseAngle() {
        double angle = Math.atan((mY - player.map.y - player.y - player.size / 2) / (mX - player.map.x - player.x - player.size / 2));

        if (mX > player.map.x + player.x + player.size / 2) {
            if (mY > player.map.y + player.y + player.size / 2) {
                mouseAngle = angle;
            } else {
                mouseAngle = angle + Math.toRadians(360);
            }
        } else if (mX < player.map.x + player.x + player.size / 2) {
            mouseAngle = angle + Math.toRadians(180);
        }
    }

    private void shootGun() {
        ArrayList<Gun> gun = new ArrayList<>(Arrays.asList(player.weaponInventory.pistol,
                player.weaponInventory.rifle,
                player.weaponInventory.shotgun,
                player.weaponInventory.sniper,
                player.weaponInventory.gatling,
                player.weaponInventory.grenadeLauncher,
                player.weaponInventory.railgun));
        ArrayList<String> gunName = new ArrayList<>(Arrays.asList("Pistol", "Rifle", "Shotgun", "Sniper", "Gatling", "Grenade launcher","Railgun"));

        String name = player.weaponInventory.weaponName;
        if (gunName.contains(player.weaponInventory.weaponName)) {
            gun.get(gunName.indexOf(name)).angle = mouseAngle;
            if (mousePressed) {
                gun.get(gunName.indexOf(name)).shoot();
                if (!gun.get(gunName.indexOf(name)).ammo.isEmpty() && gun.get(gunName.indexOf(name)).ammo.get(gun.get(gunName.indexOf(name)).ammo.size() - 1).timer == 1) {
                    player.velX -= gun.get(gunName.indexOf(name)).recoil * Math.cos(gun.get(gunName.indexOf(name)).angle);
                    player.velY -= gun.get(gunName.indexOf(name)).recoil * Math.sin(gun.get(gunName.indexOf(name)).angle);
                }
                if (name.equals("Grenade launcher")) {
                    if (!player.weaponInventory.grenadeLauncher.explosiveAmmo.isEmpty() && player.weaponInventory.grenadeLauncher.explosiveAmmo.get(player.weaponInventory.grenadeLauncher.explosiveAmmo.size() - 1).timer == 1) {
                        player.velX -= player.weaponInventory.grenadeLauncher.recoil * Math.cos(player.weaponInventory.grenadeLauncher.angle);
                        player.velY -= player.weaponInventory.grenadeLauncher.recoil * Math.sin(player.weaponInventory.grenadeLauncher.angle);
                    }
                }
            }
        }
    }

    private void throwGrenade() {
        if (player.weaponInventory.weaponName.equals("Grenade")) {
            if (mousePressed) {
                if (mouseStatus == 0) {
                    player.weaponInventory.grenadeManager.throwGrenade(mouseAngle);
                    mouseStatus = 1;
                }
            } else {
                mouseStatus = 0;
            }
        }
    }

    private void throwC4() {
        if (player.weaponInventory.weaponName.equals("C4")) {
            if (mousePressed) {
                if (mouseStatus == 0) {
                    player.weaponInventory.c4Manager.throwC4(mouseAngle);
                    mouseStatus = 1;
                }
            } else {
                mouseStatus = 0;
            }
        }
    }

    private void activateC4() {
        if (player.weaponInventory.weaponName.equals("C4 controller")) {
            player.weaponInventory.c4Controller.angle = mouseAngle;
            if (mousePressed) {
                if (mouseStatus == 0) {
                    player.weaponInventory.c4Controller.activate = true;
                    mouseStatus = 1;
                }
            } else {
                mouseStatus = 0;
                player.weaponInventory.c4Controller.activate = false;
            }
        }
    }

    private void shootRocketLauncher() {
        if (player.weaponInventory.weaponName.equals("Rocket launcher")) {
            player.weaponInventory.rocketLauncher.angle = mouseAngle;
            if (mousePressed) {
                player.weaponInventory.rocketLauncher.shoot();
                if (!player.weaponInventory.rocketLauncher.rocket.isEmpty() && player.weaponInventory.rocketLauncher.rocket.get(player.weaponInventory.rocketLauncher.rocket.size() - 1).timer == 1) {
                    //player will fall back at the moment the latest ammo start flying.
                    player.velX -= RocketLauncher.RECOIL * Math.cos(player.weaponInventory.rocketLauncher.angle);
                    player.velY -= RocketLauncher.RECOIL * Math.sin(player.weaponInventory.rocketLauncher.angle);
                }
            }
        }
    }

    private void shootMissileLauncher() {
        if (player.weaponInventory.weaponName.equals("Missile launcher")) {
            player.weaponInventory.missileLauncher.angle = mouseAngle;
            if (mousePressed) {
                player.weaponInventory.missileLauncher.shoot();
                if (!player.weaponInventory.missileLauncher.missile.isEmpty() && player.weaponInventory.missileLauncher.missile.get(player.weaponInventory.missileLauncher.missile.size() - 1).timer == 1) {
                    //player will fall back at the moment the latest ammo start flying.
                    player.velX -= MissileLauncher.RECOIL * Math.cos(player.weaponInventory.missileLauncher.angle);
                    player.velY -= MissileLauncher.RECOIL * Math.sin(player.weaponInventory.missileLauncher.angle);
                }
            }
        }
    }

    public void handle() {
        if (player.health > 0) {
            setMouseAngle();
            shootGun();
            throwGrenade();
            throwC4();
            activateC4();
            shootRocketLauncher();
            shootMissileLauncher();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1) {
            mousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1) {
            mousePressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
