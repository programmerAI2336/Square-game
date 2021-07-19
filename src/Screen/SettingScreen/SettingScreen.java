package Screen.SettingScreen;

import Audio.BackgroundMusic;
import Audio.Sound;
import Screen.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SettingScreen implements MouseListener, MouseMotionListener {
    private final BackgroundMusic backgroundMusic;

    private Screen.Button music;
    private int musicPressedCounter;

    private Screen.Button sound;
    private int soundPressedCounter;

    private final Data data;

    public Screen.Button back;
    private Screen.Button[] button;

    public SettingScreen(BackgroundMusic backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        data = DataSaver.readData("Data\\Setting.txt");
        if (data.musicOn) {
            this.backgroundMusic.counter = 1;
            musicPressedCounter = 0;
        } else {
            this.backgroundMusic.counter = 0;
            musicPressedCounter = 1;
        }
        if (data.soundOn) {
            soundPressedCounter = 0;
        } else {
            soundPressedCounter = 1;
        }
        Sound.enable = data.soundOn;

        try {
            back = new Screen.Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Back-2.png")), 100, 625, 150, 45);
            sound = new Screen.Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Sound-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Sound-2.png")), 458, 250, 200, 60);
            music = new Screen.Button(ImageIO.read(new File("Assets\\2D models\\Buttons\\Music-1.png")),
                    ImageIO.read(new File("Assets\\2D models\\Buttons\\Music-2.png")), 458, 370, 200, 60);
            button = new Screen.Button[]{back, sound, music};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        for (Screen.Button button : button) {
            button.clicked = false;
            button.touched = false;
        }
    }

    private void changeMusic() {
        if (music.clicked) {
            if (music.status == 0) {
                musicPressedCounter++;
                data.musicOn = musicPressedCounter % 2 == 0;
                if (data.musicOn) {
                    backgroundMusic.counter = 1;
                } else {
                    backgroundMusic.counter = 0;
                    backgroundMusic.stop();
                }
                DataSaver.saveData(data, "Data\\Setting.txt");
                music.status = 1;
            }
        } else {
            music.status = 0;
        }
    }

    private void changeSound() {
        if (sound.clicked) {
            if (sound.status == 0) {
                soundPressedCounter++;
                data.soundOn = soundPressedCounter % 2 == 0;
                Sound.enable = data.soundOn;
                DataSaver.saveData(data, "Data\\Setting.txt");
                sound.status = 1;
            }
        } else {
            sound.status = 0;
        }
    }

    public void update() {
        for (Screen.Button button : button) {
            button.update();
        }
        changeMusic();
        changeSound();
    }

    public void paint(Graphics2D g2d) {
        BufferedImage background;
        try {
            background = ImageIO.read(new File("Assets\\2D models\\Background.png"));
            g2d.drawImage(background, 0, 0, 1366, 748, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Screen.Button button : button) {
            button.paint(g2d);
        }
        paintStatus(g2d);
    }

    private void paintStatus(Graphics2D g2d) {
        if (data.musicOn) {
            paintText(g2d, "On", 733, 400);
        } else {
            paintText(g2d, "Off", 733, 400);
        }

        if (data.soundOn) {
            paintText(g2d, "On", 733, 280);
        } else {
            paintText(g2d, "Off", 733, 280);
        }
    }

    private void paintText(Graphics2D g2d, String text, int centerX, int centerY) {
        Font font = new Font("Open Sans", Font.BOLD, 60);
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
        for (Screen.Button button : button) {
            button.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Screen.Button button : button) {
            button.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (Screen.Button button : button) {
            button.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (Screen.Button button : button) {
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