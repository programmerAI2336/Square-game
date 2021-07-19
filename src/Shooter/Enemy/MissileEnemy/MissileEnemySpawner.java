package Shooter.Enemy.MissileEnemy;

import Map.Map;
import Shooter.PlayerController.Player;

import java.awt.*;
import java.util.ArrayList;

public class MissileEnemySpawner {
    private double x, y;
    public final ArrayList<MissileEnemy> enemy = new ArrayList<>();
    private final Player player;
    public Map map;

    private int timer;
    private final int spawnTime;

    public MissileEnemySpawner(Player player, Map map, int spawnTime) {
        this.player = player;
        this.map = map;
        this.spawnTime = spawnTime;
    }

    public void reset(Map map) {
        this.map = map;
        enemy.clear();
        timer = 0;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g2d) {
        for (int i = 0; i < enemy.size(); i++) {
            enemy.get(i).paint(g2d);
        }
    }

    public void spawnEnemy() {
        if (player.health > 0 && timer % spawnTime == 0) {
            enemy.add(new MissileEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun"}));
        }
    }

    public void update() {
        timer++;
        for (MissileEnemy enemy : enemy) {
            enemy.update();
        }
    }
}
