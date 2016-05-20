package pajk.game.main.java.model.states;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.units.Axeman;
import pajk.game.main.java.model.units.Unit;

import static org.junit.Assert.*;

/**
 * Created by jonatan on 20/05/2016.
 */
public class CombatInfoStateTest {
    GameModel gameModel;
    Board board;
    Unit activeUnit;
    Unit targetUnit;
    @Before
    public void setUp() throws Exception {
        activeUnit = new Axeman(Unit.Allegiance.PLAYER, 1);
        targetUnit = new Axeman(Unit.Allegiance.AI, 1);
        gameModel = gameModel.getInstance();
        //board = new Board("");
        gameModel.setActiveUnit(activeUnit);
        gameModel.setTargetUnit(targetUnit);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void enterAction() throws Exception {

    }

    @Test
    public void backAction() throws Exception {

    }

    @Test
    public void activate() throws Exception {

    }

    @Test
    public void getName() throws Exception {

    }

}