package snake.dev.game.display;

import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame frame;
    private Canvas canvas;
    private String title;
    private int width, heigth;

    public Canvas getCanvas(){
        return canvas;
    }
    public JFrame getFrame(){
        return frame;
    }

    public Display(String title, int width, int heigth){
        this.title = title;
        this.heigth = heigth;
        this.width = width;

        createDisplay();
    }

    private void createDisplay(){
        System.out.println("Calling function createDisplay()...");
        frame = new JFrame(title);
        frame.setSize(width,heigth);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,heigth));
        canvas.setMinimumSize(new Dimension(width,heigth));
        canvas.setMaximumSize(new Dimension(width,heigth));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }
}
