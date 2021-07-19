package ParticleSystem;

import Map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ParticleEmitter {
    public Map map;
    private double x, y;
    public final ArrayList<Particle> particle = new ArrayList<>();
    private int amountOfParticle;
    private int highLife, lowLife;
    public double maxSize;
    private double minSize;
    private double maxVel, minVel;
    private Color particleColor;
    private String particleShape;
    private int minAngle;
    private int maxAngle;
    private boolean physic;

    public ParticleEmitter(Map map) {
        this.map = map;
    }

    public ParticleEmitter() {

    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSizeBound(double maxSize, double minSize) {
        this.maxSize = maxSize;
        this.minSize = minSize;
    }

    public void setAmountOfParticle(int amountOfParticle) {
        this.amountOfParticle = amountOfParticle;
    }

    public void setParticleShape(String shape) {
        this.particleShape = shape;
    }

    public void setAge(int highLife, int lowLife) {
        this.highLife = highLife;
        this.lowLife = lowLife;
    }

    public void setPhysic(boolean b) {
        this.physic = b;
    }

    public void setAngle(int max, int min) {
        this.minAngle = min;
        this.maxAngle = max;
    }

    public void setParticleColor(Color color) {
        this.particleColor = color;
    }

    public void setVelocity(double max, double min) {
        this.maxVel = max;
        this.minVel = min;
    }

    private final Random random = new Random();

    private int getRandomAngle() {
        int[] allAngle = new int[maxAngle - minAngle + 1];
        for (int i = 0; i < allAngle.length; i++) {
            allAngle[i] = i + minAngle;
        }
        int index = random.nextInt(allAngle.length);
        return allAngle[index];
    }

    private double getRandomSize() {
        double[] allSize = new double[(int) (10 * (maxSize - minSize) + 1)];
        for (int i = 0; i < allSize.length; i++) {
            allSize[i] = minSize + (double) i / 10;
        }
        int index = random.nextInt(allSize.length);
        return allSize[index];
    }

    private int getRandomAge() {
        int[] allAge = new int[(highLife - lowLife) + 1];
        for (int i = 0; i < allAge.length; i++) {
            allAge[i] = lowLife + i;
        }
        int index = random.nextInt(allAge.length);
        return allAge[index];
    }

    private double getRandomVel() {
        double[] allVel = new double[(int) (10 * (maxVel - minVel) + 1)];
        for (int i = 0; i < allVel.length; i++) {
            allVel[i] = minVel + (double) i / 10;
        }
        int index = random.nextInt(allVel.length);
        return allVel[index];
    }

    public void spread() {
        for (int i = 0; i < amountOfParticle; i++) {
            particle.add(new Particle(map, getRandomSize(), particleColor, getRandomAge(), particleShape, physic));
            particle.get(particle.size() - 1).fly(getRandomVel(), getRandomAngle(), x, y);
        }
    }

    private int timer;

    public void spreadRepeatly(boolean repeat, int rate) {
        if (repeat) {
            timer++;
            if (timer % rate == 0) {
                spread();
            }
        } else {
            timer = 0;
        }
    }

    private void removeParticle() {
        particle.removeIf(particle -> particle.lifeTimer == 0);
    }

    public void update() {
        for (Particle particle : particle) {
            particle.update();
        }
        removeParticle();
    }

    public void paint(Graphics2D g2d) {
        if (!particle.isEmpty()) {
            for (int i = 0; i < particle.size(); i++) {
                particle.get(i).paint(g2d);
            }
        }
    }
}
