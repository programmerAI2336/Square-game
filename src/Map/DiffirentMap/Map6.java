package Map.DiffirentMap;

import Camera.Camera;
import Map.*;

public class Map6 extends Map {
    public Map6(Camera cam) {
        super(cam);
        width = 2000;
        length = 1200;
        obstacle = new Obstacle[6];
        obstacle[0] = new Obstacle(this, 300, 1100, 1400, 100, false);
        obstacle[1] = new Obstacle(this, 450, 1040, 200, 60, true);
        obstacle[2] = new Obstacle(this, 1425, 800, 200, 25, false);
        obstacle[3] = new Obstacle(this, 1125, 600, 200, 25, false);
        obstacle[4] = new Obstacle(this, 300, 1000, 150, 100, false);
        obstacle[5] = new Obstacle(this, 650, 1000, 1050, 100, false);
    }
}
