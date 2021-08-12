package dev.kelvin.gameengine.ui;

import dev.kelvin.gameengine.GameManager;
import dev.kelvin.gameengine.gfx.Display;
import dev.kelvin.gameengine.gfx.Drawer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class UIObject {

    protected boolean focused;
    public boolean focusable;

    public boolean hovering;

    public int x, y, width, height;
    protected Point renderPoint;
    public int boundsX, boundsY, boundsWidth, boundsHeight;
    protected final Rectangle bounds;

    private boolean firstTick;
    public boolean renderBounds;

    public UIObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        boundsX = x;
        boundsY = y;
        boundsWidth = width;
        boundsHeight = height;
        bounds = new Rectangle(x, y, width, height);
        renderPoint = new Point(0, 0);
    }

    public void onStateEntered() {
        firstTick = true;
    }

    public void tick() {
        if (!firstTick) {
            if (!focusable)
                focused = false;

            if (hovering)
                onMouseHovers();

            bounds.x = boundsX + renderPoint.x;
            bounds.y = boundsY + renderPoint.y;
            bounds.width = boundsWidth;
            bounds.height = boundsHeight;
        } else
            firstTick = false;
    }

    public abstract void render(Drawer drawer);

    public void renderBounds(Drawer drawer) {
        if (GameManager.debug)
            drawer.drawRect(Color.green, bounds.x - renderPoint.x, bounds.y - renderPoint.y, bounds.width, bounds.height);
            //drawer.drawRect(Color.green, bounds);
    }

    public void onMouseReleased(MouseEvent e, Display display) {
        if (!firstTick) {
            if (hovering) {
                if (focusable)
                    focused = !focused;
                onUIObjectClicked();
            }
        }
    }

    public abstract void onUIObjectClicked();

    public void onMouseMoved(MouseEvent e, Display display) {
        float x = (float) (e.getX() + renderPoint.x) / display.getMultiplier();
        float y = (float) (e.getY() + renderPoint.y) / display.getMultiplier();
        hovering = bounds.contains(x, y);
    }

    public abstract void onMouseHovers();

    public void onKeyTyped(KeyEvent e, Display display) {
        if (focused && !firstTick)
            onFocusedKeyTyped(e.getKeyChar());
    }

    public abstract void onFocusedKeyTyped(char input);
    
    public void getRenderPoint(Point point) {
        renderPoint = point;
    }
    
}
