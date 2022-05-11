package QuadTree;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Random;
import java.util.concurrent.*;


import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Visual {
    public static final int WIDTH = 700, HEIGHT = 700;
    public static final Dimension screen = new Dimension(WIDTH,HEIGHT);
    private final JFrame frame = new JFrame();
    private final JPanel scene = new Scene();
    private static final QuadTree qt = new QuadTree(new Rectangle(0,0,WIDTH,HEIGHT),5);
    {
        frame.setSize(screen);
        scene.setPreferredSize(screen);
        frame.add(scene);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private final MouseInput mouse = new MouseInput();
    private static class MouseInput extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            qt.insert(new Vector(e.getX(),e.getY(),0));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            qt.insert(new Vector(e.getX(),e.getY(),0));
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            qt.insert(new Vector(e.getX(),e.getY(),0));
        }

    }
    {
        frame.addMouseListener(mouse);
        frame.addMouseWheelListener(mouse);
        frame.addMouseMotionListener(mouse);
    }


    private static Rectangle clip = new Rectangle(400,400,100,200);
    public void start () {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(scene::repaint, 200,17, TimeUnit.MILLISECONDS);
    }


    public static class Scene extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D)graphics;
            g.fill3DRect(0,0,Visual.WIDTH,Visual.HEIGHT,false);
            qt.render(g);
            g.setColor(Color.GREEN);
            clip.render(g);

            var q = qt.query(clip);
            for (Vector v : q){
                v.render(g);
            }
        }
    }


}
