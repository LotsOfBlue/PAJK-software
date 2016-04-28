package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by Johan on 2016-04-28.
 */
public class EnemyTurnState implements State {

    @Override
    public void performAction(ActionName action) {}

    @Override
    public void activate() {
        System.out.println("ENEMY TURN"); //TODO debug
        for (Unit u : GameModel.getInstance().getComputerPlayer().getUnitList()) {
            //TODO enemy unit logic happens here
        }
        //Once all units are finished, the player's turn begins
        System.out.println("PLAYER TURN"); //TODO debug
        GameModel.getInstance().setState(GameModel.StateName.MAIN_STATE);
    }
}
