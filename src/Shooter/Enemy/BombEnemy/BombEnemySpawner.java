package Shooter.Enemy.BombEnemy;

import Map.Map;
import Shooter.PlayerController.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BombEnemySpawner {
    public final ArrayList<BombEnemy> enemy = new ArrayList<>();
    private final Player player;
    public Map map;

    private int timer;
    private final int spawnTime;
    private static final Random random = new Random();
    private final String name;

    public BombEnemySpawner(Player player, Map map, int spawnTime, String name) {
        this.player = player;
        this.map = map;
        this.spawnTime = spawnTime;
        this.name = name;
    }

    public void reset(Map map) {
        this.map = map;
        enemy.clear();
        timer = 0;
    }

    public void paint(Graphics2D g2d) {
        for (int i = 0; i < enemy.size(); i++) {
            enemy.get(i).paint(g2d);
        }
    }

    public void spawnEnemy() {
        if (player.health > 0 && timer % spawnTime == 0) {
            int y = 0;
            int[] allX = new int[(int) (map.obstacle[0].width + 1)];
            for (int i = 0; i < allX.length; i++) {
                allX[i] = (int) (i + (map.width - map.obstacle[0].width) / 2);
            }
            int x = allX[random.nextInt(allX.length)];
            if (name.equals("Survival")) {
                enemy.add(new BombEnemy(map, player, x, y, 125, 125, 3, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher"}));
            } else if (name.equals("Payload")) {
                enemy.add(new BombEnemy(map, player, x, y,  125, 125, 3, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun"}));
            }
        }
    }

    public void update() {
        timer++;
        spawnEnemy();
        for (BombEnemy enemy : enemy) {
            enemy.update();
        }
    }
}
