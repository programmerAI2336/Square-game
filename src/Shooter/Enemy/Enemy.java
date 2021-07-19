package Shooter.Enemy;

import Item.Item;
import Map.Map;
import Shooter.*;
import Shooter.PlayerController.Player;
import Audio.Sound;
import Weapon.ExplosiveWeapon.C4.C4;
import Weapon.ExplosiveWeapon.Grenade.Grenade;
import Weapon.Gun.Ammo;
import Weapon.Gun.GrenadeLauncher.ExplosiveAmmo;
import Weapon.Gun.Gun;
import Weapon.Gun.Railgun.Laser;
import Weapon.Launcher.MissileLauncher.Missile;
import Weapon.Launcher.RocketLauncher.Rocket;

import java.awt.*;
import java.util.Random;

public class Enemy extends Actor {
    protected final Player player;
    protected int closestDistanceToPlayer;
    protected int shootRange;
    protected boolean canAvoidAmmo = false;
    public Gun gun;
    protected final Random random = new Random();
    public Item item;
    public ScoreInformer scoreInformer;

    public Enemy(Map map, Player player, double x, double y, String[] itemName) {
        super(map);
        this.player = player;
        this.x = x;
        this.y = y;
        maxJumpTime = 1;
    }

    protected void initItem(String[] weaponName) {
        //Size of item must be smaller than size of enemy.Therefore, the fly enemy won't carry item.
        int num = random.nextInt(10);
        if (num <= 4 && size >= Item.SIZE) {
            item = new Item(map, this, weaponName);
        }
    }

    protected void followPlayer() {
        if (health > 0 && player.health > 0) {
            double distanceToPlayer = Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2));
            if (distanceToPlayer > closestDistanceToPlayer && Math.abs(velX) <= moveVel /*move velocity doesn't reach max value*/) {
                //When enemy takes damage from the player, it will fall back if it isn't dead. Fall back velocity can be bigger than max value of move velocity and if it bigger, the following code won't be executed.
                //Fall back velocity will be decreased by friction.
                if (player.x > x) {
                    move(0.04f);
                }
                if (player.x < x) {
                    move(-0.04f);
                }
            }
        } else if (health > 0 && player.health <= 0) {
            move(0);
        }
    }

    protected void shootPlayer() {
        if (health > 0 && player.health > 0 && gun != null) {
            double angle = Math.atan((player.y + player.size / 2 - y - size / 2) / (player.x + player.size / 2 - x - size / 2));
            if (player.x + player.size / 2 > x + size / 2) {
                if (player.y + player.size / 2 > y + size / 2) {
                    gun.angle = angle;
                } else {
                    gun.angle = angle + Math.toRadians(360);
                }
            } else if (player.x + player.size / 2 < x + size / 2) {
                gun.angle = angle + Math.toRadians(180);
            }

            double distanceToPlayer = Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2));
            if (distanceToPlayer <= shootRange) {
                gun.shoot();
            }
        }
    }

    protected void takeDamageFromPlayer() {
        if (health > 0) {
            takeDamageFromGun();
            takeDamageFromRailgun();
            takeDamageFromGrenadeLauncher();
            takeDamageFromGrenade();
            takeDamageFromC4();
            takeDamageFromRocketLauncher();
            takeDamageFromMissileLauncher();
            if (health <= 0) {
                player.score += score;
                player.kill++;
                deadEffectEmitter.setPosition(x + size / 2, y + size / 2);
                deadEffectEmitter.spread();
            }
        }

    }

    private void takeDamageFromGun() {
        Gun[] allGun = new Gun[]{
                player.weaponInventory.pistol,
                player.weaponInventory.rifle,
                player.weaponInventory.shotgun,
                player.weaponInventory.sniper,
                player.weaponInventory.gatling};
        for (Gun gun : allGun) {
            for (Ammo ammo : gun.ammo) {
                if (ammo.appear && ammo.getBound().intersects(getBound())) {
                    //fal back
                    velX += gun.recoil * Math.cos(gun.angle);
                    velY += gun.recoil * Math.sin(gun.angle);
                    takeDamage(gun.damage);
                    ammo.appear = false;
                }
            }
        }
    }

    private void takeDamageFromRailgun() {
        for (Laser laser : player.weaponInventory.railgun.laser) {
            if (laser.timer == 1) {
                if (laser.angle <= Math.toRadians(90)) {
                    for (int x = (int) laser.x; x <= laser.x + Laser.RANGE * Math.cos(laser.angle); x++) {
                        int y = (int) (laser.y + (x - laser.x) * Math.tan(laser.angle));
                        if ((this.x <= x && x <= this.x + this.size) && (this.y <= y && y <= this.y + this.size)) {
                            health -= player.weaponInventory.railgun.damage;
                            break;
                        }
                    }
                } else if (laser.angle <= Math.toRadians(180)) {
                    for (int x = (int) laser.x; x >= laser.x + Laser.RANGE * Math.cos(laser.angle); x--) {
                        int y = (int) (laser.y + (laser.x - x) * Math.tan(Math.toRadians(180) - laser.angle));
                        if ((this.x <= x && x <= this.x + this.size) && (this.y <= y && y <= this.y + this.size)) {
                            health -= player.weaponInventory.railgun.damage;
                            break;
                        }
                    }
                } else if (laser.angle <= Math.toRadians(270)) {
                    for (int x = (int) laser.x; x >= laser.x + Laser.RANGE * Math.cos(laser.angle); x--) {
                        int y = (int) (laser.y - (laser.x - x) * Math.tan(laser.angle - Math.toRadians(180)));
                        if ((this.x <= x && x <= this.x + this.size) && (this.y <= y && y <= this.y + this.size)) {
                            health -= player.weaponInventory.railgun.damage;
                            break;
                        }
                    }
                } else if (laser.angle < Math.toRadians(360)) {
                    for (int x = (int) laser.x; x <= laser.x + Laser.RANGE * Math.cos(laser.angle); x++) {
                        int y = (int) (laser.y - (x - laser.x) * Math.tan(Math.toRadians(360) - laser.angle));
                        if ((this.x <= x && x <= this.x + this.size) && (this.y <= y && y <= this.y + this.size)) {
                            health -= player.weaponInventory.railgun.damage;
                            break;
                        }
                    }
                }
            }
        }

    }

    private void takeDamageFromGrenade() {
        for (Grenade grenade : player.weaponInventory.grenadeManager.grenade) {
            if (grenade.timer == Grenade.EXPLOSION_TIME + 1) {
                //grenade is just activated

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
        for (C4 c4 : player.weaponInventory.c4Manager.c4) {
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

    private void takeDamageFromRocketLauncher() {
        for (Rocket rocket : player.weaponInventory.rocketLauncher.rocket) {
            if (rocket.appear && rocket.getBound().intersects(getBound())) {
                rocket.appear = false;
            }
            if (rocket.explosionTimer == 1) {
                double distanceToRocket = Math.sqrt(Math.pow(x + size / 2 - rocket.x - rocket.width / 2, 2) + Math.pow(y + size / 2 - rocket.y - rocket.length / 2, 2));

                if (distanceToRocket <= rocket.explosionRadius) {

                    takeDamage((int) (player.weaponInventory.rocketLauncher.damage * (1 - distanceToRocket / rocket.explosionRadius)));

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
        for (ExplosiveAmmo ammo : player.weaponInventory.grenadeLauncher.explosiveAmmo) {
            if (ammo.getBound().intersects(getBound()) && ammo.appear) {
                ammo.appear = false;
            }
            if (ammo.explosionTimer == 1) {
                double distanceToAmmo = Math.sqrt(Math.pow(x + size / 2 - ammo.x - ammo.size / 2, 2) + Math.pow(y + size / 2 - ammo.y - ammo.size / 2, 2));
                if (distanceToAmmo <= ammo.explosionRadius) {
                    takeDamage((int) (player.weaponInventory.rocketLauncher.damage * (1 - distanceToAmmo / ammo.explosionRadius)));
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

    private void takeDamageFromMissileLauncher() {
        for (Missile missile : player.weaponInventory.missileLauncher.missile) {
            if (missile.appear && missile.getBound().intersects(getBound())) {
                missile.appear = false;
            }

            if (missile.explosionTimer == 1) {
                double distanceToMissile = Math.sqrt(Math.pow(x + size / 2 - missile.x - missile.width / 2, 2) + Math.pow(y + size / 2 - missile.y - missile.length / 2, 2));

                if (distanceToMissile <= missile.explosionRadius) {

                    takeDamage((int) (player.weaponInventory.missileLauncher.damage * (1 - distanceToMissile / missile.explosionRadius)));

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


    public void damagePlayer() {
        if (player.health > 0 && gun != null) {
            for (Ammo ammo : gun.ammo) {
                if (ammo.appear && ammo.getBound().intersects(player.getBound())) {
                    player.velX += gun.recoil * Math.cos(gun.angle);
                    player.velY += gun.recoil * Math.sin(gun.angle);
                    player.takeDamage(gun.damage);
                    Sound.play("Assets\\Sounds\\Hit.wav");
                    ammo.appear = false;
                    if (player.health <= 0) {
                        player.deadEffectEmitter.setPosition(player.x + player.size / 2, player.y + player.size / 2);
                        player.deadEffectEmitter.spread();
                    }
                }
            }
        }
    }

    private void releaseItem() {
        if (item != null) {
            item.update();
            if (health <= 0 && !item.picked && item.timer < 3750 && player.getBound().intersects(item.getBound()) && player.health > 0) {
                switch (item.name) {
                    case "Several cents" -> player.cent += 9;
                    case "Cent" -> player.cent++;
                    case "Health" -> player.health = player.maxHealth;
                    case "Rifle" -> player.weaponInventory.rifle.reload();
                    case "Gatling" -> player.weaponInventory.gatling.reload();
                    case "Shotgun" -> player.weaponInventory.shotgun.reload();
                    case "Sniper" -> player.weaponInventory.sniper.reload();
                    case "Grenade launcher" -> player.weaponInventory.grenadeLauncher.reload();
                    case "Grenade" -> player.weaponInventory.grenadeManager.reload();
                    case "C4" -> player.weaponInventory.c4Manager.reload();
                    case "Rocket launcher" -> player.weaponInventory.rocketLauncher.reload();
                    case "Missile launcher" -> player.weaponInventory.missileLauncher.reload();
                    case "Railgun" -> player.weaponInventory.railgun.reload();
                }
                if (item.name.equals("Cent") || item.name.equals("Several cents")) {
                    Sound.play("Assets\\Sounds\\Pickup.wav");
                } else if(item.name.equals("Health")){
                    Sound.play("Assets\\Sounds\\Heal.wav");
                } else {
                    Sound.play("Assets\\Sounds\\Pickup.wav");
                }
                if (!item.name.equals("Health")) {
                    item.spreadEffect(Color.YELLOW);
                } else {
                    item.spreadEffect(Color.WHITE);
                }
                item.picked = true;
            }
        }
    }

    private void avoidPlayerAmmo() {
        if (canAvoidAmmo && health > 0) {
            boolean canHit = false;
            Gun[] allGun = new Gun[]{player.weaponInventory.pistol, player.weaponInventory.rifle, player.weaponInventory.shotgun, player.weaponInventory.gatling};
            for (Gun gun : allGun) {
                for (Ammo ammo : gun.ammo) {
                    if (ammo.appear) {
                        double distanceToAmmo = Math.sqrt(Math.pow(x + size / 2 - ammo.x - ammo.size / 2, 2) + Math.pow(y + size / 2 - ammo.y - ammo.size / 2, 2));
                        if (distanceToAmmo <= 600) {
                            //In bound

                            //Case 1:
                            if (ammo.x + ammo.size / 2 >= x + size && ammo.y + ammo.size / 2 <= y) {
                                double angleFromAmmo = Math.toRadians(90) + Math.abs(Math.atan((x + size / 2 - ammo.x - ammo.size / 2) / (y + size / 2 - ammo.y - ammo.size / 2)));
                                double a1 = Math.abs(Math.atan((x + size - ammo.x - ammo.size / 2) / (y + size - ammo.y - ammo.size / 2)));
                                double a2 = Math.abs(Math.atan((x + size / 2 - ammo.x - ammo.size / 2) / (y + size / 2 - ammo.y - ammo.size / 2)));
                                double def1 = a2 - a1;
                                double a3 = Math.abs(Math.atan((x - ammo.x - ammo.size / 2) / (y - ammo.y - ammo.size / 2)));
                                double def2 = a3 - a2;
                                if (angleFromAmmo - def1 <= ammo.angle && ammo.angle <= angleFromAmmo + def2) {
                                    move(-1f);
                                    jump("");
                                    canHit = true;
                                }
                            }
                            //Case 2:
                            if (ammo.x + ammo.size / 2 <= x + size && ammo.y + ammo.size / 2 <= y) {
                                double angleFromAmmo = Math.toRadians(90) - Math.abs(Math.atan((x + size / 2 - ammo.x - ammo.size / 2) / (y + size / 2 - ammo.y - ammo.size / 2)));
                                double a1 = Math.abs(Math.atan((x - ammo.x - ammo.size / 2) / (y + size - ammo.y - ammo.size / 2)));
                                double a2 = Math.abs(Math.atan((x + size / 2 - ammo.x - ammo.size / 2) / (y + size / 2 - ammo.y - ammo.size / 2)));
                                double def1 = a2 - a1;
                                double a3 = Math.abs(Math.atan((x + size - ammo.x - ammo.size / 2) / (y - ammo.y - ammo.size / 2)));
                                double def2 = a3 - a2;
                                if (angleFromAmmo + def1 >= ammo.angle && ammo.angle >= angleFromAmmo - def2) {
                                    move(1f);
                                    jump("");
                                    canHit = true;
                                }
                            }

                            if (ammo.x + ammo.size / 2 > x + size && ammo.y + ammo.size / 2 > y && ammo.y + ammo.size / 2 < y + size) {
                                double def1 = Math.abs(Math.atan((y - ammo.y - ammo.size / 2) / (x + size - ammo.x - ammo.size / 2)));
                                double def2 = Math.abs(Math.atan((y + size - ammo.y - ammo.size / 2) / (x + size - ammo.x - ammo.size / 2)));
                                if (ammo.angle <= Math.toRadians(180) + def1 && ammo.angle >= Math.toRadians(180) - def2) {
                                    jump("");
                                    canHit = true;
                                }
                            }

                            if (ammo.x + ammo.size / 2 < x && ammo.y + ammo.size / 2 > y && ammo.y + ammo.size / 2 < y + size) {
                                double def1 = Math.abs(Math.atan((y - ammo.y - ammo.size / 2) / (x + size - ammo.x - ammo.size / 2)));
                                double def2 = Math.abs(Math.atan((y + size - ammo.y - ammo.size / 2) / (x + size - ammo.x - ammo.size / 2)));
                                if (ammo.angle >= Math.toRadians(360) - def1 || ammo.angle <= def2) {
                                    jump("");
                                    canHit = true;
                                }
                            }
                        }

                    }
                }
            }
            if (!canHit) {
                followPlayer();
            }
        }
    }

    public void update() {
        avoidPlayerAmmo();
        shootPlayer();
        takeDamageFromPlayer();
        damagePlayer();
        super.update();
        if (gun != null) {
            gun.update();
        }
        releaseItem();
        scoreInformer.update();
    }

    public void paint(Graphics2D g2d) {
        scoreInformer.paint(g2d);
        super.paint(g2d);
        if (item != null) {
            item.paint(g2d);
        }
        if (gun != null) {
            gun.paintAmmo(g2d);
            gun.paintGun(g2d);
        }
    }
}
