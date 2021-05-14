package com.AWT;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class Surface extends JPanel implements ActionListener {

    // переменные
    private Timer timer;
    private double rotate = 1;
    private int pos_x = 8;
    private int pos_y = 8;
    private final double delta[] = {1, 1};

    private final int RADIUS = 60;


    public Surface() { // конструктор
        initTimer();
    }

    private void initTimer() { // инициализация таймера
        timer = new Timer(10, this);
        timer.start();
    }

    private void doDrawing(Graphics g) { // прорисовка графики
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        // Поскольку мы не создавали копию Graphics2Dобъекта,
        // мы сохраняем старый клип для дальнейшего использования. В конце концов, мы должны сбросить клип на исходный.
        Shape oldClip = g2d.getClip();

        int w = getWidth();
        int h = getHeight();

        // Прямоугольник вращается. Он всегда располагается посередине панели.
        Rectangle rect = new Rectangle(0, 0, 200, 80);

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(rotate), w / 2, h / 2);
        tx.translate(w / 2 - 100, h / 2 - 40);

        Ellipse2D circle = new Ellipse2D.Double(pos_x, pos_y,
                RADIUS, RADIUS);

        // Здесь мы получаем форму повернутого прямоугольника.
        GeneralPath path = new GeneralPath();
        path.append(tx.createTransformedShape(rect), false);

        // Здесь мы ограничиваем рисование пересечением двух фигур.
        // Если они накладываются друг на друга, внутренняя часть получившейся формы заполняется цветом.
        g2d.clip(circle);
        g2d.clip(path);

        g2d.setPaint(new Color(110, 110, 110)); // цвет
        g2d.fill(circle); // прорисовка круга

        // С помощью этого setClip()метода мы сбрасываем область клипа на старый клип, прежде чем рисовать фигуры.
        // В отличие от clip()метода, setClip() не объединяет области обрезки.
        // Он сбрасывает клип в новую область. Поэтому этот метод следует использовать исключительно при восстановлении старой обоймы.
        g2d.setClip(oldClip);

        g2d.draw(circle);
        g2d.draw(path);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void step() { // шаг трансформации

        int w = getWidth();
        int h = getHeight();

        rotate += 1;

        if (pos_x < 0) {

            delta[0] = 1;
        } else if (pos_x > w - RADIUS) {

            delta[0] = -1;
        }

        if (pos_y < 0) {

            delta[1] = 1;
        } else if (pos_y > h - RADIUS) {

            delta[1] = -1;
        }

        pos_x += delta[0];
        pos_y += delta[1];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
        repaint();
    }
}

public class ClippingShapesEx extends JFrame {

    public ClippingShapesEx() { // конструктор
        initUI(); // инициализация класса
    }

    private void initUI() {

        setTitle("Clipping shapes"); // наш заголовок

        add(new Surface());  // добавление наших объектов

        setSize(350, 300); // размер
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // выход
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) { // Главный класс

        EventQueue.invokeLater(new Runnable() { // поток

            @Override
            public void run() {
                ClippingShapesEx ex = new ClippingShapesEx();  // запуск нашего класса ClippingShapesEx
                ex.setVisible(true); // видимость
            }
        });
    }
}