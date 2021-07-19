package StatsManager;

import Screen.SettingScreen.DataSaver;
import Screen.StatsScreen.GameStatsData;

import java.io.Serializable;

public class GameStats implements Serializable {
    public int cent = 1000000;
    public int highScore;
    public int kill;

    public int level = 1;
    public int currentLevelPoint;
    //required point to go to next level;

    public int requiredLevelPoint = level * 60 / 5 + 2 * level * 60 / 10 + 3 * level * 60 / 15 + 5 * level * 60 / 20 + 8 * level * 60 / 25;

    public int longestSurvivalTime;

    public GameStats() {
        GameStatsData.writeStats(this);
    }

    private void updateLevel() {
        if (currentLevelPoint >= requiredLevelPoint) {
            //reset current point.
            currentLevelPoint -= requiredLevelPoint;
            //update level.
            level++;

            //update required point to go to next level.
            int estimatedSecond = level * 60;
            requiredLevelPoint = estimatedSecond / 5 + 2 * estimatedSecond / 10 + 3 * estimatedSecond / 15 + 5 * estimatedSecond / 20 + 8 * estimatedSecond / 25;
        }
    }

    public void update() {
        updateLevel();
    }
}
