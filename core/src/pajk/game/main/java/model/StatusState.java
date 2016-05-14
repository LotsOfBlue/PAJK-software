package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusState implements State{
    GameModel model;

    @Override
    public void performAction(ActionName action) {
        switch(action) {
            case UP:
                upAction();
                break;
            case DOWN:
                downAction();
                break;
            case LEFT:
                leftAction();
                break;
            case RIGHT:
                rightAction();
                break;
            case ENTER:
                enterAction();
                break;
            case BACK:
                goBack();
                System.out.println("Closed status screen."); //TODO debug
                break;
        }
    }

    private void enterAction(){
        //no action in this state
    }

    private void upAction(){
        //show statusscreen for other ally unit?
    }

    private void downAction(){
        //show statusscreen for other allyl unit?
    }

    private void leftAction(){
        //possibly show more information if it does not fit in screen
    }

    private void rightAction(){
        //possibly show more information if it does not fit in screen
    }

    private void goBack(){
        if(model.getActiveUnit().getAllegiance() == Unit.Allegiance.AI){
            model.setState(GameModel.StateName.MAIN_STATE);
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
