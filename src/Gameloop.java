import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gameloop extends JPanel implements Runnable, KeyListener, MouseMotionListener, MouseListener {

    private long nextStatTime;
    private int fps, realFPS;


    private final ScreenManager screenManager = new ScreenManager();

    public Gameloop() {
        addKeyListener(this);
        setFocusable(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        //Sound.play("Assets\\Background music\\Music-1.wav", true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        setBackground(Color.DARK_GRAY);
        Graphics2D g2d = (Graphics2D) g;
        screenManager.paint(g2d);
        paintFPS(g2d);
    }

    private void paintFPS(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Open Sans", Font.BOLD, 20));
        g2d.drawString("FPS: " + realFPS, 1250, 730);
    }

    private void update() {
        screenManager.update();
        fps++;
    }

    private void render() {
        repaint();
    }

    private void printStats() {
        if (System.currentTimeMillis() > nextStatTime) {
            //System.out.println("FPS: " + fps);
            realFPS = fps;
            fps = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    @Override
    public void run() {
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while (true) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSecond = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeInSecond;
            lastUpdate = currentTime;
            double updateRate = 1d / 250d;

            while (accumulator > updateRate) {
                //Update here
                update();
                accumulator -= updateRate;
            }
            //Render here
            render();
            //Print fps here
            printStats();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        screenManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        screenManager.keyReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        screenManager.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        screenManager.mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        screenManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        screenManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        screenManager.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        screenManager.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
