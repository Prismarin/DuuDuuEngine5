package dev.kelvin.gameengine.input;

import dev.kelvin.gameengine.GameManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    public boolean leftPressed, middlePressed, rightPressed;
    public int mouseX, mouseY;

    private final boolean[] pressed, justPressed, cantPress;

    public MouseManager() {
        pressed = new boolean[20];
        justPressed = new boolean[20];
        cantPress = new boolean[20];
    }

    public void tick() {
        for (int i = 0; i < pressed.length; i++) {
            if (cantPress[i] && !pressed[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && pressed[i]) {
                justPressed[i] = true;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        try {
            GameManager.singleton.game.stateManager.uiManager.onMouseMoved(e);
        } catch (Exception ignored) {}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        try {
            GameManager.singleton.game.stateManager.uiManager.onMouseMoved(e);
        } catch (Exception ignored) {}
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
            pressed[MouseEvent.BUTTON1] = true;
        }
        if(e.getButton() == MouseEvent.BUTTON2) {
            middlePressed = true;
            pressed[MouseEvent.BUTTON2] = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
            pressed[MouseEvent.BUTTON3] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
            pressed[MouseEvent.BUTTON1] = false;
        }
        if(e.getButton() == MouseEvent.BUTTON2) {
            middlePressed = false;
            pressed[MouseEvent.BUTTON2] = false;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
            pressed[MouseEvent.BUTTON3] = false;
        }
        try {
            GameManager.singleton.game.stateManager.uiManager.onMouseClicked(e);
        } catch (Exception ignored) {}
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public boolean buttonJustPressed(int keyButtonCode) {
        if(keyButtonCode < 0 || keyButtonCode >= pressed.length)
            return false;
        return justPressed[keyButtonCode];
    }

}
