package game.main.java.controller;

import game.main.java.ActionName;
import game.main.java.model.GameModel;

/**
 * Created by palm on 2016-04-18.
 */
public class ControllerAdapter {
    private GameModel gameModel;
    public ControllerAdapter(GameModel gameModel){
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
    public void noInput(){
        gameModel.performAction(ActionName.NO_INPUT);
    }

}
