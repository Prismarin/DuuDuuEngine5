package dev.kelvin.gameengine;

import dev.kelvin.gameengine.gfx.Display;
import dev.kelvin.gameengine.resources.ResourceClass;
import dev.kelvin.gameengine.state.LoadingState;
import dev.kelvin.gameengine.state.State;
import dev.kelvin.gameengine.state.StateManager;

public class Game {

    public final StateManager stateManager;
    public final ResourceClass resources;
    public final Display display;

    public Game(String title, int width, int height, ResourceClass resourceClass, State beginningState, State... states) {
        display = new Display(title, width, height);
        resources = resourceClass;
        stateManager = new StateManager(display, new LoadingState(resources), beginningState, states);
    }

    public void launch() {}

    public void alwaysTick() {}

    public void onApplicationClose() {}

}
