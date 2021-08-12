package dev.kelvin.gameengine.state;

import dev.kelvin.gameengine.GameManager;
import dev.kelvin.gameengine.gfx.Drawer;
import dev.kelvin.gameengine.resources.ResourceClass;
import dev.kelvin.gameengine.utils.Timer;

public class LoadingState extends State {

    private final ResourceClass resourceClass;

    private final ResourceClass ownRes;

    private boolean loadedUp = false, time = false;

    private final Timer timer;

    public LoadingState(ResourceClass resourceClass) {
        super(-1, "load");
        this.resourceClass = resourceClass;
        ownRes = new ResourceClass(resourceClass.res);
        ownRes.addResource("bg", "res://engine/duuduu.png");
        ownRes.loadUp();
        timer = new Timer(4000, () -> {
            time = true;
        });
        //System.out.println(ownRes.getLoadedBufferedImage("bg"));
    }

    @Override
    public void onStateEntered() {
        new Thread(this::load).start();
        timer.start();
    }

    public synchronized void load() {
        resourceClass.loadUp();
        loadedUp = true;
    }

    @Override
    public void tick() {
        timer.tick();
        if (loadedUp && (time || GameManager.debug))
            GameManager.singleton.game.stateManager.queueState(0);
    }

    @Override
    public void render(Drawer drawer) {
        if (display != null) {
            drawer.drawImg(ownRes.getLoadedBufferedImage("bg"), 0, 0, display.getWidth(), display.getHeight());
        } else
            display = GameManager.singleton.game.display;
    }

}
