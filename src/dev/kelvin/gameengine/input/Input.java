package dev.kelvin.gameengine.input;

public class Input {

    public final MouseManager mouseManager;
    public final KeyManager keyManager;

    public Input() {
        mouseManager = new MouseManager();
        keyManager = new KeyManager();
    }

    public void tick() {
        mouseManager.tick();
        keyManager.tick();
    }

}
