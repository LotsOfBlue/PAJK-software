package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-16.
 */
public class MainMenuState implements State{
    private List<Scenario> scenarioList = new ArrayList<>();
    private int menuItemSelected = 0;

    public MainMenuState(){

    }

    @Override
    public void performAction(ActionName action) {

    }

    @Override
    public void activate() {
        
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MAIN_MENU;
    }
}
