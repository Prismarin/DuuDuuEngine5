package dev.kelvin.gameengine.ui;

import dev.kelvin.gameengine.gfx.Drawer;

import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject {

    protected BufferedImage imageNormal, imageHovering;
    protected Runnable onMouseClicked;

    public UIImageButton(BufferedImage imageNormal, BufferedImage imageHovering, int x, int y, int width, int height, Runnable onMouseClicked) {
        super(x, y, width, height);
        this.imageNormal = imageNormal;
        this.imageHovering = imageHovering;
        this.onMouseClicked = onMouseClicked;
    }

    @Override
    public void render(Drawer drawer) {
        if (!focusable) {
            if (!hovering)
                drawer.drawImg(imageNormal, x, y, width, height);
            else
                drawer.drawImg(imageHovering, x, y, width, height);
            if (renderBounds)
                renderBounds(drawer);
        } else {
            if (hovering || focused)
                drawer.drawImg(imageHovering, x, y, width, height);
            if (renderBounds)
                renderBounds(drawer);
        }
    }
    
    public void toggleToggleMode() {
        focusable = !focusable;
    }

    @Override
    public void onUIObjectClicked() {
        onMouseClicked.run();
    }

    @Override
    public void onMouseHovers() {

    }

    @Override
    public void onFocusedKeyTyped(char input) {

    }

}
