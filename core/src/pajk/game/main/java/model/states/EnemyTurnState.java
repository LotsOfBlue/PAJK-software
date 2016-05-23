package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.items.Weapon;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.utils.CombatCalculator;
import pajk.game.main.java.model.utils.PathFinder;

import java.util.*;

/**
 * This state is active during the enemy turn.
 * It performs the appropriate action(s) for each enemy unit and
 * then returns control back to the player.
 *
 * @author Johan
 */
public class EnemyTurnState extends State {
    private GameModel gameModel;
    private Board board;
    private List<Unit> aiList;
    private List<Unit> unitList;

    private Unit activeUnit;
    private int stepsLeft;
    private Set<Tile> moveRange;

    private Unit target;
    private List<Tile> path;

    private int cooldown = 0;
    private int metaCooldown = 50;
    private int bannerCooldown = 60;

    private boolean bannerActive = false;

    @Override
    public void performAction(ActionName action) {
        update();
    }

    private void update(){

        //If the current unit is done, get a new one.
        if (activeUnit == null || activeUnit.getUnitState().equals(Unit.UnitState.DONE)){
            setNewActiveUnit();
            if (isAllDone()){
                endTurn();
                return;
            } else {
                board.setCursor(board.getPos(activeUnit));
                metaCooldown = 50;
            }
        }

        if (metaCooldown != 0){
            metaCooldown --;
            return;
        }

        //Get the unit's position for this update frame
        Tile currentPos = board.getPos(activeUnit);

        //Set a new target and path if needed
        if (target == null) {
            target = designateTarget();
            path = getPathTo(target);
            if (activeUnit.isDefender() && PathFinder.getPathLength(path, activeUnit) > stepsLeft + currentPos.getMovementCost(activeUnit.getMovementType())){
                activeUnit.setUnitState(Unit.UnitState.DONE);
                return;
            }
            trimPath(path, currentPos);
        }

        //If there is no path, the unit doesn't need to move
        if (path.isEmpty()) {
            activeUnit.setUnitState(Unit.UnitState.MOVED);
        }

        //If the unit is finished moving...
        if (activeUnit.getUnitState().equals(Unit.UnitState.MOVED)){
            //Fight with target if able
            activeUnit.setUnitState(Unit.UnitState.DONE);
            if (getAttackPoints(target).contains(currentPos)) {
                gameModel.setActiveUnit(activeUnit);
                gameModel.setTargetUnit(target);
                gameModel.setState(GameModel.StateName.COMBAT);
            }
        }
        //If not done moving, keep doing so
        else {
            moveTowards(path);
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

        while (path.size() > 0 && !moveRange.contains(path.get(0))){
            path.remove(0);
        }
    }

    private void endTurn(){
        if (bannerCooldown == 0) {
            bannerCooldown = 60;
            bannerActive = false;
            if (getAllTargets().size() > 0){
                board.setCursor(board.getPos(getAllTargets().get(0)));
            }
            gameModel.newTurn();
            gameModel.setState(GameModel.StateName.MAIN);
        } else {
            bannerCooldown--;
            bannerActive = true;
        }
    }

    private boolean isAllDone(){
        return activeUnit == null || getAllTargets().size() == 0;
    }

    /**
     * Gets the shortest path from the active unit to the given target Unit.
     * @param target The Unit to move towards.
     * @return The quickest path to one of the target's attack tiles.
     */
    private List<Tile> getPathTo(Unit target) {
        List<Tile> result = null;
        Set<Tile> attackPoints = getAttackPoints(target);
        for (Tile t : attackPoints) {
            if (!t.hasUnit() || t.getUnit() == activeUnit) {
                List<Tile> path = PathFinder.getQuickestPath(board, board.getPos(activeUnit), t, activeUnit);
                if (result == null || PathFinder.getPathLength(path, activeUnit) < PathFinder.getPathLength(result, activeUnit)) {
                    result = path;
                }
            }
        }
        if (result == null){
            result = PathFinder.getQuickestPath(board, board.getPos(activeUnit), board.getPos(target), activeUnit);
        }

        return result;
    }

    /**
     * Makes the given active unit move towards a tile from which it can
     * attack the designated target, as far as its move range allows.
     */
    private void moveTowards(List<Tile> path) {
        Tile end = path.get(0);

        if (cooldown <= 0) {
            //Set cursor to direct player's attention
            board.setCursor(end.getX(), end.getY());
            //Move as far along the path as possible
            board.moveAlongPath(path, activeUnit);
            stepsLeft--;
            cooldown = 8;
        } else {
            cooldown--;
        }
    }

    /**
     * Find the most optimal target for the current unit.
     * If there are more than one target in range, the weakest one of them is selected.
     * Otherwise, the closest unit is chosen.
     * @return The most optimal target.
     */
    private Unit designateTarget() {
        List<Unit> allTargets = getAllTargets();
        List<Unit> reachableTargets = findReachableTargets(allTargets);
        Unit target;
        //If there are targets in range, go for the weakest one
        if (reachableTargets.size() > 0) {
            target = findWeakestTarget(reachableTargets);
        }
        //Otherwise just go for the easiest to reach
        else {
            target = findClosest(allTargets);
        }
        return target;
    }

    /**
     * Find the target that the given unit will deal the most damage to.
     * @param targets The pool of targets to choose from.
     * @return The Unit that the active unit will deal the most damage to.
     */
    private Unit findWeakestTarget (List<Unit> targets) {
        int highestDmg = 0;
        Unit target = null;
        for (Unit u : targets) {
            int dmg = CombatCalculator.calcDamageThisToThat(activeUnit, u);
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
     * @param targets The pool of targets to check.
     * @return A List of all units that can be attacked by the active unit.
     */
    private List<Unit> findReachableTargets(List<Unit> targets) {
        List<Unit> result = new ArrayList<>();
        for (Unit u : targets) {
            Set <Tile> attackPoints = getAttackPoints(u);
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
     * @param target The unit to get tiles around.
     * @return A Set containing all tiles that the target can be attacked from,
     * given the stats of the active unit.
     */
    private Set<Tile> getAttackPoints(Unit target) {
        Set<Tile> attackPoints;
        Weapon weapon = activeUnit.getWeapon();
        attackPoints = board.getTilesAround(
                board.getPos(target),
                weapon.getMinRange(),
                weapon.getMaxRange());

        return attackPoints;
    }

    /**
     * Find all player-controlled units on the map.
     * @return A List of all player-controlled units.
     */
    private List<Unit> getAllTargets() {
        List<Unit> result = new ArrayList<>();

        for (Unit u : unitList) {
            if (u.getAllegiance().equals(Unit.Allegiance.PLAYER)) {
                result.add(u);
            }
        }

        return result;
    }

    /**
     * Find the unit whose attack tiles are easiest
     * to move to for the currently active unit.
     * @param targets The units to choose from.
     * @return The unit among the targets that requires the least "effort" to move towards.
     */
    private Unit findClosest(List<Unit> targets) {
        Unit closest = null;
        int distance = 0;

        //Check the attack tiles of all units on the board
        for (Unit u : targets) {
            Set<Tile> attackTiles = getAttackPoints(u);
            for (Tile t : attackTiles) {
                List<Tile> path = PathFinder.getQuickestPath(board, board.getPos(activeUnit), t, activeUnit);
                int pathLength = PathFinder.getPathLength(path, activeUnit);
                //Compare the paths and store the shortest one
                if (pathLength < distance || closest == null) {
                    distance = pathLength;
                    closest = u;
                }
            }
        }
        return closest;
    }

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        unitList = gameModel.getUnitList();
        aiList = new ArrayList<>();
        //Add all AI units to the queue
        for (Unit u : unitList) {
            if (u.getAllegiance().equals(Unit.Allegiance.AI)) {
                aiList.add(u);
            }
        }

        //Prepare the first active unit
        setNewActiveUnit();

        if (isAllDone()){
            endTurn();
        }
    }

    private void setNewActiveUnit(){
        for (Unit u:
             aiList) {
            if (!u.getUnitState().equals(Unit.UnitState.DONE)){
                activeUnit = u;
                stepsLeft = u.getMovement();
                moveRange = board.getTilesWithinMoveRange(u);
                moveRange.add(board.getPos(u));
                target = null;
                return;
            }
        }
        activeUnit = null;
    }

    public boolean isPlayerTurnBannerActive(){
        return bannerActive;
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
