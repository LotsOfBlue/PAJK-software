package pajk.game.main.java.controller;

import pajk.game.main.java.model.Model;

/**
 * Created by palm on 2016-04-18.
 */
public class Controller {
    private Model model;
    public Controller(Model model){
        this.model = model;
    }

    public void upInput(){
        model.performAction(Model.actionName.UP);
        System.out.println("up");
    }
    public void leftInput(){
        model.performAction(Model.actionName.LEFT);
        System.out.println("left");
    }
    public void rightInput(){
        model.performAction(Model.actionName.RIGHT);
        System.out.println("right");
    }
    public void downInput(){
        model.performAction(Model.actionName.DOWN);
        System.out.println("down");
    }
    public void enterInput(){
        model.performAction(Model.actionName.ENTER);
    }
    public void backInput(){
        model.performAction(Model.actionName.BACK);
    }

}
