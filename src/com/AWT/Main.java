package com.AWT;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Main {

    public static void main(String[] args) {
        JFrame jFrame = getFrame(); // моя форма
        jFrame.add(new MyComponent()); // мои компоненты

    }

    static  class MyComponent extends JComponent{
        @Override
        public void paint(Graphics g) {
            Graphics2D g2 =(Graphics2D)g; // графика

            Rectangle2D rectangle2D = new Rectangle2D.Double(0,0,200,100); // прямоугольник
            Ellipse2D ellipse2D = new Ellipse2D.Double(0,0,200,100); // овал
            g2.clip(ellipse2D); // rectangle2D обрезка по ellipse2D
            g2.fill(rectangle2D); // прорисовка
        }
    }

    private static JFrame getFrame() {
        JFrame jFrame = new JFrame(); // создать
        jFrame.setVisible(true); // видимость
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // закрытие
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension =toolkit.getScreenSize(); // выясняем центр экрана
        jFrame.setBounds(dimension.width/2-250, dimension.height/2-200, 250, 200);
        return jFrame;
    }
}
