package Map;

import Camera.Camera;

import java.awt.*;

public class Map {
    public Camera cam;
    public Obstacle[] obstacle;
    //x, y only used for painting so don't use it in update function.
    public double x;
    public double y;
    public double width;
    public double length;

    public Map(Camera cam) {
        this.cam = cam;
    }

    public void update() {
        x = -cam.x;
        y = -cam.y;
        for (Obstacle obstacle : obstacle) {
            obstacle.update();
        }
    }

    public void paint(Graphics2D g2d) {
//        g2d.setColor(new Color(70, 70, 70));
//        g2d.fillRect((int) x, (int) y, (int) width, (int) length);
        for (Obstacle obstacle : obstacle) {
            obstacle.paint(g2d);
        }
    }
}
