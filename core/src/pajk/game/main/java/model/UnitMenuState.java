package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by Gustav on 2016-04-22.
 */
public class UnitMenuState implements State {

    private Board board;
    private Unit activeUnit;
    

    public UnitMenuState(Board board){
        this.board = board;
    }

    @Override
    public void performAction(ActionName action) {
        switch (action){
            case UP:

                break;
            case LEFT:

                break;
            case RIGHT:

                break;
            case DOWN:

                break;
            case ENTER:

                break;
        }
    }

    @Override
    public void activate() {
        activeUnit = StateManager.getInstance().getActiveUnit();
    }
}
