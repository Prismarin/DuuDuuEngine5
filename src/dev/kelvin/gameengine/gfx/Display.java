package dev.kelvin.gameengine.gfx;

import dev.kelvin.gameengine.GameManager;
import dev.kelvin.gameengine.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    private Dimension size;
    public final Dimension defaultSize;

    public boolean fullscreen;

    private String title;

    public final Input input;

    public Display(String title, int width, int height) {
        defaultSize = new Dimension(width, height);
        input = new Input();
        createDisplay(title, width, height, false);
    }

    private void createDisplay(String title, int width, int height, boolean fullscreen) {
        if (frame != null)
            frame.dispose();
        this.title = title;
        frame = new JFrame(title);
        if (!fullscreen)
            size = new Dimension(width, height);
        else {
            size = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setUndecorated(true);
        }
        frame.setSize(size);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameManager.singleton.game.onApplicationClose();
                System.exit(0);
            }
            
            
        });

        canvas = new Canvas();
        canvas.setSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);
        canvas.setFocusable(false);
        frame.add(canvas);

        addInput();
        
        frame.pack();
        
        frame.setMinimumSize(frame.getSize());
        
        frame.setVisible(true);

        canvas.createBufferStrategy(3);
    }

    public void addInput() {
        frame.addKeyListener(input.keyManager);
        canvas.addKeyListener(input.keyManager);

        frame.addMouseListener(input.mouseManager);
        frame.addMouseMotionListener(input.mouseManager);
        canvas.addMouseListener(input.mouseManager);
        canvas.addMouseMotionListener(input.mouseManager);
    }

    public void setHalfSize() {
        fullscreen = false;
        Toolkit tk = Toolkit.getDefaultToolkit();
        size.width = tk.getScreenSize().width / 2;
        size.height = tk.getScreenSize().height / 2;

        frame.setUndecorated(false);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);
        canvas.setSize(size);
        frame.pack();
    }

    public void setFullscreen() {
        fullscreen = true;
        createDisplay(title, 0, 0, true);
    }

    public void setDefaultSize() {
        fullscreen = false;
        createDisplay(title, defaultSize.width, defaultSize.height, false);
    }

    private Point renderPoint = new Point(0, 0);

    public Point getRenderPoint() {
        renderPoint.x = 0;
        renderPoint.y = 0;
        
        if (isAspectGreaterThan169()) {
            renderPoint.y = (getHeight() - (getWidth() / 16 * 9)) / 2;
        } else {
            renderPoint.x = (getWidth() - (getHeight() / 9 * 16)) / 2;
        }
        
        return renderPoint;
    }

    public float getMultiplier() {
        //return (float) getWidth() / (float) defaultSize.width;
        return isAspectGreaterThan169() ? (float) getWidth() / (float) defaultSize.width : (float) getHeight() / (float) defaultSize.height;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public boolean isAspectGreaterThan169() {
        if (getHeight() >= getWidth()) {
            return true;
        } else if (getWidth() / 16 * 9 > getHeight()) {
            return false;
        }
        return true;
    }

}
