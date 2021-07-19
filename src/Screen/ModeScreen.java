package Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModeScreen implements MouseListener, MouseMotionListener {
    public Button back;
    public Button survival;
    public Button payload;
    private Button[] button;
    public boolean resetNextScreen;

    public ModeScreen() {
        try {
            back = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-2.png")), 100, 625, 150, 45);
            survival = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Survival-icon-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Survival-icon-2.png")), 433, 250, 200, 200);
            payload = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Payload-icon-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Payload-icon-2.png")), 733, 250, 200, 200);
            button = new Button[]{back, survival, payload};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        resetNextScreen = false;
        for (Button button : button) {
            button.clicked = false;
            button.touched = false;
        }
    }

    public void update() {
        for (Button button : button) {
            button.update();
        }
    }

    public void paint(Graphics2D g2d) {
        BufferedImage background;
        try {
            background = ImageIO.read(new File("Assets\\2D models\\Background.png"));
            g2d.drawImage(background, 0, 0, 1366, 748, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2d.setFont(new Font("Open Sans", Font.BOLD, 75));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Modes", 575, 150);
        for (Button button : button) {
            button.paint(g2d);
        }
        paintDescription(g2d);
    }

    private void paintDescription(Graphics2D g2d) {
        if (survival.touched || payload.touched) {
            Font font = new Font("Open Sans", Font.BOLD, 40);
            FontMetrics metrics = g2d.getFontMetrics(font);
            String text = "";
            if (survival.touched) {
                text = "Fight mainly with your guns ( i thinks:) )";
            } else {
                text = "More explosions, more fun";
            }
            // Determine the X coordinate for the text
            int x = 683 - metrics.stringWidth(text) / 2;
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
            int y = 550 - metrics.getHeight() / 2 + metrics.getAscent();
            // Set the font
            g2d.setFont(font);
            // Draw the String
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, x, y);
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
}
