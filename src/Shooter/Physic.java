package Shooter;

import Map.Obstacle;

public class Physic {
    public Actor actor;

    public Physic(Actor actor) {
        this.actor = actor;
    }

    private void gravity() {
        final float gravity = 0.015f;
        actor.velY += gravity;
    }

    private void friction() {
        final float friction = 0.02f;
        if (actor.velX > friction / 2) {
            actor.velX -= friction;
        }
        if (actor.velX < -friction / 2) {
            actor.velX += friction;
        }
        if (Math.abs(actor.velX) <= friction / 2) {
            actor.velX = 0;
        }
    }

    private void wallCollision() {
        for (Obstacle obs : actor.map.obstacle) {

            if (actor.velX > 0) {
                for (int x = (int) (actor.x + actor.size); x <= actor.x + actor.size + actor.velX; x++) {
                    if (x == obs.x) {
                        if (actor.y > obs.y - actor.size && actor.y < obs.y + obs.length) {
                            actor.velX = 0;
                            actor.x = x - actor.size;
                        }
                    }
                }
            } else if (actor.velX < 0) {
                for (int x = (int) actor.x; x >= actor.x + actor.velX; x--) {
                    if (x == obs.x + obs.width) {
                        if (actor.y > obs.y - actor.size && actor.y < obs.y + obs.length) {
                            actor.velX = 0;
                            actor.x = x;
                        }
                    }
                }
            }

            if (actor.velY > 0) {
                for (int y = (int) (actor.y + actor.size); y <= actor.y + actor.size + actor.velY; y++) {
                    if (y == obs.y) {
                        if (actor.x > obs.x - actor.size && actor.x < obs.x + obs.width) {
                            actor.velY = 0;
                            actor.y = y - actor.size;
                            actor.jumpTime = 0;
                        }
                    }
                }
            } else if (actor.velY < 0) {
                for (int y = (int) actor.y; y >= actor.y + actor.velY; y--) {
                    if (y == obs.y + obs.length) {
                        if (actor.x > obs.x - actor.size && actor.x < obs.x + obs.width) {
                            actor.velY = 0;
                            actor.y = y;
                            actor.jumpTime = 0;
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
