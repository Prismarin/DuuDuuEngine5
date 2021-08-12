package dev.kelvin.gameengine.ui;

import dev.kelvin.gameengine.gfx.Display;
import dev.kelvin.gameengine.gfx.Drawer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.ArrayList;

public class UIObjects extends ArrayList<UIObject> {

    public void onStateEntered() {
        for (UIObject uiObject : this)
            uiObject.onStateEntered();
    }

    public void tick() {
        int i = 0;
        while (i < size()) {
            get(i).tick();
            i++;
        }
    }

    public void render(Drawer drawer) {
        int i = 0;
        while (i < size()) {
            get(i).render(drawer);
            i++;
        }
    }
    
    public void setRenderPoint(Point renderPoint) {
        int i = 0;
        while (i < size()) {
            get(i).getRenderPoint(renderPoint);
            i++;
        }
    }

    public void onMouseMoved(MouseEvent e, Display display) {
        int i = 0;
        while (i < size()) {
            get(i).onMouseMoved(e, display);
            i++;
        }
    }

    public void onMouseClicked(MouseEvent e, Display display) {
        int i = 0;
        while (i < size()) {
            get(i).onMouseReleased(e, display);
            i++;
        }
    }

    public void onKeyTyped(KeyEvent e, Display display) {
        int i = 0;
        while (i < size()) {
            get(i).onKeyTyped(e, display);
            i++;
        }
    }

}
