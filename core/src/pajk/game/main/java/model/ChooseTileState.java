package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gustav on 2016-04-25.
 */
public class ChooseTileState implements State{

    private Unit activeUnit;
    private Board board;
    private StateManager manager;
    private Set<Tile> allowedTiles;

    public ChooseTileState(Board board){
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
                Tile cursorTile = board.getCursorTile();
                for (Tile t:
                     allowedTiles) {
                    if (t == cursorTile){
                        System.out.println("MOved the unit.");
                        board.moveUnit(activeUnit, cursorTile);
                        manager.setState(StateManager.StateName.MAIN_STATE);
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void activate() {
        manager = StateManager.getInstance();
        activeUnit = StateManager.getInstance().getActiveUnit();
        Tile centerTile = board.getPos(activeUnit);
        allowedTiles = board.getTilesWithinRange(new HashSet<>(), centerTile, centerTile, activeUnit.getMovement());
    }
}
