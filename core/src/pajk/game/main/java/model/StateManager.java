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
    private MainState mainState;
    public enum StateName{
        MAIN_STATE;
    }

    public static StateManager getInstance(){
        return ourInstance;
    }

    private StateManager(){
        board = new Board(10, 5);
        player = new Player(false);
        computer = new Player(true);
        mainState = new MainState(board);
        setState(mainState);
        Unit myLittleSoldier = new Unit(Unit.Allegiance.human);
        player.addUnit(myLittleSoldier);
        System.out.println(board.toString());
    }

    public void setState(State state){
        currentState = state;
    }

    public void performAction(ActionName action){
        currentState.performAction(action);
    }
}