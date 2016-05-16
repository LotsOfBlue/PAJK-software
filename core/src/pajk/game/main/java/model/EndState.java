package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.GameModel.StateName;

/**
 * Created by jonatan on 16/05/2016.
 */
public class EndState implements State {
    private int score, turns, units;

    @Override
    public StateName getName(){
        return StateName.END;
    }

    @Override
    public void activate(){

    }

    @Override
    public void performAction (ActionName actionName){

    }

}
