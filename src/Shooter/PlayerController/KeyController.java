package Shooter.PlayerController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {
    private final Player player;
    private boolean aPressed, dPressed, wPressed;
    private boolean ePressed, qPressed;
    //Status = 1 means button is pressed. Status = 0 means button is released
    private int eStatus, qStatus, wStatus;

    private String[] weaponName = new String[0];
    private int weaponIndex;

    public KeyController(Player player) {
        this.player = player;
    }

    public void reset(String[] weaponName) {
        this.weaponName = weaponName;
        player.weaponInventory.weaponName = weaponName[0];
        weaponIndex = 0;
        aPressed = false;
        dPressed = false;
        wPressed = false;
        ePressed = false;
        qPressed = false;
        eStatus = 0;
        qStatus = 0;
        wStatus = 0;

    }

    private void move() {
        if (aPressed) {
            player.move(-0.04f);
        }
        if (dPressed) {
            player.move(0.04f);
        }
        if (wPressed) {
            if (wStatus == 0) {
                player.jump("Assets\\Sounds\\Jump.wav");
                wStatus = 1;
            }
        } else {
            wStatus = 0;
        }
    }

    private void switchWeapon() {
        // switch to next weapon
        if (ePressed) {
            if (eStatus == 0) {
                if (weaponIndex < weaponName.length - 1) {
                    weaponIndex++;
                } else {
                    weaponIndex = 0;
                }
                eStatus = 1;
            }
        } else {
            eStatus = 0;
        }

        // switch to previous weapon
        if (qPressed) {
            if (qStatus == 0) {
                if (weaponIndex > 0) {
                    weaponIndex--;
                } else {
                    weaponIndex = weaponName.length - 1;
                }
                qStatus = 1;
            }
        } else {
            qStatus = 0;
        }

        player.weaponInventory.weaponName = weaponName[weaponIndex];
    }

    public void handle() {
        if (player.health > 0) {
            move();
            switchWeapon();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (key == KeyEvent.VK_D) {
            dPressed = true;
        }
        if (key == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (key == KeyEvent.VK_E) {
            ePressed = true;
        }
        if (key == KeyEvent.VK_Q) {
            qPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (key == KeyEvent.VK_D) {
            dPressed = false;
        }
        if (key == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (key == KeyEvent.VK_E) {
            ePressed = false;
        }
        if (key == KeyEvent.VK_Q) {
            qPressed = false;
        }
    }
}
