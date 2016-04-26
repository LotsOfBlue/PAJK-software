package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by Johan on 2016-04-25.
 */
public class CombatInfoState implements State{

    @Override
    public void performAction(ActionName action) {
        switch (action) {
            case ENTER:
                //TODO Combat state
                break;

            case BACK:
                StateManager.getInstance().setState(StateManager.StateName.CHOOSE_TILE);
                break;
        }
    }

    @Override
    public void activate() {

    }
}
