package Map.DiffirentMap;

import Camera.Camera;
import Map.*;

public class Map2 extends Map {
    public Map2(Camera cam) {
        super(cam);
        width = 2000;
        length = 1200;
        obstacle = new Obstacle[4];
        obstacle[0] = new Obstacle(this, 400, 1000, 1200, 200,false);
        obstacle[1] = new Obstacle(this, 850, 800, 300, 25,false);
        obstacle[2] = new Obstacle(this, 500, 700, 100, 25,false);
        obstacle[3] = new Obstacle(this, 1400, 600, 100, 25,false);
    }
}
