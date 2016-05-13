package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

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
    private Queue<Unit> unitQueue;

    @Override
    public void performAction(ActionName action) {
        update();
    }

    private void update(){
        System.out.println(unitQueue.size() + " Unit(s) left: " + unitQueue); //TODO remove
        Unit currentUnit = unitQueue.poll();
        Unit target = designateTarget(currentUnit);
        //TODO go beat up target

        //End the turn if all units have acted
        if (unitQueue.peek() == null) {
            gameModel.newTurn();
            System.out.println("--PLAYER TURN--"); //TODO debug
            gameModel.setState(GameModel.StateName.MAIN_STATE);
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
        if (reachableTargets.size() > 0) {
            target = findWeakestTarget(active, reachableTargets);
        }
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
        System.out.println("The optimal target is " + target);
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
        gameModel.newTurn();
        unitQueue = new LinkedList<>();

        //Add all the units to the queue to act.
        for (Unit u : gameModel.getUnitList()) {
            if (u.getAllegiance().equals(Unit.Allegiance.AI)) {
                unitQueue.add(u);
            }
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
