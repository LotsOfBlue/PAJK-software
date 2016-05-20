package pajk.game.main.java.model.states;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.scenarios.BanditScenario;
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

    public MainMenuState(){
        scenarioList.add(new BanditScenario());
        scenarioList.add(new MountainScenario());
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
        System.out.println(scenarioList.get(menuItemSelected).getName());
        Board board = scenarioList.get(menuItemSelected).makeBoard();
        gameModel.resetNumberOfTurns();
        gameModel.setBoard(board);
        gameModel.setUnitList(scenarioList.get(menuItemSelected).makeUnitList(board));
        gameModel.setState(GameModel.StateName.MAIN);
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
