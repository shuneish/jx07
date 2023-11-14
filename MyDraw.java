import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyDraw extends JFrame {

    private static int[] strt;
    private static int[] end;
    private static int cnt = 0;
    // aaa

    public MyDraw() {

        strt = new int[2];
        end = new int[2];

        setTitle("MyDraw");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new MyPanel();
        panel.addMouseListener(new CustomMouseListener());

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyDraw());
    }

    private static class CustomMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e1) {
            cnt++;

            switch (cnt % 2) {
                case 1:
                    strt[0] = e1.getX(); // Fix: use getX() for x-coordinate
                    strt[1] = e1.getY();
                    break;

                case 0:
                    end[0] = e1.getX();
                    end[1] = e1.getY();
                    break;
            }

            System.out.println("Mouse Clicked at: (" + e1.getX() + ", " + e1.getY() + ")");
            System.out.println(cnt);
        }

        @Override
        public void mousePressed(MouseEvent e2) {
            // System.out.println("Mouse Pressed at: (" + e2.getX() + ", " + e2.getY() + ")");
        }

        @Override
        public void mouseReleased(MouseEvent e3) {
            // System.out.println("Mouse Released at: (" + e3.getX() + ", " + e3.getY() + ")");
        }

        @Override
        public void mouseEntered(MouseEvent e4) {
            // System.out.println("Mouse Entered at: (" + e.getX() + ", " + e.getY() + ")");
        }

        @Override
        public void mouseExited(MouseEvent e5) {
            // System.out.println("Mouse Exited at: (" + e.getX() + ", " + e.getY() + ")");
        }
    }

    private static class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // ここに描画コードを追加します
            drawLine(g);
            repaint(); 
            // Fix: trigger repaint after the second click
        }

        private void drawLine(Graphics g) {
            g.setColor(Color.BLUE);
            g.drawLine(strt[0], strt[1], end[0], end[1]);
        }
    }
}
