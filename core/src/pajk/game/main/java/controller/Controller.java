package pajk.game.main.java.controller;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.GameModel;

/**
 * Created by palm on 2016-04-18.
 */
public class Controller {
    private GameModel gameModel;
    public Controller(GameModel gameModel){
        this.gameModel = gameModel;
    }

    public void upInput(){
        gameModel.performAction(ActionName.UP);
    }
    public void leftInput(){
        gameModel.performAction(ActionName.LEFT);
    }
    public void rightInput(){
        gameModel.performAction(ActionName.RIGHT);
    }
    public void downInput(){
        gameModel.performAction(ActionName.DOWN);
    }
    public void enterInput(){
        gameModel.performAction(ActionName.ENTER);
    }
    public void backInput(){
        gameModel.performAction(ActionName.BACK);
    }

}
