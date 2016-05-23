package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.states.State;
import pajk.game.main.java.model.states.MainMenuState;
import pajk.game.main.java.model.states.MainState;
import pajk.game.main.java.model.states.StatusState;
import pajk.game.main.java.model.states.EndState;
import pajk.game.main.java.model.states.ChooseTargetState;
import pajk.game.main.java.model.states.CombatInfoState;
import pajk.game.main.java.model.states.CombatState;
import pajk.game.main.java.model.states.EnemyTurnState;
import pajk.game.main.java.model.states.MoveSelectionState;
import pajk.game.main.java.model.states.UnitMenuState;
import pajk.game.main.java.model.states.MoveUnitState;

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

    private Unit.Allegiance winner;
    private int numberOfTurns = 1;

    //States
    private State currentState;
    private final MainState mainState = new MainState();
    private final UnitMenuState unitMenuState = new UnitMenuState();
    private final EnemyTurnState enemyTurnState = new EnemyTurnState();
    private final MoveSelectionState moveSelectionState = new MoveSelectionState();
    private final ChooseTargetState chooseTargetState = new ChooseTargetState();
    private final CombatInfoState combatInfoState = new CombatInfoState();
    private final CombatState combatState = new CombatState();
    private final MoveUnitState moveUnitState = new MoveUnitState();
    private final StatusState statusState = new StatusState();
    private final EndState endState = new EndState();
    private final MainMenuState mainMenuState = new MainMenuState();

    public enum StateName{
        MAIN,
        UNIT_MENU,
        ENEMY_TURN,
        MOVE_SELECT,
        COMBAT_INFO,
        CHOOSE_TARGET,
        COMBAT,
        MOVE_UNIT,
        STATUS,
        END,
        MAIN_MENU
    }

    public static GameModel getInstance(){
        if(ourInstance == null) {
            ourInstance = new GameModel();
        }
        return ourInstance;
    }

    private GameModel(){
        board = new Board("plains.txt");
    }

    /**
     * Changes the current game state to another state, and also fires off its starting actions.
     * @param state The name of the state you want to swap to.
     */
    public void setState(StateName state){
        switch (state){
            case MAIN:
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
            case COMBAT:
                currentState = combatState;
                break;
            case MOVE_UNIT:
                currentState = moveUnitState;
                break;
            case STATUS:
                currentState = statusState;
                break;
            case END:
                currentState = endState;
                break;
            case MAIN_MENU:
                currentState = mainMenuState;
                break;
        }

        currentState.activate();
    }

    public Unit.Allegiance getWinner() {
        return winner;
    }

    public void setWinner(Unit.Allegiance winner) {
        this.winner = winner;
    }

    public void removeUnit (Unit unit){
        unitList.remove(unit);
        board.getPos(unit).setUnit(null);
        if(activeUnit == unit){//TODO is needed or not?
            activeUnit = null;
        } else if(targetUnit == unit) {
            targetUnit = null;
        }
    }

    public boolean isGameOver(){
        if(getNumberOfUnits(Unit.Allegiance.AI) < 1){
            winner = Unit.Allegiance.PLAYER;
            return true;
        } else if(getNumberOfUnits(Unit.Allegiance.PLAYER) < 1) {
            winner = Unit.Allegiance.AI;
            return true;
        } else {
            return false;
        }
    }

    public int getNumberOfUnits (Unit.Allegiance allegiance){
        int myInt = 0;
        for(Unit u : unitList){
            if(u.getAllegiance() == allegiance){
                myInt++;
            }
        }
        return myInt;
    }

    public State getState(){
        return currentState;
    }

    public Board getBoard(){
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public Unit getActiveUnit() {
        return activeUnit;
    }

    public List<Unit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Unit> unitList){
        this.unitList = unitList;
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
        if(result){
            numberOfTurns++;
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

    /**
     * Resets the saved stats of the model, such as number of turns, units nad
     */
    public void resetNumberOfTurns(){
        numberOfTurns = 1;
    }

    public int getNumberOfTurns(){
        return numberOfTurns;
    }
}