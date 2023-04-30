import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("title");
        frame.setPreferredSize(new Dimension(700, 400));
//        frame.setResizable(false);
        frame.setSize(700, 400);
        JComponent canvas = new JComponent() {
            int calls = 0;
            @Override
            public void paint(Graphics g) {
                g.fillRect(calls*2, 10, 50, 50);
                ++calls;
            }
        };
        canvas.setDoubleBuffered(true);
//        Timer timer = new Timer(5, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                canvas.repaint();
//            }
//        });

        System.out.println(canvas.getBounds().width + " " + canvas.getBounds().height + " " + canvas.getBounds().x);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                System.out.println("typed");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("pressed");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                System.out.println("released");
            }
        });

        frame.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
//        timer.start();

        while (true) {
            canvas.repaint();
            TimeUnit.MILLISECONDS.sleep(5);
        }
    }
}