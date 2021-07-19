package Item;

import Map.Map;
import Map.Obstacle;
import ParticleSystem.ParticleEmitter;
import Shooter.Actor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Item {
    public final Map map;
    public final Actor actor;
    public double x, y;
    public double velY;
    public static final double SIZE = 30;
    public boolean picked;
    public String name;
    private int nameOpacity = 255;
    public int timer;
    private BufferedImage image;
    private ParticleEmitter effectEmitter;

    public Item(Map map, Actor actor, String[] allItems) {
        this.map = map;
        this.actor = actor;
        setLocation();
        randomWeapon(allItems);
        initEmitter();
    }

    private void initEmitter() {
        effectEmitter = new ParticleEmitter(map);
        effectEmitter.setAmountOfParticle(10);
        effectEmitter.setAngle(360, 0);
        effectEmitter.setSizeBound(20, 10);
        effectEmitter.setAge(750, 500);
        effectEmitter.setVelocity(2, 0.5);
        effectEmitter.setParticleShape("Circle");
    }

    private void randomWeapon(String[] allItems) {
        Random random = new Random();
        int index = random.nextInt(allItems.length);
        name = allItems[index];

        try {
            switch (name) {
                case "Several cents" -> image = ImageIO.read(new File("Assets\\2D models\\Items\\Several-cents.png"));
                case "Cent" -> image = ImageIO.read(new File("Assets\\2D models\\Items\\Cent.png"));
                case "Health" -> image = ImageIO.read(new File("Assets\\2D models\\Items\\Health.png"));
                default -> image = ImageIO.read(new File("Assets\\2D models\\Items\\Weapon.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLocation() {
        x = actor.x + actor.size / 2 - SIZE / 2;
        y = actor.y + actor.size / 2 - SIZE / 2;
    }

    private void gravity() {
        if (velY <= 2) {
            velY += 0.005;
        }
    }

    private void wallCollision() {
        for (Obstacle obs : map.obstacle) {
            for (int y = (int) (this.y + SIZE); y <= this.y + SIZE + velY; y++) {
                if (y == obs.y) {
                    if (this.x > obs.x - SIZE && this.x < obs.x + obs.width) {
                        velY = 0;
                        this.y = y - SIZE;
                    }
                }
            }
        }
    }

    private void appear() {
        if (actor.health <= 0) {
            timer++;
        }
        for (Obstacle obs : map.obstacle) {
            if (y + SIZE == obs.y && obs.isLava) {
                timer = 3750;
                break;
            }
        }
    }

    public void spreadEffect(Color color) {
        effectEmitter.setParticleColor(color);
        effectEmitter.setPosition(x + SIZE / 2, y + SIZE / 2);
        effectEmitter.spread();
    }

    public void update() {
        if (actor.health > 0) {
            setLocation();
        } else {
            appear();
            gravity();
            wallCollision();
            y += velY;
            if (picked) {
                if (nameOpacity > 0) {
                    nameOpacity -= 1;
                }
            }
        }
        effectEmitter.update();
    }

    public Rectangle getBound() {
        return new Rectangle((int) x, (int) y, (int) SIZE, (int) SIZE);
    }

    private void paintName(Graphics2D g2d) {
        if (picked) {
            Font font = new Font("Open Sans", Font.BOLD, 15);
            FontMetrics metrics = g2d.getFontMetrics(font);
            String text = name;
            // Determine the X coordinate for the text
            int x = (int) (this.x + SIZE / 2 - metrics.stringWidth(text) / 2);
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
            int y = (int) (this.y + SIZE / 2 - metrics.getHeight() / 2 + metrics.getAscent());
            // Set the font
            g2d.setFont(font);
            // Draw the String
            g2d.setColor(new Color(255, 255, 255, nameOpacity));
            g2d.drawString(text, (int) map.x + x, (int) map.y + y);
        }
    }

    public void paint(Graphics2D g2d) {
        if (actor.health <= 0 && !picked && timer < 3750) {
            g2d.drawImage(image, (int) (map.x + x), (int) (map.y + y), (int) SIZE, (int) SIZE, null);
        }
        effectEmitter.paint(g2d);
        paintName(g2d);
    }
}
