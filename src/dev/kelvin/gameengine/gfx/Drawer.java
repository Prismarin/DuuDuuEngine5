package dev.kelvin.gameengine.gfx;

import dev.kelvin.gameengine.GameManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Drawer {

    public float multiplier = 1;
    public Point renderPoint;

    private Graphics g;
    private BufferStrategy bs;
    private final Display display;

    public Drawer(Display display) {
        this.display = display;
    }

    public void prepare() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            prepare();
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, display.getWidth(), display.getHeight());
    }

    public void show() {
        if (renderPoint.x != 0){
            g.clearRect(0, 0, renderPoint.x, display.getHeight());
            g.clearRect(display.getWidth() - renderPoint.x, 0, renderPoint.x, display.getHeight());
        }
        else if (renderPoint.y != 0) {
            g.clearRect(0, 0, display.getWidth(), renderPoint.y);
            g.clearRect(0, display.getHeight() - renderPoint.y, display.getWidth(), renderPoint.y);
        }
        bs.show();
        g.dispose();
    }

    public void tick() {
        multiplier = display.getMultiplier();
        renderPoint = display.getRenderPoint();
        GameManager.singleton.game.stateManager.uiManager.setRenderPoint(renderPoint);
        //System.out.println(renderPoint.x);
    }

    public void drawRect(Color color, float x, float y, int width, int height) {
        g.setColor(color);
        g.drawRect(Math.round(x * multiplier) + renderPoint.x, Math.round(y * multiplier) + renderPoint.y, Math.round(width * multiplier), Math.round(height * multiplier));
    }

    public void fillRect(Color color, float x, float y, int width, int height) {
        g.setColor(color);
        g.fillRect(Math.round(x * multiplier) + renderPoint.x, Math.round(y * multiplier) + renderPoint.y, Math.round(width * multiplier), Math.round(height * multiplier));
    }

    public void drawImg(BufferedImage img, float x, float y) {
        g.drawImage(img, Math.round(x * multiplier) + renderPoint.x, Math.round(y * multiplier) + renderPoint.y, Math.round(img.getWidth() * multiplier), Math.round(img.getHeight() * multiplier), null);
    }

    public void drawImg(BufferedImage img, float x, float y, int width, int height) {
        g.drawImage(img, Math.round(x * multiplier) + renderPoint.x, Math.round(y * multiplier) + renderPoint.y, Math.round(width * multiplier), Math.round(height * multiplier), null);
    }

    public void drawDrawable(Drawable drawable) {
        drawable.drawDrawable(this, g, display);
    }
    
    public void drawRect(Color color, Rectangle rectangle) {
        drawRect(color, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
//////////////// Nötige Methoden für Hotbar Klasse - hinzugefügt!/////////////////
    public void drawString(String text, int x, int y) {
        Font test = new Font("Arial",Font.BOLD, (int) (20f * multiplier));
        g.setFont(test);
        g.setColor(Color.WHITE);
        g.drawString(text, (int) (x * multiplier) + renderPoint.x, (int) (y * multiplier) + renderPoint.y);
    }
    
    public void drawString(String text, int x, int y, int fontSize) {
        Font test = new Font("Arial",Font.BOLD, (int) (fontSize * multiplier));
        g.setFont(test);
        g.setColor(Color.WHITE);
        g.drawString(text, (int) (x * multiplier) + renderPoint.x, (int) (y * multiplier) + renderPoint.y);
    }
    
    public void drawString(String text, float x, float y, String font) {
        Font test = new Font(font,Font.BOLD, (int) (20f * multiplier));
        g.setFont(test);        
        g.drawString(text, (int) (x * multiplier) + renderPoint.x, (int) (y * multiplier) + renderPoint.y);
    }
    
    public void drawScaledImage(BufferedImage img, float x, float y, double scale) {   //Scale ist Multiplikationsfaktor der Bildgröße
        g.drawImage(img, Math.round(x * multiplier) + renderPoint.x, Math.round(y * multiplier) + renderPoint.y, (int) (img.getWidth() * multiplier*scale), (int) (img.getHeight() * multiplier*scale), null);
    }
    
///////////////////////////////////////////////////////////////////////////////       
}
