package dev.kelvin.gameengine.state;

import dev.kelvin.gameengine.GameManager;
import dev.kelvin.gameengine.gfx.Display;
import dev.kelvin.gameengine.gfx.Drawer;
import dev.kelvin.gameengine.input.Input;
import dev.kelvin.gameengine.resources.ResourceClass;
import dev.kelvin.gameengine.ui.UIObjects;

public abstract class State {

    public final int defaultId = -183468;
    public final String defaultName = "-183468";

    public final int id;
    public final String name;

    protected Input input;
    protected StateManager stateManager;
    protected ResourceClass resources;
    protected Display display;
    protected UIObjects uiObjects;

    public State(int id) {
        this.id = id;
        this.name = this.defaultName;
        uiObjects = new UIObjects();
    }

    public State(String name) {
        this.id = this.defaultId;
        this.name = name;
        uiObjects = new UIObjects();
    }

    public State(int id, String name) {
        this.id = id;
        this.name = name;
        uiObjects = new UIObjects();
    }

    public void getModules() {
        input = GameManager.singleton.game.display.input;
        stateManager = GameManager.singleton.game.stateManager;
        resources = GameManager.singleton.game.resources;
        display = GameManager.singleton.game.display;
    }

    public final void postInit() {
        getModules();
    }

    public void onStateEntered(){}

    public abstract void tick();

    public abstract void render(Drawer drawer);

    public void onStateLeft(){}

}
