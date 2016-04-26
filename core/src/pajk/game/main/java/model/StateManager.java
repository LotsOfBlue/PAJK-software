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


    private MainState mainState;
    private UnitMenuState unitMenuState;
    private ChooseTileState chooseTileState;

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
        //Init game objects.
        board = new Board(10, 5);
        player = new Player(false);
        computer = new Player(true);
        //Init states
        unitMenuState = new UnitMenuState();
        mainState = new MainState(board);
        chooseTileState = new ChooseTileState(board);

        setState(StateName.MAIN_STATE);

        //Place a dummy unit on the board.
        Unit myLittleSoldier = new Unit(Unit.Allegiance.human);
        player.addUnit(myLittleSoldier);
        board.placeUnit(myLittleSoldier, board.getTile(6,4));
        board.placeUnit(myLittleSoldier, board.getTile(1,0));
        System.out.println(board.toString());
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
        }
    }

    public void performAction(ActionName action){
        currentState.performAction(action);
    }
}