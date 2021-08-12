package dev.kelvin.gameengine.state;

import dev.kelvin.gameengine.gfx.Display;
import dev.kelvin.gameengine.gfx.Drawer;
import dev.kelvin.gameengine.ui.UIManager;

import java.util.HashMap;

public class StateManager {

    private final HashMap<Integer, State> byId;
    private final HashMap<String, State> byName;

    private State currentState;
    private State nextState;
    private boolean switchState;

    private final Drawer drawer;
    public final UIManager uiManager;

    public StateManager(Display display, LoadingState loadingState, State beginningState, State... states) {
        drawer = new Drawer(display);
        byId = new HashMap<>();
        byName = new HashMap<>();
        registerState(loadingState);
        if (beginningState.id != 0)
            System.err.println("Id of beginningState should be \"0\"");
        registerState(beginningState);
        for (State state : states) {
            registerState(state);
        }
        currentState = loadingState;
        currentState.onStateEntered();
        uiManager = new UIManager();
        uiManager.setUiObjects(currentState.uiObjects);
        uiManager.onStateEntered();
    }

    public void registerState(State state) {
        if (state.id != state.defaultId)
            byId.put(state.id, state);
        if (!state.name.equals(state.defaultName))
            byName.put(state.name, state);
    }

    public void queueState(int id) {
        queueState(byId.get(id));
    }

    public void queueState(String name) {
        queueState(byName.get(name));
    }
    
    public void queueState(State state) {
        if (state != null) {
            nextState = state;
            switchState = true;
        } else
            System.err.println("Couldn't queue next state because nextState == null");
    }

    private void switchState() {
        switchState = false;
        if (currentState != null)
            currentState.onStateLeft();
        currentState = nextState;
        currentState.postInit();
        currentState.onStateEntered();
        uiManager.setUiObjects(currentState.uiObjects);
        uiManager.onStateEntered();
    }

    public void tick() {
        drawer.tick();
        if (switchState)
            switchState();
        currentState.tick();
        uiManager.tick();
    }

    public void render() {
        drawer.prepare();
        currentState.render(drawer);
        uiManager.render(drawer);
        drawer.show();
    }

    public State getCurrentState() {
        return currentState;
    }

    public Drawer getDrawer(){
        return drawer;
    }
}
