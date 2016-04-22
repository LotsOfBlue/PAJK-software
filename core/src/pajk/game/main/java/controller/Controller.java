package pajk.game.main.java.controller;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.StateManager;

/**
 * Created by palm on 2016-04-18.
 */
public class Controller {
    private StateManager stateManager;
    public Controller(StateManager stateManager){
        this.stateManager = stateManager;
    }

    public void upInput(){
        stateManager.performAction(ActionName.UP);
        System.out.println("up");
    }
    public void leftInput(){
        stateManager.performAction(ActionName.LEFT);
        System.out.println("left");
    }
    public void rightInput(){
        stateManager.performAction(ActionName.RIGHT);
        System.out.println("right");
    }
    public void downInput(){
        stateManager.performAction(ActionName.DOWN);
        System.out.println("down");
    }
    public void enterInput(){
        stateManager.performAction(ActionName.ENTER);
    }
    public void backInput(){
        stateManager.performAction(ActionName.BACK);
    }

}
