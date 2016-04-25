package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by palm on 2016-04-18.
 */
public class StateManager {

    private static StateManager ourInstance = new StateManager();

    private State currentState;
    private Board board;
    private Player player;
    private Player computer;
    private Unit activeUnit;


    private MainState mainState;
    private UnitMenuState unitMenuState;

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
        MAIN_STATE, UNIT_MENU;
    }

    public static StateManager getInstance(){
        return ourInstance;
    }

    private StateManager(){
        //Init game objects.
        board = new Board(10, 5);
        player = new Player(false);
        computer = new Player(true);
        //Init states
        unitMenuState = new UnitMenuState(board);
        mainState = new MainState(board);

        setState(StateName.MAIN_STATE);

        //Place a dummy unit on the board.
        Unit myLittleSoldier = new Unit(Unit.Allegiance.human);
        player.addUnit(myLittleSoldier);
        board.placeUnit(myLittleSoldier, board.getTile(3,3));
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
        }
    }

    public void performAction(ActionName action){
        currentState.performAction(action);
    }
}