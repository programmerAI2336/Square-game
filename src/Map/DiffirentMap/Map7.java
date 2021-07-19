package Map.DiffirentMap;

import Camera.Camera;
import Map.*;

public class Map7 extends Map {
    public Map7(Camera cam) {
        super(cam);
        width = 2000;
        length = 1200;
        obstacle = new Obstacle[7];
        obstacle[0] = new Obstacle(this, 175, 1000, 750, 200, false);
        obstacle[1] = new Obstacle(this, 1075, 1000, 300, 200, false);
        obstacle[2] = new Obstacle(this, 1525, 1000, 300, 200, false);

        obstacle[3] = new Obstacle(this, 925, 1050, 150, 150, true);
        obstacle[4] = new Obstacle(this, 1375, 1050, 150, 150, true);

        obstacle[5] = new Obstacle(this, 850, 800, 300, 25, false);
        obstacle[6] = new Obstacle(this, 1400, 700, 200, 25, false);
    }
}
