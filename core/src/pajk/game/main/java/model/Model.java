package pajk.game.main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palm on 2016-04-18.
 */
public class Model {

    public enum actionName{
        UP,DOWN,LEFT,RIGHT,ENTER,BACK
    }

    private Board board;
    private Player player;
    private Player computer;
    public Model(){
        board = new Board(10);
        player = new Player(false);
        computer = new Player(true);
        Unit myLittleSoldier = new Unit(Unit.Allegiance.human);
        player.addUnit(myLittleSoldier);
        System.out.println(board.toString());
    }

    public void performAction(actionName action){
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
        }
    }
}
