package pajk.game.main.java.model.states;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
public class StatusStateTest {

    private GameModel gameModel;
    private StatusState state;

    @Before
    public void setUp() throws Exception {
        gameModel = GameModel.getInstance();
        List<Unit> unitList = new ArrayList<>();
        Unit swordsman = new Swordsman(Unit.Allegiance.PLAYER,5);
        Unit axesman = new Axeman(Unit.Allegiance.PLAYER,5);
        unitList.add(swordsman);
        unitList.add(axesman);
        gameModel.setActiveUnit(axesman);
        gameModel.setUnitList(unitList);
        gameModel.setState(GameModel.StateName.STATUS);
        state = (StatusState)gameModel.getState();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void enterAction() throws Exception {
        state.enterAction();
        assertTrue(state.isInInfoState());
        state.enterAction();
        assertTrue(state.isInInfoState());

    }

    @Test
    public void upAction() throws Exception {

        String text1 = state.getInfoItem(3);
        state.upAction();
        String text2 = state.getInfoItem(3);
        assertNotEquals(text1,text2);

        state.enterAction();
        String text3 = state.getActiveInfoItemText();
        state.upAction();
        String text4 = state.getActiveInfoItemText();
        assertNotEquals(text3,text4);

    }

    @Test
    public void downAction() throws Exception {
        String text1 = state.getInfoItem(3);
        state.downAction();
        String text2 = state.getInfoItem(3);
        assertNotEquals(text1,text2);

        state.enterAction();
        String text3 = state.getActiveInfoItemText();
        state.downAction();
        String text4 = state.getActiveInfoItemText();
        assertNotEquals(text3,text4);
    }



    @Test
    public void backAction() throws Exception {
        state.enterAction();
        state.backAction();
        assertFalse(state.isInInfoState());
        state.backAction();
        assertNotEquals(gameModel.getState(),state);
    }


}