package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.GameModel;

/**
 * The game is always in one of multiple states, represented by this interface.
 * Each state handles a different part of game logic and reacts to inputs differently.
 * @author Gustav Grännsjö
 */
public abstract class State {
    /**
     * Called when player input is received.
     * @param action The type of input to handle.
     */
    public void performAction(ActionName action){

            switch(action) {
                case UP:
                    upAction();
                    break;
                case DOWN:
                    downAction();
                    break;
                case LEFT:
                    leftAction();
                    break;
                case RIGHT:
                    rightAction();
                    break;
                case ENTER:
                    enterAction();
                    break;
                case BACK:
                    backAction();
                    break;
            }

    }
     void upAction(){}
     void downAction(){}
     void leftAction(){}
     void rightAction(){}
     void enterAction(){}
     void backAction(){}

    /**
     * Called when the state becomes active. Initializes everything the state needs.
     */
    public abstract void activate();



    /**
     * Get the name of the current state.
     * @return This state's name.
     */
    public abstract GameModel.StateName getName();
        //TODO make abstract and define performaction with methods for each buttonpress (switch case here instead of in all states)
}
