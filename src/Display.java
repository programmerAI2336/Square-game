import javax.swing.*;

public class Display extends JFrame {
    public Display(){
        Gameloop game = new Gameloop();
        Thread thread = new Thread(game);
        thread.start();
        add(game);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args){
        new Display();
    }
}
