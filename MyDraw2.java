//list ver.


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MyDraw2 extends JFrame {

    private JPanel drawingPanel;
    private List<Shape> shapes; // 描画の履歴を保存するリスト
    private int shapeToDraw; // 1: 直線, 2: 四角形, 3: 楕円
    private Color drawColor;

    public MyDraw2() {
        setTitle("Drawing Program");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shapes = new ArrayList<>();

        // 描画領域用のパネル
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawShapes(g);
            }
        };
        drawingPanel.setBackground(Color.WHITE);

        // マウスイベントのリスナーを追加
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                shapes.add(new Shape(shapeToDraw, drawColor, e.getPoint(), e.getPoint()));
                drawingPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                shapes.get(shapes.size() - 1).setEnd(e.getPoint());
                drawingPanel.repaint();
            }
        });

        // ボタン用のパネル
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4));

        // ボタンの作成と配置
        JButton lineButton = createButton("Line", e -> shapeToDraw = 1);
        JButton rectButton = createButton("Rect", e -> shapeToDraw = 2);
        JButton ovalButton = createButton("Oval", e -> shapeToDraw = 3);
        JButton blackButton = createColorButton("Black", Color.BLACK);
        JButton redButton = createColorButton("Red", Color.RED);
        JButton greenButton = createColorButton("Green", Color.GREEN);
        JButton clearButton = createButton("Clear", e -> clearDrawing());
        JButton quitButton = createButton("Quit", e -> System.exit(0));

        buttonPanel.add(lineButton);
        buttonPanel.add(rectButton);
        buttonPanel.add(ovalButton);
        buttonPanel.add(blackButton);
        buttonPanel.add(redButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // レイアウトの設定
        setLayout(new BorderLayout());
        add(drawingPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    private JButton createColorButton(String text, Color color) {
        JButton button = createButton(text, e -> drawColor = color);
        button.setBackground(color);
        return button;
    }

    private void drawShapes(Graphics g) {
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    private void clearDrawing() {
        shapes.clear();
        drawingPanel.repaint();
    }

    private static class Shape {
        private int type; // 1: 直線, 2: 四角形, 3: 楕円
        private Color color;
        private Point start;
        private Point end;

        public Shape(int type, Color color, Point start, Point end) {
            this.type = type;
            this.color = color;
            this.start = start;
            this.end = end;
        }

        public void setEnd(Point end) {
            this.end = end;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            switch (type) {
                case 1: // 直線
                    g.drawLine(start.x, start.y, end.x, end.y);
                    break;
                case 2: // 四角形
                    int width = Math.abs(end.x - start.x);
                    int height = Math.abs(end.y - start.y);
                    int x = Math.min(start.x, end.x);
                    int y = Math.min(start.y, end.y);
                    g.drawRect(x, y, width, height);
                    break;
                case 3: // 楕円
                    width = Math.abs(end.x - start.x);
                    height = Math.abs(end.y - start.y);
                    x = Math.min(start.x, end.x);
                    y = Math.min(start.y, end.y);
                    g.drawOval(x, y, width, height);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyDraw2 drawingProgram = new MyDraw2();
            drawingProgram.setVisible(true);
        });
    }
}
