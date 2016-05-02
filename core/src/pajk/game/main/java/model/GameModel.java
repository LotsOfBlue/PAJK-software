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
    private Unit prevActiveUnit;
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
        board = new Board("map1.txt");
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

        //Place a dummy unit on the board.
        Unit myLittleSoldier = new Unit(Unit.Allegiance.HUMAN, 4, Unit.MovementType.WALKING);
        myLittleSoldier.setWeapon(new Weapon(Weapon.WeaponType.AXE,2,3,1,1,90));
        player.addUnit(myLittleSoldier);
        board.placeUnit(myLittleSoldier, board.getTile(6,4));
        Unit myOtherSoldier = new Unit(Unit.Allegiance.HUMAN, 3, Unit.MovementType.WALKING);
        player.addUnit(myOtherSoldier);
        board.placeUnit(myOtherSoldier, board.getTile(5, 3));

        //Create an enemy
        Unit theBigBad = new Unit(Unit.Allegiance.AI, 5, Unit.MovementType.WALKING);
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

    public Board getBoard(){
        return board;
    }

    public Unit getActiveUnit() {
        return activeUnit;
    }

    /**
     * Sets a new active unit and stores the old one as prevActiveUnit.
     * @param activeUnit The new active unit.
     */
    public void setActiveUnit(Unit activeUnit) {
        this.prevActiveUnit = this.activeUnit;
        this.activeUnit = activeUnit;
    }

    public Unit getPrevActiveUnit() {
        return prevActiveUnit;
    }

    public Unit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(Unit targetUnit) {
        this.targetUnit = targetUnit;
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