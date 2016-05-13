package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by Gustav on 2016-04-22.
 */
public class MainState implements State {

    private GameModel model;
    private Board board;

    @Override
    public void performAction(ActionName action) {
        switch (action){
            case UP:
                board.moveCursor(Board.Direction.NORTH);
                break;
            case LEFT:
                board.moveCursor(Board.Direction.WEST);
                break;
            case RIGHT:
                board.moveCursor(Board.Direction.EAST);
                break;
            case DOWN:
                board.moveCursor(Board.Direction.SOUTH);
                break;
            case ENTER:
                Tile cursorTile = board.getCursorTile();
                if (cursorTile.hasUnit()){
                    Unit currentUnit = cursorTile.getUnit();
                    if (    currentUnit.getAllegiance() == Unit.Allegiance.PLAYER &&
                            currentUnit.getUnitState() != Unit.UnitState.ATTACKED) {
                        model.setActiveUnit(currentUnit);
                        model.setState(GameModel.StateName.UNIT_MENU);
                    }
                }
                break;
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MAIN_STATE;
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        board = GameModel.getInstance().getBoard();
        //If all units are done when this state activates, begin enemy turn
        if (model.allUnitsDone()) {
            model.setState(GameModel.StateName.ENEMY_TURN);
        }
    }
}
