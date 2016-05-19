package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.*;

/**
 * This state is active during the enemy turn.
 * It performs the appropriate action(s) for each enemy unit and
 * then returns control back to the player.
 *
 * @author Johan
 */
public class EnemyTurnState implements State {
    private GameModel gameModel;
    private Board board;
    private Queue<Unit> unitQueue;
    private Unit activeUnit;
    private int stepsLeft;
    private Set<Tile> moveRange;
    private Unit target;
    private List<Tile> path;

    private int cooldown = 0;

    @Override
    public void performAction(ActionName action) {
        update();
    }

    private void update(){

        //Switch to the next unit if the current one is done
        if (activeUnit.getUnitState().equals(Unit.UnitState.DONE)) {
            //Reset the target
            target = null;
            //If there are no units left, the player's turn begins
            if (unitQueue.peek() == null) {
                System.out.println("--PLAYER TURN--"); //TODO remove
                gameModel.newTurn();
                gameModel.setState(GameModel.StateName.MAIN);
                return;
            } else {
                activeUnit = unitQueue.poll();
                stepsLeft = activeUnit.getMovement();
                moveRange = board.getTilesWithinMoveRange(activeUnit);
            }
        }

        //Get the unit's position for this update frame
        Tile currentPos = board.getPos(activeUnit);

        //Set a new target and path if needed
        if (target == null) {
            target = designateTarget(activeUnit);
            path = getPathTo(activeUnit, target);
            trimPath(path, currentPos);
        }

        //If there is no path, the unit doesn't need to move
        if (path.isEmpty()) {
            activeUnit.setUnitState(Unit.UnitState.MOVED);
        }

        //If the unit is finished moving...
        if (activeUnit.getUnitState().equals(Unit.UnitState.MOVED)){
            //Fight with target if able
            if (getAttackPoints(activeUnit, target).contains(currentPos)) {
                gameModel.setActiveUnit(activeUnit);
                gameModel.setTargetUnit(target);
                gameModel.setState(GameModel.StateName.COMBAT);
            }

            activeUnit.setUnitState(Unit.UnitState.DONE);
        }
        //If not done moving, keep doing so
        else {
            moveTowards(path, activeUnit, currentPos);
            if (stepsLeft == 0 || path.isEmpty()) {
                activeUnit.setUnitState(Unit.UnitState.MOVED);
            }
        }
    }

    /**
     * Remove all unreachable tiles in the path, as well as the
     * tile the unit is currently standing on.
     * @param path The path to trim.
     */
    private void trimPath(List<Tile> path, Tile currentPos) {
        path.remove(currentPos);

        //Starting from the farthest tile, remove tiles until only reachable remains
        while (path.size() > activeUnit.getMovement()) {
            if (!moveRange.contains(path.get(0))) {
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
            //Set cursor to direct player's attention
            board.setCursor(end.getX(), end.getY());
            //Move as far along the path as possible
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
        moveRange = board.getTilesWithinMoveRange(activeUnit);
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
