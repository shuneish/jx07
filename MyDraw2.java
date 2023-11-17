import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MyDraw2 extends JFrame {

    private DrawPanel drawPanel;

    public MyDraw2() {
        setTitle("MyDraw2");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4));

        JButton lineButton = createButton("Line", null, "Line");
        JButton rectButton = createButton("Rect", null, "Rect");
        JButton ovalButton = createButton("Oval", null, "Oval");
        JButton blackButton = createColorButton("Black", Color.BLACK);
        JButton redButton = createColorButton("Red", Color.RED);
        JButton greenButton = createColorButton("Green", Color.GREEN);
        JButton clearButton = createButton("Clear", null, "Clear");
        JButton quitButton = createButton("Quit", null, "Quit");

        buttonPanel.add(lineButton);
        buttonPanel.add(rectButton);
        buttonPanel.add(ovalButton);
        buttonPanel.add(blackButton);
        buttonPanel.add(redButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        setLayout(new BorderLayout());
        add(drawPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color color, String command) {
        JButton button = new JButton(text);
        if (color != null) {
            button.setForeground(color);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (command != null) {
                    processCommand(command);
                } else {
                    drawPanel.setCurrentColor(color);
                    drawPanel.setCurrentShape(text);
                }
            }
        });
        return button;
    }

    private JButton createColorButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrentColor(color);
            }
        });
        return button;
    }

    private void processCommand(String command) {
        switch (command) {
            case "Clear":
                drawPanel.clear();
                break;
            case "Quit":
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyDraw2().setVisible(true);
            }
        });
    }
}

class DrawPanel extends JPanel {

    private List<ShapeData> shapes;
    private Color currentColor;
    private String currentShape;
    private int startX, startY, endX, endY;

    public DrawPanel() {
        shapes = new ArrayList<>();
        currentColor = Color.BLACK;
        currentShape = "Line";

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                startX = evt.getX();
                startY = evt.getY();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                endX = evt.getX();
                endY = evt.getY();
                saveShape();
                repaint();
            }
        });
    }

    private void saveShape() {
        shapes.add(new ShapeData(startX, startY, endX, endY, currentColor, currentShape));
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public void setCurrentShape(String shape) {
        currentShape = shape;
    }

    public void clear() {
        shapes.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (ShapeData shapeData : shapes) {
            g.setColor(shapeData.color);

            switch (shapeData.shape) {
                case "Line":
                    g.drawLine(shapeData.startX, shapeData.startY, shapeData.endX, shapeData.endY);
                    break;
                case "Rect":
                    g.drawRect(Math.min(shapeData.startX, shapeData.endX), Math.min(shapeData.startY, shapeData.endY),
                            Math.abs(shapeData.endX - shapeData.startX), Math.abs(shapeData.endY - shapeData.startY));
                    break;
                case "Oval":
                    g.drawOval(Math.min(shapeData.startX, shapeData.endX), Math.min(shapeData.startY, shapeData.endY),
                            Math.abs(shapeData.endX - shapeData.startX), Math.abs(shapeData.endY - shapeData.startY));
                    break;
                default:
                    break;
            }
        }
    }

    private static class ShapeData {
        int startX, startY, endX, endY;
        Color color;
        String shape;

        ShapeData(int startX, int startY, int endX, int endY, Color color, String shape) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.color = color;
            this.shape = shape;
        }
    }
}
