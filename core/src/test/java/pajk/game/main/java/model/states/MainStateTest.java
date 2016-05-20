package pajk.game.main.java.model.states;

import org.junit.Before;
import org.junit.Test;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.units.Axeman;
import pajk.game.main.java.model.units.Swordsman;
import pajk.game.main.java.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palm on 2016-05-20.
 */
public class MainStateTest {

    private GameModel gameModel;
    private MainState state;

    @Before
    public void setUp() throws Exception {
        gameModel = GameModel.getInstance();
        List<Unit> unitList = new ArrayList<>();
        Unit swordsman = new Swordsman(Unit.Allegiance.PLAYER,5);
        Unit axeman = new Axeman(Unit.Allegiance.AI,5);
        unitList.add(swordsman);
        unitList.add(axeman);
        gameModel.setActiveUnit(axeman);
        gameModel.setUnitList(unitList);
        gameModel.setBoard(new Board("map1.txt"));
        gameModel.getBoard().getTile(0,0).setUnit(swordsman);
        gameModel.getBoard().getTile(0,1).setUnit(axeman);
        gameModel.setState(GameModel.StateName.MAIN);
        state = (MainState) gameModel.getState();
    }

    @Test
    public void enterAction() throws Exception {
        state.downAction();
        assertEquals(gameModel.getState(),state);
        state.upAction();
        state.enterAction();
        assertNotEquals(gameModel.getState(),state);

    }

    //up left right is same test as MoveSelectionState

}