package Weapon.ExplosiveWeapon.C4;

import Map.Obstacle;

public class Physic {
    private final C4 c4;

    public Physic(C4 weapon) {
        this.c4 = weapon;
    }

    private void gravity() {
        if (c4.thrown) {
            if (!c4.stick) {
                c4.velY += 0.015;
            }
        }
    }

    private void friction() {
        final float friction = 0.005f;
        if (c4.velX >= friction / 2) {
            c4.velX -= friction;
        }
        if (c4.velX <= -friction / 2) {
            c4.velX += friction;
        }
        if (Math.abs(c4.velX) < friction / 2) {
            c4.velX = 0;
        }
    }

    private void wallCollision() {
        for (Obstacle obs : c4.map.obstacle) {
            if (c4.velX > 0) {
                for (int x = (int) (c4.x + c4.width); x <= c4.x + c4.width + c4.velX; x++) {
                    if (x == obs.x) {
                        if (c4.y > obs.y - c4.length && c4.y < obs.y + obs.length) {
                            c4.stick = true;
                            c4.x = x - (c4.width - c4.length) / 2 - c4.length;
                            //stop rotating
                            c4.bodyAngle = Math.toRadians(270);
                        }
                    }
                }
            } else if (c4.velX < 0) {
                for (int x = (int) c4.x; x >= c4.x + c4.velX; x--) {
                    if (x == obs.x + obs.width) {
                        if (c4.y > obs.y - c4.length && c4.y < obs.y + obs.length) {
                            c4.stick = true;
                            c4.x = x - (c4.width - c4.length) / 2;
                            //stop rotating
                            c4.bodyAngle = Math.toRadians(90);
                        }
                    }
                }
            }

            if (c4.velY > 0) {
                for (int y = (int) (c4.y + c4.length); y <= c4.y + c4.length + c4.velY; y++) {
                    if (y == obs.y) {
                        if (c4.x > obs.x - c4.width && c4.x < obs.x + obs.width) {
                            c4.stick = true;
                            c4.y = y - c4.length;
                            //stop rotating
                            c4.bodyAngle = Math.toRadians(0);
                        }
                    }
                }
            } else if (c4.velY < 0) {
                for (int y = (int) c4.y; y >= c4.y + c4.velY; y--) {
                    if (y == obs.y + obs.length) {
                        if (c4.x > obs.x - c4.width && c4.x < obs.x + obs.width) {
                            c4.stick = true;
                            c4.y = y;
                            //stop rotating
                            c4.bodyAngle = Math.toRadians(180);
                        }
                    }
                }
            }

            if (c4.stick) {
                c4.velX = 0;
                c4.velY = 0;
                c4.bodyRotateVel = 0;
            }
        }
    }

    public void handle() {
        gravity();
        friction();
        wallCollision();
    }
}
