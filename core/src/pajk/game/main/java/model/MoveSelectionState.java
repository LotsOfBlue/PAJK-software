package pajk.game.main.java.model;


import java.util.Set;

/**
 * This state allows you to select what tile you want to move the active unit to. It paints the allowed tiles blue and
 * allows you to move the cursor.
 *
 * Created by Gustav on 2016-04-25.
 */
public class MoveSelectionState extends MoveState{

    private Unit activeUnit;
    private Board board;
    private GameModel model;
    private Set<Tile> allowedTiles;


    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MOVE_SELECT;
    }

    @Override
    public void enterAction(){
        //If the player selected an allowed tile, proceed to the state for moving units.
        Tile cursorTile = board.getCursorTile();
        for (Tile t:
                allowedTiles) {
            if (t == cursorTile){
                for (Tile ti:
                        allowedTiles) {
                    ti.setOverlay(Tile.Overlay.NONE);
                }
                activeUnit.setUnitState(Unit.UnitState.MOVED);
                model.setTargetTile(t);
                model.setState(GameModel.StateName.MOVE_UNIT);
                break;
            }
        }
    }

    /**
     * Cancel moving and return back to the unit menu
     */
    @Override
    public void backAction() {
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
        //Get the tiles the active unit can move to.
        allowedTiles = board.getTilesWithinMoveRange(activeUnit);
        for (Tile t:
             allowedTiles) {
            t.setOverlay(Tile.Overlay.MOVEMENT);
        }
    }
}
