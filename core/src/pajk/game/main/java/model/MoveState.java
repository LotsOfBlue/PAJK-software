package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Abstract state that allows you to move the cursor with the arrow keys.
 *
 * Created by palm on 2016-05-11.
 */
public abstract class MoveState extends State{




    @Override
    void upAction(){
        GameModel.getInstance().getBoard().moveCursor(Board.Direction.NORTH);
    }
    @Override
    void downAction(){
        GameModel.getInstance().getBoard().moveCursor(Board.Direction.SOUTH);
    }
    @Override
    void leftAction(){
        GameModel.getInstance().getBoard().moveCursor(Board.Direction.WEST);
    }
    @Override
    void rightAction(){
        GameModel.getInstance().getBoard().moveCursor(Board.Direction.EAST);
    }





}
