import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SimpleMusicAdjust {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = "Assets\\Background music\\Music-1.wav";
        String in = "";
        while (true) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            System.out.print("Music: ");
            in = scanner.nextLine();
        }
    }
}
