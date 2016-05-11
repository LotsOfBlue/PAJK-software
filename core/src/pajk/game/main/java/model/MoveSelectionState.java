package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.Set;

/**
 * Created by Gustav on 2016-04-25.
 */
public class MoveSelectionState extends MoveState{

    private Unit activeUnit;
    private Board board;
    private GameModel model;
    private Set<Tile> allowedTiles;




//    @Override
//    public void performAction(ActionName action) {
//        switch (action){
//            case UP:
//                board.moveCursor(Board.Direction.NORTH);
//                break;
//            case LEFT:
//                board.moveCursor(Board.Direction.WEST);
//                break;
//            case RIGHT:
//                board.moveCursor(Board.Direction.EAST);
//                break;
//            case DOWN:
//                board.moveCursor(Board.Direction.SOUTH);
//                break;
//            case ENTER:
//                enterAction();
//                break;
//            case BACK:
//                backToMenu();
//                break;
//        }
//    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MOVE_SELECT;
    }

    @Override
    public void enterAction(){
        Tile cursorTile = board.getCursorTile();
        for (Tile t:
                allowedTiles) {
            if (t == cursorTile){
                System.out.println("Moved the unit.");
                for (Tile ti:
                        allowedTiles) {
                    ti.setOverlay(Tile.Overlay.NONE);
                }

                activeUnit.setUnitState(Unit.UnitState.MOVED);
                model.setTargetTile(t);
                System.out.println("Test1");
                //Open the menu again when the unit is finished moving
                model.setState(GameModel.StateName.MOVE_UNIT);
                break;
            }
        }
    }

    /**
     * Cancel moving and return back to the unit menu
     */
    @Override
    public void backToMenu() {
        for (Tile t : allowedTiles) {
            t.setOverlay(Tile.Overlay.NONE);
        }
        model.setState(GameModel.StateName.UNIT_MENU);
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        activeUnit = model.getActiveUnit();
        board = GameModel.getInstance().getBoard();
//        Tile centerTile = board.getPos(activeUnit);
        allowedTiles = board.getTilesWithinMoveRange(activeUnit);
        for (Tile t:
             allowedTiles) {
            t.setOverlay(Tile.Overlay.MOVEMENT);
        }
    }
}
