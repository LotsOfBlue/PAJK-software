package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * A state showing how much damage the units will deal to each other if you commence the attack, and also their chance
 * to hit and crit and other relevant info.
 * You can confirm the attack or back out.
 *
 * Created by Johan on 2016-04-25.
 */
public class CombatInfoState implements State{

    @Override
    public void performAction(ActionName action) {
        switch (action) {
            case ENTER:
                GameModel.getInstance().setState(GameModel.StateName.COMBAT);
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
