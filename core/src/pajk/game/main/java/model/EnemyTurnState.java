package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 2016-04-28.
 */
public class EnemyTurnState implements State {
    private GameModel gameModel;
    private Board board;

    @Override
    public void performAction(ActionName action) {}

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        System.out.println("ENEMY TURN"); //TODO debug
        gameModel.newTurn();

        //Make all enemy units act
        for (Unit u : gameModel.getUnitList()) {
            if (u.getAllegiance().equals(Unit.Allegiance.AI)) {
                Unit target = findTarget(u);
                //TODO move to and attack target
            }
        }

        //Once all units are finished, the player's turn begins
        System.out.println("PLAYER TURN"); //TODO debug
        gameModel.newTurn();
        GameModel.getInstance().setState(GameModel.StateName.MAIN_STATE);
    }

    private Unit findTarget(Unit active) {
        List<Unit> potentialTargets = new ArrayList<>();
        Unit target = null;

        //Find all player-controlled units
        for (Unit u : gameModel.getUnitList()) {
            if (u.getAllegiance().equals(Unit.Allegiance.PLAYER)) {
                potentialTargets.add(u);
            }
        }
        System.out.println(potentialTargets.size() + " targets found: " + potentialTargets); //TODO debug

        //Compare the potential targets and store the closest one
        int distanceToTarget = 1000000;
        for (Unit u : potentialTargets) {
            Tile targetTile = board.getPos(u);
            List<Tile> path = PathFinder.getQuickestPath(board, board.getPos(active), targetTile, active);
            //Check the path's length
            int distance = 0;
            for (Tile t : path) {
                distance += t.getMovementCost(active.getMovementType());
            }
            if (distance < distanceToTarget && distance > 0) {
                distanceToTarget = distance;
                target = u;
            }
        }

        return target;
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
