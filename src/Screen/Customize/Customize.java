package Screen.Customize;

import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Screen.Button;

public class Customize implements MouseMotionListener, MouseListener {
    public final Data data;
    private final ColorSkin[] colorSkin;
    public Button back;
    private Button[] button;

    public Customize(GameStats gameStats) {
        data = DataSaver.readData("Data\\Customize\\Customize.txt");

        colorSkin = new ColorSkin[6];
        try {
            colorSkin[0] = new ColorSkin(gameStats, ImageIO.read(new File("Assets\\2D models\\Customize\\Cyan-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Customize\\Cyan-2.png")), 210, 300, Color.CYAN, 0);
            colorSkin[1] = new ColorSkin(gameStats, ImageIO.read(new File("Assets\\2D models\\Customize\\Red-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Customize\\Red-2.png")), 375, 300, Color.RED, 100);
            colorSkin[2] = new ColorSkin(gameStats, ImageIO.read(new File("Assets\\2D models\\Customize\\Orange-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Customize\\Orange-2.png")), 540, 300, Color.ORANGE, 200);
            colorSkin[3] = new ColorSkin(gameStats, ImageIO.read(new File("Assets\\2D models\\Customize\\Yellow-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Customize\\Yellow-2.png")), 705, 300, Color.YELLOW, 500);
            colorSkin[4] = new ColorSkin(gameStats, ImageIO.read(new File("Assets\\2D models\\Customize\\Pink-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Customize\\Pink-2.png")), 870, 300, new Color(255, 192, 203), 1000);
            colorSkin[5] = new ColorSkin(gameStats, ImageIO.read(new File("Assets\\2D models\\Customize\\Green-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Customize\\Green-2.png")), 1035, 300, new Color(127, 255, 0), 2000);

            data.color = colorSkin[data.colorIndex].color;
            colorSkin[data.colorIndex].chosen = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            back = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-2.png")), 100, 625, 150, 45);
            button = new Button[]{back};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        for (Button button : button) {
            button.clicked = false;
            button.touched = false;
        }
    }

    public void paint(Graphics2D g2d) {
        try {
            BufferedImage background = ImageIO.read(new File("Assets\\2D models\\Background.png"));
            g2d.drawImage(background, 0, 0, 1366, 748, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        g2d.setFont(new Font("Open Sans", Font.BOLD, 80));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Color", 575, 200);

        for (ColorSkin skin : colorSkin) {
            skin.paint(g2d);
        }
        for (Button button : button) {
            button.paint(g2d);
        }
    }

    private void chooseColor() {
        for (int i = 0; i < colorSkin.length; i++) {
            if (colorSkin[i].button.clicked) {
                if (colorSkin[i].button.status == 0) {
                    if (colorSkin[i].killRequirement <= colorSkin[i].gameStats.kill) {
                        colorSkin[i].chosen = true;
                        data.colorIndex = i;
                        data.color = colorSkin[i].color;
                        DataSaver.saveData(data, "Data\\Customize\\Customize.txt");
                        for (int j = 0; j < i; j++) {
                            colorSkin[j].chosen = false;
                        }
                        for (int j = i + 1; j < colorSkin.length; j++) {
                            colorSkin[j].chosen = false;
                        }
                        colorSkin[i].button.status = 1;
                    }
                }
            } else {
                colorSkin[i].button.status = 0;
            }
        }
    }

    public void update() {
        for (ColorSkin skin : colorSkin) {
            skin.update();
        }
        for (Button button : button) {
            button.update();
        }
        chooseColor();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (ColorSkin colorSkin : colorSkin) {
            colorSkin.mousePressed(e);
        }
        for (Button button : button) {
            button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (ColorSkin colorSkin : colorSkin) {
            colorSkin.mouseReleased(e);
        }
        for (Button button : button) {
            button.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (ColorSkin colorSkin : colorSkin) {
            colorSkin.mouseEntered(e);
        }
        for (Button button : button) {
            button.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (ColorSkin skin : colorSkin) {
            skin.mouseDragged(e);
        }
        for (Button button : button) {
            button.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (ColorSkin skin : colorSkin) {
            skin.mouseMoved(e);
        }
        for (Button button : button) {
            button.mouseMoved(e);
        }
    }
}
