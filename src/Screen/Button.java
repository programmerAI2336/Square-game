package Screen;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class Button implements MouseMotionListener, MouseListener {
    public int x;
    public int y;
    public int width;
    public int length;
    private Rectangle mouse = new Rectangle();
    public boolean touched;
    public boolean clicked;
    public int status;
    public final BufferedImage image1;
    public final BufferedImage image2;

    public Button(BufferedImage image1, BufferedImage image2, int x, int y, int width, int length) {
        this.image1 = image1;
        this.image2 = image2;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    public Button(BufferedImage image1, BufferedImage image2) {
        this.image1 = image1;
        this.image2 = image2;
    }

    public void update() {
        touched = mouse.intersects(new Rectangle(x, y, width, length));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouse = new Rectangle(e.getX(), e.getY(), 1, 1);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse = new Rectangle(e.getX(), e.getY(), 1, 1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse = new Rectangle(e.getX(), e.getY(), 1, 1);
        if (mouse.intersects(new Rectangle(x, y, width, length))) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                clicked = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse = new Rectangle(e.getX(), e.getY(), 1, 1);
        if (e.getButton() == MouseEvent.BUTTON1) {
            clicked = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouse = new Rectangle(e.getX(), e.getY(), 1, 1);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void paint(Graphics2D g2d) {
        if (touched) {
            g2d.drawImage(image2, x, y, width, length, null);
        } else {
            g2d.drawImage(image1, x, y, width, length, null);
        }
    }

    public Rectangle getBound() {
        return new Rectangle(x, y, width, length);
    }
}
