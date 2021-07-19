package Map.DiffirentMap;

import Camera.Camera;
import Map.*;

public class Map3 extends Map {

    public Map3(Camera cam) {
        super(cam);
        width = 2000;
        length = 1200;
        obstacle = new Obstacle[4];
        obstacle[0] = new Obstacle(this, 400, 1000, 1200, 200, false);
        obstacle[1] = new Obstacle(this, 500, 800, 200, 25, false);
        obstacle[2] = new Obstacle(this, 500, 650, 200, 25, false);
        obstacle[3] = new Obstacle(this, 1300, 725, 200, 25, false);
    }
}
