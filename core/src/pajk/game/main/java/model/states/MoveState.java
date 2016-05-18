package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.State;

/**
 * Abstract state that allows you to move the cursor with the arrow keys.
 *
 * Created by palm on 2016-05-11.
 */
public abstract class MoveState implements State {
    private Board board;



    @Override
    public void performAction(ActionName action) {
        board = GameModel.getInstance().getBoard();
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
            case BACK:
                backAction();
                break;
        }
    }


    public abstract void enterAction();
    public abstract void backAction();


}
