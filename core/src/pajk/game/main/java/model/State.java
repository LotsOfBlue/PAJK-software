package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by Gustav on 2016-04-22.
 */
public interface State {
    void performAction(ActionName action);
    GameModel.StateName getName();
    void activate();
}
