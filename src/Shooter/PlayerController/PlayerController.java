package Shooter.PlayerController;

import Map.Map;

import java.awt.*;
import java.awt.event.*;

public class PlayerController implements KeyListener, MouseMotionListener, MouseListener {
    public final Player player;
    private final KeyController keyController;
    private final MouseController mouseController;

    public PlayerController(Player player) {
        this.player = player;
        keyController = new KeyController(this.player);
        mouseController = new MouseController(this.player);
    }

    public void reset(Map map, String[] weaponName, Color color) {
        player.reset(map, color);
        player.weaponInventory.reset(map);
        keyController.reset(weaponName);
        mouseController.reset();
    }

    public void paint(Graphics2D g2d) {
        player.paint(g2d);
    }

    public void update() {
        keyController.handle();
        mouseController.handle();
        player.update();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyController.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyController.keyReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseController.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseController.mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseController.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseController.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseController.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
