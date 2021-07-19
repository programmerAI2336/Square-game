package Map.DiffirentMap;

import Camera.Camera;
import Map.*;

public class Map5 extends Map {
    public Map5(Camera cam) {
        super(cam);
        width = 2000;
        length = 1200;
        obstacle = new Obstacle[4];
        obstacle[0] = new Obstacle(this, 300, 1000, 1400, 200, false);
        obstacle[1] = new Obstacle(this, 850, 750, 300, 25, false);
        obstacle[2] = new Obstacle(this, 400, 950, 1200, 50, false);
        obstacle[3] = new Obstacle(this, 850, 550, 300, 25, false);
    }
}
