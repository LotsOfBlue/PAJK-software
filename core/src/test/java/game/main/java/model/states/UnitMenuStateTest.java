package game.main.java.model.states;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import game.main.java.model.Board;
import game.main.java.model.GameModel;
import game.main.java.model.units.Axeman;
import game.main.java.model.units.Swordsman;
import game.main.java.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palm on 2016-05-20.
 */
public class UnitMenuStateTest {
    private GameModel gameModel;
    private UnitMenuState state;
    @Before
    public void setUp() throws Exception {
        gameModel = GameModel.getInstance();
        List<Unit> unitList = new ArrayList<>();
        Unit swordsman = new Swordsman(Unit.Allegiance.PLAYER,5);
        Unit axeman = new Axeman(Unit.Allegiance.PLAYER,5);
        unitList.add(swordsman);
        unitList.add(axeman);
        gameModel.setActiveUnit(axeman);
        gameModel.setUnitList(unitList);
        gameModel.setBoard(new Board("testAssets/map1.txt"));
        gameModel.setState(GameModel.StateName.UNIT_MENU);
        gameModel.getBoard().getTile(0,0).setUnit(swordsman);
        gameModel.getBoard().getTile(0,1).setUnit(axeman);
        state = (UnitMenuState)gameModel.getState();
    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void upAction() throws Exception {
        state.upAction();
        int itemSelectedAfter = state.getMenuItemSelected();
        assertTrue(itemSelectedAfter == state.getMenuList().size()-1);
    }

    @Test
    public void downAction() throws Exception {
        int itemSelectedBefore = state.getMenuItemSelected();
        state.downAction();
        int itemSelectedAfter = state.getMenuItemSelected();
        System.out.println(state.getMenuList());

        assertTrue(itemSelectedAfter == itemSelectedBefore +1);
    }

    @Test
    public void enterAction() throws Exception {

        state.enterAction();
        assertNotEquals(gameModel.getState(),state);
    }

    @Test
    public void backAction() throws Exception {
        state.backAction();
        assertNotEquals(gameModel.getState(),state);
    }

}