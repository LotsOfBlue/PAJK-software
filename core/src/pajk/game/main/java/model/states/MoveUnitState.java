package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.utils.PathFinder;

import java.util.List;

/**
 * State that moves the unit towards a target tile step by step.
 * Does not accept user input while the unit is moving.
 *
 * Created by Gustav on 2016-05-11.
 */
public class MoveUnitState implements State {
    private GameModel gameModel;
    private Board board;
    private Unit unit;
    private Tile target;
    private List<Tile> path;
    private int cooldown = 0;

    @Override
    public void performAction(ActionName action) {
        update();
    }

    private void update(){
        //Moves the unit along once every 7 frames.
        if (cooldown == 0){
            board.moveAlongPath(path, unit);
            cooldown = 7;
        }
        //Count down.
        cooldown--;
        if (board.getPos(unit) == target){
            //Change state if we've reached the target.
            gameModel.setState(GameModel.StateName.UNIT_MENU);
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MOVE_UNIT;
    }

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        unit = gameModel.getActiveUnit();
        target = gameModel.getTargetTile();
        //Find the quickest path between where the unit is now and where he is supposed to go.
        path = PathFinder.getQuickestPath(board, board.getPos(unit), target, unit);
    }
}
