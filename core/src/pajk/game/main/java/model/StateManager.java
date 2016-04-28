package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by palm on 2016-04-18.
 */
public final class StateManager {

    private static StateManager ourInstance = null;

    private State currentState;
    private Board board;
    private Player player;
    private Player computer;
    private Unit activeUnit;

    //States
    private final MainState mainState;
    private final UnitMenuState unitMenuState;
    private final ChooseTileState chooseTileState;
    private final CombatInfoState combatInfoState;


    public void setActiveUnit(Unit activeUnit) {
        this.activeUnit = activeUnit;
    }

    public Unit getActiveUnit() {
        return activeUnit;
    }
    public Board getBoard(){
        return board;
    }

    public enum StateName{
        MAIN_STATE,
        UNIT_MENU,
        CHOOSE_TILE,
        COMBAT_INFO;
    }

    public static StateManager getInstance(){
        if(ourInstance == null) {
            ourInstance = new StateManager();
        }
        return ourInstance;
    }

    private StateManager(){
        //Initialize game objects.
        board = new Board(10, 5);
        player = new Player(false);
        computer = new Player(true);

        //Initialize states
        unitMenuState = new UnitMenuState();
        mainState = new MainState(board);
        chooseTileState = new ChooseTileState(board);
        combatInfoState = new CombatInfoState();

        //Place a dummy unit on the board.
        Unit myLittleSoldier = new Unit(Unit.Allegiance.human, 4);
        player.addUnit(myLittleSoldier);
        board.placeUnit(myLittleSoldier, board.getTile(6,4));

        //Set starting state
        setState(StateName.MAIN_STATE);
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
            case CHOOSE_TILE:
                currentState = chooseTileState;
                currentState.activate();
                break;
            case COMBAT_INFO:
                currentState = combatInfoState;
                currentState.activate();
                break;
        }
    }

    public void performAction(ActionName action){
        currentState.performAction(action);
    }
}