package Map.DiffirentMap;

import Camera.Camera;
import Map.*;

public class Map4 extends Map {
    public Map4(Camera cam) {
        super(cam);
        width = 2000;
        length = 1200;
        obstacle = new Obstacle[6];
        obstacle[0] = new Obstacle(this, 250, 1100, 1500, 100, false);
        obstacle[1] = new Obstacle(this, 250, 1000, 600, 100, false);
        obstacle[2] = new Obstacle(this, 1150, 1000, 600, 100, false);
        obstacle[3] = new Obstacle(this, 850, 800, 300, 25, false);
        obstacle[4] = new Obstacle(this, 850, 1040, 300, 60, true);
        obstacle[5] = new Obstacle(this, 1375, 650, 150, 25, false);
    }
}
