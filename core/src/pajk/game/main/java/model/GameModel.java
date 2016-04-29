package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by palm on 2016-04-18.
 */
public final class GameModel {

    private static GameModel ourInstance = null;

    private Board board;
    private Player player;
    private Player computerPlayer;
    private Unit activeUnit;
    private Unit targetUnit;

    //States
    private State currentState;
    private final MainState mainState;
    private final UnitMenuState unitMenuState;
    private final EnemyTurnState enemyTurnState;
    private final MoveSelectionState moveSelectionState;
    private final ChooseTargetState chooseTargetState;
    private final CombatInfoState combatInfoState;
    private final CombatState combatState;

    public void setActiveUnit(Unit activeUnit) {
        this.activeUnit = activeUnit;
    }

    public Unit getActiveUnit() {
        return activeUnit;
    }
    public Board getBoard(){
        return board;
    }

    public Unit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(Unit targetUnit) {
        this.targetUnit = targetUnit;
    }

    public enum StateName{
        MAIN_STATE,
        UNIT_MENU,
        ENEMY_TURN,
        MOVE_SELECT,
        COMBAT_INFO,
        CHOOSE_TARGET,
        COMBAT_STATE
    }

    public static GameModel getInstance(){
        if(ourInstance == null) {
            ourInstance = new GameModel();
        }
        return ourInstance;
    }

    private GameModel(){
        //Init game objects.
        board = new Board(10, 5);
        player = new Player(false);
        computerPlayer = new Player(true);

        //Initialize states
        unitMenuState = new UnitMenuState();
        mainState = new MainState(board);
        enemyTurnState = new EnemyTurnState();
        moveSelectionState = new MoveSelectionState(board);
        chooseTargetState = new ChooseTargetState(board);
        combatInfoState = new CombatInfoState();
        combatState = new CombatState(board);
        setState(StateName.MAIN_STATE);
        //Place a dummy unit on the board.
        Unit myLittleSoldier = new Unit(Unit.Allegiance.human, 4);
        myLittleSoldier.setWeapon(new Weapon(Weapon.WeaponType.Axe,2,3,1,1,90));
        player.addUnit(myLittleSoldier);
        board.placeUnit(myLittleSoldier, board.getTile(6,4));
        Unit myOtherSoldier = new Unit(Unit.Allegiance.human, 3);
        player.addUnit(myOtherSoldier);
        board.placeUnit(myOtherSoldier, board.getTile(5, 3));
        //Create an enemy
        Unit theBigBad = new Unit(Unit.Allegiance.ai, 5);
        computerPlayer.addUnit(theBigBad);
        board.placeUnit(theBigBad, board.getTile(2,2));
    }

    public void setState(StateName state){
        switch (state){
            case MAIN_STATE:
                currentState = mainState;
                currentState.activate();
                break;
            case UNIT_MENU:
                currentState = unitMenuState;
                currentState.activate();
                break;
            case MOVE_SELECT:
                currentState = moveSelectionState;
                currentState.activate();
                break;
            case CHOOSE_TARGET:
                currentState = chooseTargetState;
                currentState.activate();
                break;
            case COMBAT_INFO:
                currentState = combatInfoState;
                currentState.activate();
                break;
            case ENEMY_TURN:
                currentState = enemyTurnState;
                currentState.activate();
                break;
            case COMBAT_STATE:
                currentState = combatState;
                currentState.activate();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }
    
    //Delegates the keyboard press to the current state handling the logic.
    public void performAction(ActionName action){
        currentState.performAction(action);
    }
}