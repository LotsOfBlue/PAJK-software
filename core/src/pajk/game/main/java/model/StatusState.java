package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusState extends State{
    GameModel model;



    @Override
    void enterAction(){
        //no action in this state
    }

    @Override
    void upAction(){
        //show statusscreen for other ally unit?
    }

    @Override
    void downAction(){
        //show statusscreen for other allyl unit?
    }

    @Override
    void leftAction(){
        //possibly show more information if it does not fit in screen
    }

    @Override
    void rightAction(){
        //possibly show more information if it does not fit in screen
    }

    @Override
    void backAction(){
        if(model.getActiveUnit().getAllegiance() == Unit.Allegiance.AI){
            model.setState(GameModel.StateName.MAIN);
        } else {
            model.setState(GameModel.StateName.UNIT_MENU);
        }

    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.UNIT_MENU;
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();

    }
}
