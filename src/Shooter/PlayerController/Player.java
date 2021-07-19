package Shooter.PlayerController;

import Map.Map;
import Shooter.Actor;
import Weapon.ExplosiveWeapon.C4.C4;
import Weapon.ExplosiveWeapon.Grenade.Grenade;
import Weapon.Gun.GrenadeLauncher.ExplosiveAmmo;
import Weapon.Launcher.MissileLauncher.Missile;
import Weapon.Launcher.RocketLauncher.Rocket;

import java.awt.*;

public class Player extends Actor {
    public WeaponInventory weaponInventory;
    public int cent;
    public int kill;
    public int survivalTime;
    public double healingSpeed;

    public Player(Map map) {
        super(map);
        color = Color.CYAN;
        x = 600;
        y = 300;
        size = 36;
        moveVel = 2f;
        jumpVel = 1.5f;
        maxJumpTime = 2;
        maxHealth = 200;
        health = maxHealth;
        healingSpeed = 0.01;
        weaponInventory = new WeaponInventory(map, this);
        initDeadEffectEmitter();
    }

    public void reset(Map map, Color color) {
        this.map = map;
        cent = 0;
        kill = 0;
        survivalTime = 0;
        x = map.width / 2 - size / 2;
        y = map.length / 2 - size / 2;
        this.color = color;
        velX = 0;
        velY = 0;
        jumpTime = 0;
        health = maxHealth;
        score = 0;
        weaponInventory = new WeaponInventory(map, this);
        weaponInventory.weaponName = "";
        initDeadEffectEmitter();
    }

    private void takeDamageFromWeapon() {
        if (health > 0) {
            takeDamageFromGrenade();
            takeDamageFromC4();
            takeDamageFromGrenadeLauncher();
            takeDamageFromRocket();
            takeDamageFromMissile();
        }
    }

    private void takeDamageFromGrenade() {
        for (Grenade grenade : weaponInventory.grenadeManager.grenade) {
            if (grenade.timer == Grenade.EXPLOSION_TIME + 1) {
                //grenade is just triggered

                //distance from enemy to grenade
                double distanceToGrenade = Math.sqrt(Math.pow(x + size / 2 - grenade.x - grenade.width / 2, 2) + Math.pow(y + size / 2 - grenade.y - grenade.length / 2, 2));
                if (distanceToGrenade <= grenade.explosionRadius) {
                    //enemy's in the explosion

                    takeDamage((int) (grenade.damage * (1 - (distanceToGrenade / grenade.explosionRadius))));

                    double fallBackAngle = 0;
                    double angle = Math.atan((y + size / 2 - grenade.y - grenade.length / 2) / (x + size / 2 - grenade.x - grenade.width / 2));
                    //calculating fall back angle
                    if (x + size / 2 > grenade.x + grenade.width / 2) {
                        if (y + size / 2 > grenade.y + grenade.length / 2) {
                            fallBackAngle = angle;
                        } else {
                            fallBackAngle = angle + Math.toRadians(360);
                        }
                    } else if (x + size / 2 < grenade.x + grenade.width / 2) {
                        fallBackAngle = angle + Math.toRadians(180);
                    }

                    //fall back
                    velX = grenade.power * (1 - distanceToGrenade / grenade.explosionRadius) * Math.cos(fallBackAngle);
                    velY = grenade.power * (1 - distanceToGrenade / grenade.explosionRadius) * Math.sin(fallBackAngle);
                }

            }
        }
    }

    private void takeDamageFromC4() {
        for (C4 c4 : weaponInventory.c4Manager.c4) {
            if (c4.timer == 1) {
                //c4 is just activated
                double distanceToGrenade = Math.sqrt(Math.pow(x + size / 2 - c4.x - c4.width / 2, 2) + Math.pow(y + size / 2 - c4.y - c4.length / 2, 2));
                if (distanceToGrenade <= c4.explosionRadius) {
                    //enemy's in the explosion

                    takeDamage((int) (c4.damage * (1 - (distanceToGrenade / c4.explosionRadius))));

                    double fallBackAngle = 0;
                    double angle = Math.atan((y + size / 2 - c4.y - c4.length / 2) / (x + size / 2 - c4.x - c4.width / 2));
                    //calculating fall back angle
                    if (x + size / 2 > c4.x + c4.width / 2) {
                        if (y + size / 2 > c4.y + c4.length / 2) {
                            fallBackAngle = angle;
                        } else {
                            fallBackAngle = angle + Math.toRadians(360);
                        }
                    } else if (x + size / 2 < c4.x + c4.width / 2) {
                        fallBackAngle = angle + Math.toRadians(180);
                    }

                    //fall back
                    velX = c4.power * (1 - distanceToGrenade / c4.explosionRadius) * Math.cos(fallBackAngle);
                    velY = c4.power * (1 - distanceToGrenade / c4.explosionRadius) * Math.sin(fallBackAngle);
                }
            }
        }
    }

    private void takeDamageFromRocket() {
        for (Rocket rocket : weaponInventory.rocketLauncher.rocket) {

            if (rocket.explosionTimer == 1) {
                double distanceToRocket = Math.sqrt(Math.pow(x + size / 2 - rocket.x - rocket.width / 2, 2) + Math.pow(y + size / 2 - rocket.y - rocket.length / 2, 2));

                if (distanceToRocket <= rocket.explosionRadius) {

                    takeDamage((int) (weaponInventory.rocketLauncher.damage * (1 - distanceToRocket / rocket.explosionRadius)));

                    double fallBackAngle = 0;
                    double angle = Math.atan((y + size / 2 - rocket.y - rocket.length / 2) / (x + size / 2 - rocket.x - rocket.width / 2));
                    //calculating fall back angle
                    if (x + size / 2 > rocket.x + rocket.width / 2) {
                        if (y + size / 2 > rocket.y + rocket.length / 2) {
                            fallBackAngle = angle;
                        } else {
                            fallBackAngle = angle + Math.toRadians(360);
                        }
                    } else if (x + size / 2 < rocket.x + rocket.width / 2) {
                        fallBackAngle = angle + Math.toRadians(180);
                    }

                    velX = rocket.power * (1 - distanceToRocket / rocket.explosionRadius) * Math.cos(fallBackAngle);
                    velY = rocket.power * (1 - distanceToRocket / rocket.explosionRadius) * Math.sin(fallBackAngle);
                }
            }
        }
    }

    private void takeDamageFromGrenadeLauncher() {
        for (ExplosiveAmmo ammo : weaponInventory.grenadeLauncher.explosiveAmmo) {
            if (ammo.explosionTimer == 1) {
                double distanceToAmmo = Math.sqrt(Math.pow(x + size / 2 - ammo.x - ammo.size / 2, 2) + Math.pow(y + size / 2 - ammo.y - ammo.size / 2, 2));
                if (distanceToAmmo <= ammo.explosionRadius) {
                    takeDamage((int) (weaponInventory.rocketLauncher.damage * (1 - distanceToAmmo / ammo.explosionRadius)));
                    double fallBackAngle = 0;
                    double angle = Math.atan((y + size / 2 - ammo.y - ammo.size / 2) / (x + size / 2 - ammo.x - ammo.size / 2));
                    //calculating fall back angle
                    if (x + size / 2 > ammo.x + ammo.size / 2) {
                        if (y + size / 2 > ammo.y + ammo.size / 2) {
                            fallBackAngle = angle;
                        } else {
                            fallBackAngle = angle + Math.toRadians(360);
                        }
                    } else if (x + size / 2 < ammo.x + ammo.size / 2) {
                        fallBackAngle = angle + Math.toRadians(180);
                    }

                    velX = ammo.power * (1 - distanceToAmmo / ammo.explosionRadius) * Math.cos(fallBackAngle);
                    velY = ammo.power * (1 - distanceToAmmo / ammo.explosionRadius) * Math.sin(fallBackAngle);
                }
            }
        }

    }

    private void takeDamageFromMissile() {
        for (Missile missile : weaponInventory.missileLauncher.missile) {

            if (missile.explosionTimer == 1) {
                double distanceToMissile = Math.sqrt(Math.pow(x + size / 2 - missile.x - missile.width / 2, 2) + Math.pow(y + size / 2 - missile.y - missile.length / 2, 2));

                if (distanceToMissile <= missile.explosionRadius) {

                    takeDamage((int) (weaponInventory.missileLauncher.damage * (1 - distanceToMissile / missile.explosionRadius)));

                    double fallBackAngle = 0;
                    double angle = Math.atan((y + size / 2 - missile.y - missile.length / 2) / (x + size / 2 - missile.x - missile.width / 2));
                    //calculating fall back angle
                    if (x + size / 2 > missile.x + missile.width / 2) {
                        if (y + size / 2 > missile.y + missile.length / 2) {
                            fallBackAngle = angle;
                        } else {
                            fallBackAngle = angle + Math.toRadians(360);
                        }
                    } else if (x + size / 2 < missile.x + missile.width / 2) {
                        fallBackAngle = angle + Math.toRadians(180);
                    }

                    velX = missile.power * (1 - distanceToMissile / missile.explosionRadius) * Math.cos(fallBackAngle);
                    velY = missile.power * (1 - distanceToMissile / missile.explosionRadius) * Math.sin(fallBackAngle);
                }
            }
        }
    }

    private void heal() {
        if (health > 0 && health < maxHealth) {
            health += healingSpeed;
        }
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    private void countSurvivalTime() {
        if (health > 0) {
            survivalTime++;
        }
    }

    public void update() {
        super.update();
        weaponInventory.update();
        takeDamageFromWeapon();
        heal();
        countSurvivalTime();
    }

    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        weaponInventory.paint(g2d);
    }
}
