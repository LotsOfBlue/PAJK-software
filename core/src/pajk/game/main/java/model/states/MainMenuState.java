package pajk.game.main.java.model.states;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
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
        scenarioList.add(new Scenario(
                "Scenarios/scen1map.txt",
                "Scenarios/scen1units.txt",
                "Ambushed on the Forest Road",
                "Command a small troop of soldiers when they battle with a band of highway robbers. (Map Size 15×9)",
                null));

        scenarioList.add(new Scenario(
                "Scenarios/scen2map.txt",
                "Scenarios/scen2units.txt",
                "Clash of Dukes",
                "Command a large amount of soldiers in a battle with an opposing Duke's forces. (Map Size 30×20",
                null));
    }

    public Boolean getTitle() {
        return showTitle;
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
            System.out.println(scenarioList.get(menuItemSelected).getName()); //TODO remove
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
