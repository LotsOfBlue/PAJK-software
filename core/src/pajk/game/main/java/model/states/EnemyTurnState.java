package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.utils.PathFinder;

import java.util.*;

/**
 * This state is active during the enemy turn. It makes an action for each enemy unit and then returns control back to
 * the player.
 *
 * Created by Johan on 2016-04-28.
 */
public class EnemyTurnState extends State {
    private GameModel gameModel;
    private Board board;
    private Queue<Unit> unitQueue;
    private Unit activeUnit;
    private int stepsLeft;
    private Unit target;
    private List<Tile> path;

    private int cooldown = 0;

    @Override
    public void performAction(ActionName action) {
        update();
    }

    private void update(){

        if (activeUnit.getUnitState().equals(Unit.UnitState.DONE)) {
            //Reset the target
            target = null;
            //If there are no units left, the player's turn begins
            if (unitQueue.peek() == null) {
                System.out.println("--PLAYER TURN--"); //TODO remove
                gameModel.newTurn();
                gameModel.setState(GameModel.StateName.MAIN);
                return;
            }
            activeUnit = unitQueue.poll();
            stepsLeft = activeUnit.getMovement();
        }

        Tile currentPos = board.getPos(activeUnit);

        if (target == null) {
            target = designateTarget(activeUnit);
            path = getPathTo(activeUnit, target);
            trimPath(path);
        }

        System.out.println(path); //TODO remove

        if (path.get(0).equals(currentPos)) {
            //Fight the target unit
            gameModel.setActiveUnit(activeUnit);
            gameModel.setTargetUnit(target);
            gameModel.setState(GameModel.StateName.COMBAT);
            activeUnit.setUnitState(Unit.UnitState.DONE);
        } else {
            moveTowards(path, activeUnit, currentPos);
        }
        if (path.isEmpty() || stepsLeft == 0) {
            activeUnit.setUnitState(Unit.UnitState.DONE);
        }
    }

    /**
     * Removes all unreachable tile in the path, as well as the
     * tile the unit is currently standing on.
     * @param path The path to trim.
     */
    private void trimPath(List<Tile> path) {
        if (path.contains(board.getPos(activeUnit))) {
            path.remove(board.getPos(activeUnit));
        }
        for (int i = path.size(); i >= 0; i--) {
            if (!board.getTilesWithinMoveRange(activeUnit).contains(path.get(0))) {
                path.remove(0);
            }
        }
    }

    /**
     * Gets the shortest path to the given target Unit.
     * @param active The Unit which will move.
     * @param target The Unit to move towards.
     * @return The quickest path to one of the target's attack tiles.
     */
    private List<Tile> getPathTo(Unit active, Unit target) {
        List<Tile> result = null;
        Set<Tile> attackPoints = getAttackPoints(active, target);
        for (Tile t : attackPoints) {
            List<Tile> path = PathFinder.getQuickestPath(board, board.getPos(active), t, active);
            if (result == null || PathFinder.getPathLength(path, active) < PathFinder.getPathLength(result, active)) {
                result = path;
            }
        }

        return result;
    }

    /**
     * Makes the given active unit move towards a tile from which it can
     * attack the designated target, as far as its move range allows.
     * @param active The unit that will move.
     * @param currentPos The active unit's position before moving.
     */
    private void moveTowards(List<Tile> path, Unit active, Tile currentPos) {
        Tile end = path.get(0);

        if (cooldown <= 0) {
            //Move as far along the path as possible
            board.setCursor(end.getX(), end.getY());
            board.moveAlongPath(path, active);
            stepsLeft--;
            cooldown = 8;
            System.out.println("step2"); //TODO remove
        } else {
            cooldown--;
        }
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
        List<Tile> shortestPath = null;
        for (Tile t : attackTiles) {
            if (moveRange.contains(t)) {
                List<Tile> path = PathFinder.getQuickestPath(board, currentPos, t, active);
                //Set cursor on the target unit to direct player's attention
                board.setCursor(t.getX(), t.getY());
                //Remove the first tile of the path, since that's where the unit is standing
                path.remove(path.size()-1);
                if (shortestPath == null || PathFinder.getPathLength(path, active) < PathFinder.getPathLength(shortestPath, active)) {
                    shortestPath = path;
                }
            }
        }

        while (!shortestPath.isEmpty()) {
            board.moveAlongPath(shortestPath, active);
            System.out.println("step");
        }
        //Fight the target unit
        gameModel.setActiveUnit(active);
        gameModel.setTargetUnit(target);
        gameModel.setState(GameModel.StateName.COMBAT);
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
        //Otherwise just go for the easiest to reach
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
            int dmg = CombatState.calcDamageThisToThat(active, u);
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
        System.out.println("--ENEMY TURN--"); //TODO debug
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        List<Unit> unitList = gameModel.getUnitList();
        unitQueue = new LinkedList<>();
        //Add all AI units to the queue
        for (Unit u : unitList) {
            if (u.getAllegiance().equals(Unit.Allegiance.AI)) {
                unitQueue.add(u);
            }
        }
        //Prepare the first active unit
        activeUnit = unitQueue.poll();
        stepsLeft = activeUnit.getMovement();
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
