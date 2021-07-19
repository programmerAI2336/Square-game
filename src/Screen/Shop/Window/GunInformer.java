package Screen.Shop.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GunInformer {
    private final int x, y;

    private static final int ICON_SIZE = 100;
    public static BufferedImage DEFAULT_IMAGE;
    public static final int DEFAULT_FIRE_RATE = 0;
    public static final int DEFAULT_DEFLECTION = 0;
    public static final int DEFAULT_MAX_MAG = 0;
    public static final int DEFAULT_DAMAGE = 0;

    private BufferedImage icon;
    private String name;

    private int fireRate;
    private double deflection;
    private int maxMag;
    private int damage;

    private int fireRateValue;
    private int deflectionValue;
    private int maxMagValue;
    private int damageValue;

    public GunInformer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static {
        try {
            DEFAULT_IMAGE = ImageIO.read(new File("Assets\\2D models\\Shop\\Nothing.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void informGun(BufferedImage icon, String name, int fireRate, double deflection, int maxMag, int damage) {
        this.icon = icon;
        this.name = name;
        this.fireRate = fireRate;
        this.maxMag = maxMag;
        this.damage = damage;
        this.deflection = deflection;
    }

    public void resetValue() {
        fireRateValue = 0;
        maxMagValue = 0;
        deflectionValue = 0;
        damageValue = 0;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(icon, x, y, ICON_SIZE, ICON_SIZE, null);
        paintStats(g2d);
    }

    private void paintStats(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 16));

        g2d.setColor(Color.WHITE);
        if (name != null) {
            g2d.drawString(name, x, y - 5);
        }
        g2d.fillRect(x + ICON_SIZE, y, fireRateValue, 16);
        g2d.drawString("Fire rate", x + ICON_SIZE + fireRateValue + 2, y + 16);

        g2d.fillRect(x + ICON_SIZE, y + 28, damageValue, 16);
        g2d.drawString("Damage", x + ICON_SIZE + damageValue + 2, y + 44);

        g2d.fillRect(x + ICON_SIZE, y + 56, deflectionValue, 16);
        g2d.drawString("Deflection", x + ICON_SIZE + deflectionValue + 2, y + 72);

        g2d.fillRect(x + ICON_SIZE, y + 84, maxMagValue, 16);
        g2d.drawString("Mag", x + ICON_SIZE + maxMagValue + 2, y + 100);
    }

    public void update() {
        if (fireRateValue < fireRate * 5) {
            fireRateValue += 2;
        }

        if (deflectionValue < deflection * 1000) {
            deflectionValue += 2;
        }

        if (damageValue < damage) {
            damageValue += 2;
        }

        if (maxMagValue < maxMag) {
            maxMagValue += 2;
        }
    }
}
