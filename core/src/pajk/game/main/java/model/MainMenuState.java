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
    private GameModel gameModel;

    public MainMenuState(){
        scenarioList.add(new Scenario("scen1map.txt", "scen1units.txt", "Ambushed on the Forest Road", "Command a small troop of soldiers" +
                " when they battle with a band of highway robbers. (Map Size 15×9)"));
        scenarioList.add(new Scenario("scen2map.txt", "scen2units.txt", "Clash of Dukes", "Command a large amount of soldiers " +
                "in a battle with an opposing Duke's forces. (Map Size 30×20"));
    }

    @Override
    public void performAction(ActionName action) {
        switch (action){
            case UP: case LEFT:
                menuItemSelected = (menuItemSelected + scenarioList.size()-1) % scenarioList.size();
                break;
            case DOWN: case RIGHT:
                menuItemSelected = (menuItemSelected + 1) % scenarioList.size();
                break;
            case ENTER:
                Board board = scenarioList.get(menuItemSelected).makeBoard();
                gameModel.setBoard(board);
                gameModel.setUnitList(scenarioList.get(menuItemSelected).makeUnitList(board));
                gameModel.setState(GameModel.StateName.MAIN);
        }
    }

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MAIN_MENU;
    }
}
