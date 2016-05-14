package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * The states is where the meat of the game logic lies. It is in states where we decide what happens each frame of
 * the game. It is also here where we decide if we should change to a different state.
 *
 * Created by Gustav on 2016-04-22.
 */
public interface State {
    /**
     * Handles the keyboard input from the controller, is called upon repeatedly, even if there is no current
     * input from the keyboard.
     * @param action
     */
    void performAction(ActionName action);

    GameModel.StateName getName();

    //TODO make abstract and define performaction with methods for each buttonpress (switch case here instead of in all states)

    /**
     * Function that does things when the state is set to the active state. Usually used for initializing variables.
     */
    void activate();
}
