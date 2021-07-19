package Screen.Shop;

import Screen.Button;
import Screen.Shop.Window.Window1;
import Screen.Shop.Window.Window2;
import Screen.Shop.Window.Window3;
import StatsManager.GameStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shop implements MouseMotionListener, MouseListener {
    private final Window1 window1;
    private final Window2 window2;
    private final Window3 window3;

    public Button back;
    private Button goNext, goPrevious;
    private int index;

    private final Button[] button;

    public Shop(GameStats gameStats, ShopManager shopManager) {
        window1 = new Window1(gameStats, shopManager.playerUpgradeManager);
        window2 = new Window2(gameStats, shopManager.weaponUpgradeManager);
        window3 = new Window3(gameStats, shopManager.weaponUpgradeManager);
        try {
            back = new Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-1.png")), ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-2.png")), 100, 625, 150, 45);
            goNext = new Button(ImageIO.read(new File("Assets\\2D models\\Shop\\Changer\\Go-next-1.png")), ImageIO.read(new File("Assets\\2D models\\Shop\\Changer\\Go-next-2.png")), 1206, 354, 60, 60);
            goPrevious = new Button(ImageIO.read(new File("Assets\\2D models\\Shop\\Changer\\Go-previous-1.png")), ImageIO.read(new File("Assets\\2D models\\Shop\\Changer\\Go-previous-2.png")), 100, 354, 60, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        button = new Button[]{back, goPrevious, goNext};
    }

    public void reset() {
        index = 0;
        for (Button button : button) {
            button.clicked = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (index == 0) {
            window1.mousePressed(e);
        } else if (index == 1) {
            window2.mousePressed(e);
        } else {
            window3.mousePressed(e);
        }
        for (Button button : button) {
            button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (index == 0) {
            window1.mouseReleased(e);
        } else if (index == 1) {
            window2.mouseReleased(e);
        } else {
            window3.mouseReleased(e);
        }
        for (Button button : button) {
            button.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (index == 0) {
            window1.mouseEntered(e);
        } else if (index == 1) {
            window2.mouseEntered(e);
        } else {
            window3.mouseEntered(e);
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
        if (index == 0) {
            window1.mouseDragged(e);
        } else if (index == 1) {
            window2.mouseDragged(e);
        } else {
            window3.mouseDragged(e);
        }
        for (Button button : button) {
            button.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (index == 0) {
            window1.mouseMoved(e);
        } else if (index == 1) {
            window2.mouseMoved(e);
        } else {
            window3.mouseMoved(e);
        }
        for (Button button : button) {
            button.mouseMoved(e);
        }
    }

    public void update() {
        changeWindow();
        for (Button button : button) {
            button.update();
        }
        if (index == 0) {
            window1.update();
        } else if (index == 1) {
            window2.update();
        } else {
            window3.update();
        }
//        System.out.println(index);
    }

    private void changeWindow() {
        if (goNext.clicked) {
            if (goNext.status == 0) {
                if (index < 2) {
                    index++;
                } else {
                    index = 0;
                }
                goNext.status = 1;
            }
        } else {
            goNext.status = 0;
        }

        if (goPrevious.clicked) {
            if (goPrevious.status == 0) {
                if (index > 0) {
                    index--;
                } else {
                    index = 2;
                }
                goPrevious.status = 1;
            }
        } else {
            goPrevious.status = 0;
        }
    }

    public void paint(Graphics2D g2d) {
        try {
            BufferedImage image = ImageIO.read(new File("Assets\\2D models\\Background.png"));
            g2d.drawImage(image, 0, 0, 1366, 748, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (index == 0) {
            window1.paint(g2d);
        } else if (index == 1) {
            window2.paint(g2d);
        } else {
            window3.paint(g2d);
        }
        for (Button button : button) {
            button.paint(g2d);
        }
    }
}
