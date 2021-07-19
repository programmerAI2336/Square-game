package Screen.StatsScreen;

import StatsManager.GameStats;

import java.io.*;

public class GameStatsData {
    public static GameStats readStats() {
        GameStats gameStats = new GameStats();
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("Data\\Stats.txt"));
            gameStats = (GameStats) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameStats;
    }

    public static void writeStats(GameStats gameStats) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Data\\Stats.txt"));
            output.writeObject(gameStats);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
