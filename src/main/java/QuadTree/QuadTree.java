package QuadTree;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class QuadTree {

    private final Rectangle boundary;
    private final int capacity;

    private QuadTree NW,NE,SW,SE;

    private Vector[] points;
    private int size;
    private boolean divided;

    public QuadTree(Rectangle boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new Vector[capacity];
        this.size = 0;
        this.divided = false;
    }

    public void insert(Vector v) {
        if (size < capacity) {
            if (boundary.contains(v)) {
                points[size++] = v;
                return;
            }
            return;
        }
        if (!this.divided) {
            this.subdivide();
        }

        this.NW.insert(v);
        this.NE.insert(v);
        this.SW.insert(v);
        this.SE.insert(v);
    }

    private void subdivide() {
        var nw = new Rectangle(boundary.x(), boundary.y(), boundary.w()/2, boundary.h()/2);
        var ne = new Rectangle(boundary.x() + (boundary.w()/2), boundary.y(), boundary.w()/2, boundary.h()/2);
        var sw = new Rectangle(boundary.x(), boundary.y() + (boundary.h())/2, boundary.w()/2, boundary.h()/2);
        var se = new Rectangle(boundary.x() + (boundary.w()/2), boundary.y() + (boundary.h()/2), boundary.w()/2, boundary.h()/2);
        this.NW = new QuadTree(nw,capacity);
        this.NE = new QuadTree(ne,capacity);
        this.SW = new QuadTree(sw,capacity);
        this.SE = new QuadTree(se,capacity);
        this.divided = true;
    }

    public List<Vector> query(Rectangle range) {
        List<Vector> found = new ArrayList<>();
        if (!this.boundary.intersects(range)) return new ArrayList<>();
        else {
            for (int i = 0; i< this.size; i++) {
                Vector p = this.points[i];
                if (range.contains(p)) {
                    found.add(p);
                }
            }
            if (this.divided) {
                found.addAll(NW.query(range));
                found.addAll(NE.query(range));
                found.addAll(SW.query(range));
                found.addAll(SE.query(range));
            }
        }
        return found;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        this.boundary.render(g);

        for (int i = 0; i< this.size; i++) {
            this.points[i].render(g);
        }

        if (this.divided) {
            this.NW.render(g);
            this.NE.render(g);
            this.SW.render(g);
            this.SE.render(g);
        }


    }

}
