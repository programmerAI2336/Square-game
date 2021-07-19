package Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AboutScreen implements MouseListener, MouseMotionListener {
    public Button back;
    private Button[] button;

    public AboutScreen() {
        try {
            back = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-2.png")), 100, 625, 150, 45);
            button = new Button[]{back};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2d) {
        try {
            BufferedImage image = ImageIO.read(new File("Assets\\2D models\\Background.png"));
            g2d.drawImage(image, 0, 0, 1366, 748, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        paintText(g2d,"''Hi, i'm HIT. Have fun when playing my game.''",683,250);
        paintText(g2d," Programmer: HIT                                                 ",683,310);
        paintText(g2d,"Art designer: HIT                                                  ",683,370);
        paintText(g2d,"Producer: HIT                                                       ",683,430);
        for (Button button : button) {
            button.paint(g2d);
        }
    }

    private void paintText(Graphics2D g2d, String text, int centerX, int centerY) {
        Font font = new Font("Open Sans", Font.PLAIN, 40);
        FontMetrics metrics = g2d.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = centerX - metrics.stringWidth(text) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = centerY - metrics.getHeight() / 2 + metrics.getAscent();
        // Set the font
        g2d.setFont(font);
        // Draw the String
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
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

    public void reset() {
        for (Button button : button) {
            button.touched = false;
            button.clicked = false;
        }
    }

    public void update() {
        for (Button button : button) {
            button.update();
        }
    }
}
