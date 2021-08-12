package dev.kelvin.gameengine.input;

import dev.kelvin.gameengine.GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private final boolean[] keys, justPressed, cantPress;

    public KeyManager() {
        keys = new boolean[1024];
        justPressed = new boolean[1024];
        cantPress = new boolean[1024];
    }

    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        GameManager.singleton.game.stateManager.uiManager.onKeyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }

}
