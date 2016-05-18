package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.utils.PathFinder;

import java.util.*;

/**
 * This state is active during the enemy turn. It makes an action for each enemy unit and then returns control back to
 * the player.
 *
 * Created by Johan on 2016-04-28.
 */
public class EnemyTurnState implements State {
    private GameModel gameModel;
    private Board board;
    private List<Unit> unitList;

    @Override
    public void performAction(ActionName action) {

    }

    private void update(Unit unit){

        Tile currentPos = board.getPos(unit);
        Unit target = designateTarget(unit);

        //If the target was within range, move to and attack it
        if (findReachableTargets(unit, getAllTargets()).contains(target)) {
            moveToAttack(unit, target, currentPos);
        }
        //If not, move towards the closest attack tile
        else {
            moveTowards(unit, target, currentPos);
        }
    }

    /**
     * Makes the given active unit move towards a tile from which it can
     * attack the designated target, as far as its move range allows.
     * @param active The unit that will move.
     * @param target The unit which will be moved towards.
     * @param currentPos The active unit's position before moving.
     */
    private void moveTowards(Unit active, Unit target, Tile currentPos) {
        int shortestDistance = 10000;
        Tile targetTile = null;
        List<Tile> path;
        for (Tile t : getAttackPoints(active, target)){
            path = PathFinder.getQuickestPath(board, currentPos, t, active);
            int pathLength = PathFinder.getPathLength(path, active);
            if (pathLength < shortestDistance) {
                shortestDistance = pathLength;
                targetTile = t;
            }
        }
        path = PathFinder.getQuickestPath(board, currentPos, targetTile, active);
        //Remove the first tile of the path, since that's where the unit is standing
        path.remove(path.size()-1);
        //Move as far along the path as possible
        for (int i = active.getMovement(); i > 0; i--) {
            board.moveAlongPath(path, active);
            System.out.println("step2");
        }
        active.setUnitState(Unit.UnitState.DONE);
        activate();
    }

    /**
     * Makes the given active unit move towards a tile from which it can
     * attack the designated target, and then attacks from that tile.
     * @param active The unit that will move.
     * @param target The unit that will be attacked.
     * @param currentPos The active unit's position before moving.
     */
    private void moveToAttack(Unit active, Unit target, Tile currentPos) {
        Set<Tile> moveRange = board.getTilesWithinMoveRange(active);
        Set<Tile> attackTiles = getAttackPoints(active, target);
        for (Tile t : attackTiles) {
            if (moveRange.contains(t)) {
                List<Tile> path = PathFinder.getQuickestPath(board, currentPos, t, active);
                //Remove the first tile of the path, since that's where the unit is standing
                path.remove(path.size()-1);
                while (!path.isEmpty()) {
                    board.moveAlongPath(path, active);
                    System.out.println("step");
                }
                //Fight the target unit
                gameModel.setActiveUnit(active);
                gameModel.setTargetUnit(target);
                gameModel.setState(GameModel.StateName.COMBAT);
                break;
            }
        }
    }

    /**
     * Find the most optimal target for the current unit.
     * @param active The currently active computer-controlled unit.
     * @return The most optimal target.
     */
    private Unit designateTarget(Unit active) {
        List<Unit> allTargets = getAllTargets();
        List<Unit> reachableTargets = findReachableTargets(active, allTargets);
        Unit target = null;
        //If there are targets in range, go for the weakest one
        if (reachableTargets.size() > 0) {
            target = findWeakestTarget(active, reachableTargets);
        }
        //Otherwise just go for the easiest to reach //TODO go for weakest here too?
        else {
            int distance = 10000;
            //Check the attack tiles of all units on the board
            for (Unit u : allTargets) {
                Set<Tile> attackTiles = getAttackPoints(active, u);
                for (Tile t : attackTiles) {
                    List<Tile> path = PathFinder.getQuickestPath(board, board.getPos(active), t, active);
                    int pathLength = PathFinder.getPathLength(path, active);
                    if (pathLength < distance) {
                        distance = pathLength;
                        target = u;
                    }
                }
            }
        }
        return target;
    }

    /**
     * Find the target that the given unit will deal the most damage to.
     * @param active The attacking unit.
     * @param targets The pool of targets to choose from.
     * @return The Unit that the active unit will deal the most damage to.
     */
    private Unit findWeakestTarget (Unit active, List<Unit> targets) {
        int highestDmg = 0;
        Unit target = null;
        for (Unit u : targets) {
            CombatState cs = new CombatState();
            int dmg = cs.calcDamageThisToThat(active, u);
            //TODO consider evasion
            if (dmg >= highestDmg) {
                highestDmg = dmg;
                target = u;
            }
        }
        return target;
    }

    /**
     * Find all targets that can be attacked from within the active unit's movement range.
     * @param active The attacking unit.
     * @param targets The pool of targets to check.
     * @return A List of all units that can be attacked by the active unit.
     */
    private List<Unit> findReachableTargets(Unit active, List<Unit> targets) {
        List<Unit> result = new ArrayList<>();
        Set<Tile> moveRange = board.getTilesWithinMoveRange(active);
        for (Unit u : targets) {
            Set <Tile> attackPoints = getAttackPoints(active, u);
            for (Tile t : attackPoints) {
                if (moveRange.contains(t) && !result.contains(u)) {
                    result.add(u);
                }
            }
        }
        return result;
    }

    /**
     * Find all tiles that the active unit can hit the target unit from.
     * @param active The unit that will attack.
     * @param target The unit to get tiles around.
     * @return A Set containing all tiles that the target can be attacked from,
     * given the stats of the active unit.
     */
    private Set<Tile> getAttackPoints(Unit active, Unit target) {
        Set<Tile> attackPoints = null;
        attackPoints = board.getTilesAround(
                board.getPos(target),
                active.getWeaponMinRange(),
                active.getWeaponMaxRange());

        return attackPoints;
    }

    /**
     * Find all player-controlled units on the map.
     * @return A List of all player-controlled units.
     */
    private List<Unit> getAllTargets() {
        List<Unit> result = new ArrayList<>();

        for (Unit u : gameModel.getUnitList()) {
            if (u.getAllegiance().equals(Unit.Allegiance.PLAYER)) {
                result.add(u);
            }
        }
        System.out.println(result.size() + " targets found: " + result); //TODO debug

        return result;
    }

    /**
     * Get targets that are within movement range of the given unit.
     * @param center The Unit whose movement to check.
     * @return A List of enemies within the movement range of the unit.
     */
    private List<Unit> getTargetsWithinRange(Unit center) {
        List<Unit> result = new ArrayList<>();
        for (Tile t : board.getTilesWithinMoveRange(center)) {
            Unit u = t.getUnit();
            if (u != null && u.getAllegiance().equals(Unit.Allegiance.PLAYER)) {
                result.add(u);
            }
        }

        System.out.println("Within movement range: " + result);
        return result;
    }

    /**
     * Find the unit that is easiest to move to for the currently active unit.
     * @param active The active unit.
     * @param targets The units to choose from.
     * @return The unit among the targets that requires the least "effort" to move towards.
     */
    private Unit findClosest(Unit active, List<Unit> targets) {
        Unit closest = null;
        int distanceToTarget = 1000000;

        //Compare the targets and store the closest one
        for (Unit u : targets) {
            //Get the shortest path to the target
            Tile targetTile = board.getPos(u);
            List<Tile> path = PathFinder.getQuickestPath(board, board.getPos(active), targetTile, active);

            //If the path is shorter than the previously stored one, store it instead
            int dist = PathFinder.getPathLength(path, active);
            if (dist < distanceToTarget && dist > 0) {
                distanceToTarget = dist;
                closest = u;
            }
        }
        System.out.println("Closest enemy is " + closest);
        return closest;
    }

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        System.out.println("--ENEMY TURN--"); //TODO debug
        //gameModel.newTurn();
        unitList = gameModel.getUnitList();
        boolean done = true;
        //Add all the units to the queue to act.
        for (Unit u : unitList) {
            if ( (u.getAllegiance().equals(Unit.Allegiance.AI)) && (u.getUnitState()!= Unit.UnitState.DONE) ) {
                done = false;
                update(u);
            }
        }
        if(done) {
            gameModel.newTurn();
            System.out.println("--PLAYER TURN--"); //TODO debug
            gameModel.setState(GameModel.StateName.MAIN);
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
