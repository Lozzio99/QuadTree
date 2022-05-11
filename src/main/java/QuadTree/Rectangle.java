package QuadTree;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public record Rectangle(double x, double y, double w, double h) {
    public boolean contains(Vector v) {
         return v.x() >= x() &&
                 v.x() < (x() + w()) &&
                 v.y() >= y() &&
                 v.y() < (y() + h());
    }

    public double left() {
        return x();
    }
    public double top(){
        return y();
    }
    public double right(){
        return x() + w();
    }
    public double bottom(){
        return y() + h();
    }

    public boolean intersects(Rectangle r) {
        double xMin = Math.max(x, r.x);
        double xMax = Math.min(right(), r.right());
        if (xMax > xMin) {
            double yMin = Math.max(y, r.y);
            double yMax = Math.min(bottom(), r.bottom());
            return yMax > yMin;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(
                new Rectangle(0,0,700,700).
                        intersects(new Rectangle(650,650,100,200))
        );
    }
    public void render(Graphics2D g) {
        g.draw(new Rectangle2D.Double(x,y,w,h));
    }
}
