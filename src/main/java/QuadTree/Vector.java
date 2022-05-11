package QuadTree;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public record Vector(double x, double y, double z) {

    public void render(Graphics2D g){
        g.fill(new Ellipse2D.Double(x - 2, y-2, 4,4));
    }
}
