package Map;

import ParticleSystem.ParticleEmitter;

import java.awt.*;
import java.util.Random;

public class Obstacle {
    public final double x;
    public final double y;
    public final double width;
    public final double length;
    private final Map map;
    public boolean isLava;
    public ParticleEmitter bubbleEmitter;

    public Obstacle(Map map, double x, double y, double width, double length, boolean isLava) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        this.isLava = isLava;
        bubbleEmitter = new ParticleEmitter(map);
        bubbleEmitter.setAmountOfParticle(1);
        bubbleEmitter.setAngle(280, 260);
        bubbleEmitter.setAge(1750, 1000);
        bubbleEmitter.setVelocity(0.3, 0.1);
        bubbleEmitter.setSizeBound(25, 15);
        bubbleEmitter.setParticleShape("Circle");
    }

    public Rectangle getBound() {
        return new Rectangle((int) x, (int) y, (int) width, (int) length);
    }

    public void paint(Graphics2D g2d) {
        if (!isLava) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(new Color(0xFF0000));
            bubbleEmitter.paint(g2d);
        }
        g2d.fillRect((int) (map.x + x), (int) (map.y + y), (int) width, (int) length);
    }

    private static final Random random = new Random();

    public void update() {
        bubbleEmitter.setPosition(x + random.nextInt((int) width), y);
        bubbleEmitter.update();
        bubbleEmitter.spreadRepeatly(true, 10);
    }
}
