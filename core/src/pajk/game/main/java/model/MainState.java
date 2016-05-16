package pajk.game.main.java.model;


/**
 * This state is active when you start the game. It allows you to move the cursor around the board and select units.
 *
 * Created by Gustav on 2016-04-22.
 */
public class MainState extends MoveState {

    private GameModel model;
    private Board board;

    @Override
    public void enterAction() {
        //Open the menu for the unit under the cursor of it's an allied unit that has not yet acted this turn.
        Tile cursorTile = board.getCursorTile();
        if (cursorTile.hasUnit()){
            Unit currentUnit = cursorTile.getUnit();
            model.setActiveUnit(currentUnit);
            if(currentUnit.getAllegiance() == Unit.Allegiance.AI){
                model.setState(GameModel.StateName.STATUS);
            } else {
                model.setState(GameModel.StateName.UNIT_MENU);
            }
//            if (    currentUnit.getAllegiance() == Unit.Allegiance.PLAYER &&
//                    currentUnit.getUnitState() != Unit.UnitState.DONE) {
//                model.setActiveUnit(currentUnit);
//                model.setState(GameModel.StateName.UNIT_MENU);
//            }
        }
    }

    @Override
    public void backAction() {

    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MAIN;
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
