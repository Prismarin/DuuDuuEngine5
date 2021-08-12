package dev.kelvin.gameengine.ui;

import dev.kelvin.gameengine.GameManager;
import dev.kelvin.gameengine.gfx.Display;
import dev.kelvin.gameengine.gfx.Drawer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class UIManager {

    private Display display;

    private UIObjects uiObjects;

    public UIManager() {
        //display = GameManager.singleton.game.display;
    }

    public void setUiObjects(UIObjects uiObjects) {
        this.uiObjects = uiObjects;
    }

    public void onStateEntered() {
        if (uiObjects != null)
            uiObjects.onStateEntered();
    }

    public void tick() {
        display = GameManager.singleton.game.display;
        if (uiObjects != null)
            uiObjects.tick();
    }

    public void render(Drawer drawer) {
        if (uiObjects != null)
            uiObjects.render(drawer);
    }

    public void setRenderPoint(Point renderPoint) {
        if (uiObjects != null)
            uiObjects.setRenderPoint(renderPoint);
    }

    public void onMouseMoved(MouseEvent e) {
        if (uiObjects != null)
            uiObjects.onMouseMoved(e, display);
    }

    public void onMouseClicked(MouseEvent e) {
        if (uiObjects != null)
            uiObjects.onMouseClicked(e, display);
    }

    public void onKeyTyped(KeyEvent e) {
        if (uiObjects != null)
            uiObjects.onKeyTyped(e, display);
    }

}
