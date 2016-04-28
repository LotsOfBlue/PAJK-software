package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gustav on 2016-04-25.
 */
public class MoveSelectionState implements State{

    private Unit activeUnit;
    private Board board;
    private GameModel manager;
    private Set<Tile> allowedTiles;

    public MoveSelectionState(Board board){
        this.board = board;
    }

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
                enterAction();
                break;
        }
    }

    private void enterAction(){
        Tile cursorTile = board.getCursorTile();
        for (Tile t:
                allowedTiles) {
            if (t == cursorTile){
                System.out.println("Moved the unit.");
                for (Tile ti:
                        allowedTiles) {
                    ti.setOverlay(Tile.Overlay.NONE);
                }
                board.moveUnit(activeUnit, cursorTile);
                manager.setState(GameModel.StateName.MAIN_STATE);
                break;
            }
        }
    }

    @Override
    public void activate() {
        manager = GameModel.getInstance();
        activeUnit = GameModel.getInstance().getActiveUnit();
        Tile centerTile = board.getPos(activeUnit);
        allowedTiles = board.getTilesWithinMoveRange(new HashSet<>(), centerTile, centerTile, activeUnit.getMovement());
        for (Tile t:
             allowedTiles) {
            t.setOverlay(Tile.Overlay.MOVEMENT);
        }
    }
}
