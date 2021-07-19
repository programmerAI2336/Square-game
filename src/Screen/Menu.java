package Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu implements MouseMotionListener, MouseListener {
    public Button play;
    public Button shop;
    public Button stats;
    public Button customize;
    public Button about;
    public Button setting;
    private final Button[] button;
    public boolean resetNextScreen;

    {
        try {
            play = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Play-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Play-2.png")), 583, 400, 200, 60);
            shop = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Shop-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Shop-2.png")), 583, 485, 200, 60);
            stats = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Stats-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Stats-2.png")), 208, 575, 100, 120);
            customize = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Customize-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Customize-2.png")), 583, 570, 200, 60);
            about = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Instruction-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Instruction-2.png")), 50, 575, 100, 109);
            setting = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Setting-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Setting-2.png")), 1150, 575, 100, 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
        button = new Button[]{play, shop, stats, customize, about,setting};
    }

    public void reset() {
        resetNextScreen = false;
        for (Button button : button) {
            button.clicked = false;
            button.touched = false;
        }
    }

    public void paint(Graphics2D g2d) {
        try {
            BufferedImage background = ImageIO.read(new File("Assets\\2D models\\Background.png"));
            g2d.drawImage(background, 0, 0, 1366, 748, null);
            BufferedImage logo = ImageIO.read(new File("Assets\\2D models\\Logo.png"));
            g2d.drawImage(logo, 333, 25, 700, 100, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Button button : button) {
            button.paint(g2d);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (Button button : button) {
            button.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (Button button : button) {
            button.mouseMoved(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Button button : button) {
            button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Button button : button) {
            button.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (Button button : button) {
            button.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void update() {
        for (Button button : button) {
            button.update();
        }
    }
}
