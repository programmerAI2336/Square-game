package Map.DiffirentMap;

import Camera.Camera;
import Map.*;

public class Map1 extends Map {

    public Map1(Camera cam) {
        super(cam);
        width = 2000;
        length = 1200;
        obstacle = new Obstacle[2];
        obstacle[0] = new Obstacle(this, 400, 1000, 1200, 200,false);
        obstacle[1] = new Obstacle(this,850,800,300,25,false);
    }
}
