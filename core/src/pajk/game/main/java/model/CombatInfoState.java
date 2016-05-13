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
                GameModel.getInstance().setState(GameModel.StateName.COMBAT_STATE);
                break;

            case BACK:
                GameModel.getInstance().setState(GameModel.StateName.CHOOSE_TARGET);
                break;
        }
    }

    @Override
    public void activate() {
        //TODO display combat preview here
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.COMBAT_INFO;
    }
}
