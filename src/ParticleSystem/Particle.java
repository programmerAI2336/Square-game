package ParticleSystem;

import Map.Map;

import java.awt.*;

public class Particle {
    private final Map map;
    private double x;
    private double y;
    private double velX, velY;
    private double size;
    private final Color color;
    public int lifeTimer;
    public final int age;
    private final String shape;
    private boolean physic;


    public Particle(Map map, double size, Color color, int age, String shape, boolean physic) {
        this.map = map;
        this.color = color;
        this.age = age;
        this.lifeTimer = age;
        this.shape = shape;
        this.size = size;
    }

    public void update() {
        x += velX;
        y += velY;
        gravity();
        die();
    }

    public void fly(double vel, int angle, double x, double y) {
        this.x = x - size / 2;
        this.y = y - size / 2;
        velX = vel * Math.cos(Math.toRadians(angle));
        velY = vel * Math.sin(Math.toRadians(angle));
    }

    private void die() {
        if (lifeTimer > 0) {
            lifeTimer--;
            size *= (double) lifeTimer / age;
        }
    }

    private void gravity() {
        if (physic) {
            velY += 0.01;
        }
    }

    public void paint(Graphics2D g2d) {
        if (map != null) {
            if (shape.equals("Rectangle")) {
                g2d.setColor(color);
                g2d.fillRect((int) (map.x + x), (int) (map.y + y), (int) size, (int) size);
            } else if (shape.equals("Circle")) {
                g2d.setColor(color);
                g2d.fillOval((int) (map.x + x), (int) (map.y + y), (int) size, (int) size);
            }
        } else {
            if (shape.equals("Rectangle")) {
                g2d.setColor(color);
                g2d.fillRect((int) x, (int) y, (int) size, (int) size);
            } else if (shape.equals("Circle")) {
                g2d.setColor(color);
                g2d.fillOval((int) x, (int) y, (int) size, (int) size);
            }
        }
    }
}
