package pajk.game.main.java.model.states;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.scenarios.BanditScenario;
import pajk.game.main.java.model.scenarios.JailBreakScenario;
import pajk.game.main.java.model.scenarios.MountainScenario;
import pajk.game.main.java.model.scenarios.Scenario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-16.
 */
public class MainMenuState extends State {
    private List<Scenario> scenarioList = new ArrayList<>();
    private int menuItemSelected = 0;
    private GameModel gameModel;
    private Boolean showTitle;

    public MainMenuState(){
        scenarioList.add(new BanditScenario());
        scenarioList.add(new MountainScenario());
        scenarioList.add(new JailBreakScenario());
    }

    @Override
    void upAction(){
        menuItemSelected = (menuItemSelected + scenarioList.size()-1) % scenarioList.size();
    }

    @Override
    void downAction(){
        menuItemSelected = (menuItemSelected + 1) % scenarioList.size();
    }

    @Override
    void leftAction(){
        upAction();
    }

    @Override
    void rightAction(){
        downAction();
    }

    @Override
    void enterAction(){
        if (!showTitle) {
            Board board = scenarioList.get(menuItemSelected).makeBoard();
            gameModel.resetNumberOfTurns();
            gameModel.setBoard(board);
            gameModel.setUnitList(scenarioList.get(menuItemSelected).makeUnitList(board));
            gameModel.setState(GameModel.StateName.MAIN);
        } else {
            showTitle = false;
        }
    }

    @Override
    void backAction() {
        if (!showTitle) {
            showTitle = true;
        }
    }

    public List<Scenario> getScenarioList() {
        return scenarioList;
    }

    public int getMenuItemSelected() {
        return menuItemSelected;
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    @Override
    public void activate() {
        showTitle = true;
        gameModel = GameModel.getInstance();
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MAIN_MENU;
    }
}
