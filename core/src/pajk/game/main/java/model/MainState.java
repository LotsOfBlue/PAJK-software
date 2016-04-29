package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by Gustav on 2016-04-22.
 */
public class MainState implements State {

    Board board;

    public MainState(Board board){
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
                if (cursorTile.hasUnit() && cursorTile.getUnit().getAllegiance() == Unit.Allegiance.HUMAN){
                    GameModel.getInstance().setActiveUnit(cursorTile.getUnit());
                    GameModel.getInstance().setState(GameModel.StateName.UNIT_MENU);
                }
                break;
        }
    }

    @Override
    public void activate() {

    }
}
