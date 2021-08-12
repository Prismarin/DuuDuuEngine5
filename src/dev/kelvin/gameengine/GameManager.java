package dev.kelvin.gameengine;

import dev.kelvin.gameengine.gfx.Drawer;

public class GameManager {

    public static final String version = "5.1pre EZ EDITION";

    public static final GameManager singleton;
    public static boolean debug = false;

    static {
        singleton = new GameManager();
    }

    private GameManager() {}

    private boolean isGameRunning;
    public Game game;

    public void launchGame(Game game, int tps) {
        launchGame(game, tps, false);
    }

    public void launchGame(Game game, int tps, boolean _debug) {
        if (isGameRunning) {
            System.err.println("The game has already been started!");
            return;
        }
        System.out.println("Starting DùuDùu Engine " + version);
        System.out.println("Modules in development:");
        System.out.println("-AudioManager");
        System.out.println("-GUI and UIManager");
        this.game = game;
        this.game.launch();
        isGameRunning = true;
        debug = _debug;
        gameloop(tps);
    }

    private void gameloop(int tps) {
        double timePerTick = 1000000000d/tps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (isGameRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                if (debug)
                    System.out.println("TPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
    }

    private void tick() {
        game.display.input.tick();
        if (game.stateManager.getCurrentState().id != -1)
            game.alwaysTick();
        game.stateManager.tick();
    }

    private void render() {
        game.stateManager.render();
    }
    
    public Drawer getDrawer(){
        return game.stateManager.getDrawer();
    }
    
}
