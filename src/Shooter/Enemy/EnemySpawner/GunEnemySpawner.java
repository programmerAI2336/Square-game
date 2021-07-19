package Shooter.Enemy.EnemySpawner;

import Map.Map;
import Shooter.Enemy.GunEnemy.*;
import Shooter.Enemy.Enemy;
import Shooter.PlayerController.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GunEnemySpawner {
    public final ArrayList<Enemy> enemy = new ArrayList<>();
    private final Player player;
    public Map map;
    private static final Random random = new Random();

    private int timer;
    private final int spawnTime;
    private final String type;
    private final String game;

    public GunEnemySpawner(Player player, Map map, int spawnTime, String type, String game) {
        this.player = player;
        this.map = map;
        this.spawnTime = spawnTime;
        this.type = type;
        this.game = game;
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

    private void spawnEnemy() {
        if (player.health > 0 && timer % spawnTime == 0) {
            int y = 0;
            int[] allX = new int[(int) (map.obstacle[0].width + 1)];
            for (int i = 0; i < allX.length; i++) {
                allX[i] = (int) (i + map.obstacle[0].x);
            }
            int x = allX[random.nextInt(allX.length)];
            if (game.equals("Survival")) {
                switch (type) {
                    case "Normal enemy" -> enemy.add(new NormalEnemy(map, player, x, y, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher", "Cent", "Several cents", "Health"}));
                    case "Tank enemy" -> enemy.add(new TankEnemy(map, player, x, y, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher", "Cent", "Several cents", "Health"}));
                    case "Fly enemy" -> enemy.add(new FlyEnemy(map, player, x, y, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher", "Cent", "Several cents", "Health"}));
                    case "Sniper enemy" -> enemy.add(new SniperEnemy(map, player, x, y, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher", "Cent", "Several cents", "Health"}));
                    case "Drone enemy" -> enemy.add(new DroneEnemy(map, player, x, y, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher", "Cent", "Several cents", "Health"}));
                    case "Super drone enemy" -> enemy.add(new SuperDroneEnemy(map, player, x, y, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher", "Cent", "Several cents", "Health"}));
                    case "Grenade launcher enemy" -> enemy.add(new GrenadeLauncherEnemy(map, player, x, y, new String[]{"Rifle", "Shotgun", "Grenade", "C4", "Rocket launcher", "Cent", "Several cents", "Health"}));
                }
            }
            if (game.equals("Payload")) {
                switch (type) {
                    case "Normal enemy" -> enemy.add(new NormalEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun", "Cent", "Several cents", "Health"}));
                    case "Tank enemy" -> enemy.add(new TankEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun", "Cent", "Several cents", "Health"}));
                    case "Fly enemy" -> enemy.add(new FlyEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun", "Cent", "Several cents", "Health"}));
                    case "Sniper enemy" -> enemy.add(new SniperEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun", "Cent", "Several cents", "Health"}));
                    case "Drone enemy" -> enemy.add(new DroneEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun", "Cent", "Several cents", "Health"}));
                    case "Super drone enemy" -> enemy.add(new SuperDroneEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun", "Cent", "Several cents", "Health"}));
                    case "Grenade launcher enemy" -> enemy.add(new GrenadeLauncherEnemy(map, player, x, y, new String[]{"Gatling", "Sniper", "Grenade launcher", "Rocket launcher", "Missile launcher", "Railgun", "Cent", "Several cents", "Health"}));
                }
            }
        }
    }

    public void update() {
        timer++;
        spawnEnemy();
        for (Enemy enemy : enemy) {
            enemy.update();
        }
    }
}
