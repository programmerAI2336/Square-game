package Weapon.ExplosiveWeapon.Grenade;

import Map.Obstacle;

public class Physic {
    private final Grenade grenade;

    public Physic(Grenade weapon) {
        this.grenade = weapon;
    }

    private void gravity() {
        if (grenade.thrown) {
            final float gravity = 0.015f;
            grenade.velY += gravity;
        }
    }

    private void friction() {
        final float friction = 0.005f;
        if (grenade.velX >= friction / 2) {
            grenade.velX -= friction;
        }
        if (grenade.velX <= -friction / 2) {
            grenade.velX += friction;
        }
        if (Math.abs(grenade.velX) < friction / 2) {
            grenade.velX = 0;
        }
    }

    private void wallCollision(){
        for (Obstacle obs : grenade.map.obstacle) {

            if (grenade.velX > 0) {
                for (int x = (int) (grenade.x + grenade.width); x <= grenade.x + grenade.width + grenade.velX; x++) {
                    if (x == obs.x) {
                        if (grenade.y > obs.y - grenade.length && grenade.y < obs.y + obs.length) {
                            grenade.velX = 0;
                            grenade.x = x - grenade.width;
                        }
                    }
                }
            } else if (grenade.velX < 0) {
                for (int x = (int) grenade.x; x >= grenade.x + grenade.velX; x--) {
                    if (x == obs.x + obs.width) {
                        if (grenade.y > obs.y - grenade.length && grenade.y < obs.y + obs.length) {
                            grenade.velX = 0;
                            grenade.x = x;
                        }
                    }
                }
            }

            if (grenade.velY > 0) {
                for (int y = (int) (grenade.y + grenade.length); y <= grenade.y + grenade.length + grenade.velY; y++) {
                    if (y == obs.y) {
                        if (grenade.x > obs.x - grenade.width && grenade.x < obs.x + obs.width) {
                            grenade.velY = 0;
                            grenade.y = y - grenade.length;
                        }
                    }
                }
            } else if (grenade.velY < 0) {
                for (int y = (int) grenade.y; y >= grenade.y + grenade.velY; y--) {
                    if (y == obs.y + obs.length) {
                        if (grenade.x > obs.x - grenade.width && grenade.x < obs.x + obs.width) {
                            grenade.velY = 0;
                            grenade.y = y;
                        }
                    }
                }
            }
        }
    }

    public void handle() {
        gravity();
        friction();
        wallCollision();
    }
}
