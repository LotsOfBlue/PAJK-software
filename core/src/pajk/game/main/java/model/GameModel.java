package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.ArrayList;
import java.util.List;

/**
 * The main entry point into the game model, this class picks up the input from the controller and
 * delegates that to the current State to decide how to alter the game data based on the input.
 * This class also has references to the game board and the list of units.
 *
 * Created by palm on 2016-04-18.
 */
public final class GameModel {

    private static GameModel ourInstance = null;

    private Board board;
    private Unit activeUnit;
    private Unit targetUnit;
    private List<Unit> unitList = new ArrayList<>();
    private Tile targetTile;

    //States
    private State currentState;
    private final MainState mainState;
    private final UnitMenuState unitMenuState;
    private final EnemyTurnState enemyTurnState;
    private final MoveSelectionState moveSelectionState;
    private final ChooseTargetState chooseTargetState;
    private final CombatInfoState combatInfoState;
    private final CombatState combatState;
    private final MoveUnitState moveUnitState;
    private final StatusState statusState;
    private final EndState endState;

    public enum StateName{
        MAIN_STATE,
        UNIT_MENU,
        ENEMY_TURN,
        MOVE_SELECT,
        COMBAT_INFO,
        CHOOSE_TARGET,
        COMBAT_STATE,
        MOVE_UNIT,
        STATUS_STATE,
        END_STATE
    }

    public static GameModel getInstance(){
        if(ourInstance == null) {
            ourInstance = new GameModel();
        }
        return ourInstance;
    }

    private GameModel(){
        //Init game objects.
        board = new Board("map1.txt");

        //Initialize states
        unitMenuState = new UnitMenuState();
        mainState = new MainState();
        enemyTurnState = new EnemyTurnState();
        moveSelectionState = new MoveSelectionState();
        chooseTargetState = new ChooseTargetState();
        combatInfoState = new CombatInfoState();
        combatState = new CombatState();
        moveUnitState = new MoveUnitState();
        statusState = new StatusState();
        endState = new EndState();

        //Place a dummy unit on the board.
        Unit myLittleSoldier = new Unit(Unit.Allegiance.PLAYER, 4, Unit.MovementType.WALKING, Unit.UnitClass.BOW);
        myLittleSoldier.setWeapon(new Weapon(Weapon.WeaponType.AXE,2,3,1,1,90));
        unitList.add(myLittleSoldier);
        board.placeUnit(myLittleSoldier, board.getTile(6,4));

        Unit myOtherSoldier = new Unit(Unit.Allegiance.PLAYER, 3, Unit.MovementType.WALKING, Unit.UnitClass.SWORD);
        unitList.add(myOtherSoldier);
        board.placeUnit(myOtherSoldier, board.getTile(5, 3));

        //Create an enemy
        Unit theBigBad = new Unit(Unit.Allegiance.AI, 5, Unit.MovementType.WALKING, Unit.UnitClass.SWORD);
        unitList.add(theBigBad);
        board.placeUnit(theBigBad, board.getTile(2,2));
    }

    /**
     * Changes the current game state to another state, and also fires off its starting actions.
     * @param state The name of the state you want to swap to.
     */
    public void setState(StateName state){
        switch (state){
            case MAIN_STATE:
                currentState = mainState;
                break;
            case UNIT_MENU:
                currentState = unitMenuState;
                break;
            case MOVE_SELECT:
                currentState = moveSelectionState;
                break;
            case CHOOSE_TARGET:
                currentState = chooseTargetState;
                break;
            case COMBAT_INFO:
                currentState = combatInfoState;
                break;
            case ENEMY_TURN:
                currentState = enemyTurnState;
                break;
            case COMBAT_STATE:
                currentState = combatState;
                break;
            case MOVE_UNIT:
                currentState = moveUnitState;
                break;
            case STATUS_STATE:
                currentState = statusState;
                break;
            case END_STATE:
                currentState = endState;
                break;
        }

        currentState.activate();
    }

    public void removeUnit (Unit unit){
        unitList.remove(unit);
        board.getPos(unit).setUnit(null);
    }

    public State getState(){
        return currentState;
    }

    public Board getBoard(){
        return board;
    }

    public Unit getActiveUnit() {
        return activeUnit;
    }

    public List<Unit> getUnitList() {
        return unitList;
    }

    public StateName getCurrentStateName(){
        return currentState.getName();
    }

    public UnitMenuState getMenuState(){
        return unitMenuState;
    }

    public Tile getTargetTile() {
        return targetTile;
    }

    public void setTargetTile(Tile targetTile) {
        this.targetTile = targetTile;
    }

    /**
     * Checks whether all player-controlled units have acted this turn.
     * @return True if all units under player control have exhausted their available actions
     */
    public Boolean allUnitsDone() {
        Boolean result = true;
        for (Unit u : unitList) {
            if (u.getAllegiance().equals(Unit.Allegiance.PLAYER) && !u.getUnitState().equals(Unit.UnitState.DONE)) {
                result = false;
            }
        }
        return result;
    }

    public void setActiveUnit(Unit activeUnit) {
        if (activeUnit != this.activeUnit){
            this.activeUnit = activeUnit;
        }
    }

    public Unit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(Unit targetUnit) {
        this.targetUnit = targetUnit;
    }

    public void newTurn() {
        for (Unit u : unitList) {
            u.setUnitState(Unit.UnitState.READY);
        }
    }

    /**
     * Delegates the keyboard press to the current state handling the logic.
     * @param action The action to be performed by the current state.
     */
    public void performAction(ActionName action){
        currentState.performAction(action);
    }
}