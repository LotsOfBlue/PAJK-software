package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.GameModel;

/**
 * The game is always in one of multiple states, represented by this interface.
 * Each state handles a different part of game logic and reacts to inputs differently.
 * @author Gustav Grännsjö
 */
public interface State {
    /**
     * Called when player input is received.
     * @param action The type of input to handle.
     */
    void performAction(ActionName action);

    /**
     * Called when the state becomes active. Initializes everything the state needs.
     */
    void activate();

    /**
     * Get the name of the current state.
     * @return This state's name.
     */
    GameModel.StateName getName();
        //TODO make abstract and define performaction with methods for each buttonpress (switch case here instead of in all states)
}
