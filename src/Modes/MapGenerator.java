package Modes;

import Camera.Camera;
import Map.*;
import Map.DiffirentMap.*;

import java.awt.*;
import java.util.Random;

public class MapGenerator {
    public final Map[] map;
    public int index;

    private static final Random random = new Random();

    public MapGenerator(Camera cam) {
        Map1 map1 = new Map1(cam);
        Map2 map2 = new Map2(cam);
        Map3 map3 = new Map3(cam);
        Map4 map4 = new Map4(cam);
        Map5 map5 = new Map5(cam);
        Map6 map6 = new Map6(cam);
        Map7 map7 = new Map7(cam);
        map = new Map[]{map1, map2, map3, map4, map5, map6, map7};
    }

    public void paint(Graphics2D g2d) {
        map[index].paint(g2d);
    }

    public void update() {
        map[index].update();
    }

    public Map getRandomMap() {
        index = random.nextInt(map.length);
        return map[index];
    }
}
