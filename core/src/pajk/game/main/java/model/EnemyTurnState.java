package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Johan on 2016-04-28.
 */
public class EnemyTurnState implements State {
    private GameModel gameModel;
    private Board board;
    private PathFinder pathFinder;
    private Queue<Unit> unitQueue;

    @Override
    public void performAction(ActionName action) {
        update();
    }

    private void update(){

    }

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        pathFinder = new PathFinder(board);
        System.out.println("ENEMY TURN"); //TODO debug
        gameModel.newTurn();
        unitQueue = new LinkedList<>();

        //Add all the units to the queue to act.
        for (Unit u : gameModel.getUnitList()) {
            if (u.getAllegiance().equals(Unit.Allegiance.AI)) {
                unitQueue.add(u);
            }
        }

        //Once all units are finished, the player's turn begins
        System.out.println("PLAYER TURN"); //TODO debug
        gameModel.newTurn();
        GameModel.getInstance().setState(GameModel.StateName.MAIN_STATE);
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
