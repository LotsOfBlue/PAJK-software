package game.main.java.model.states;

import game.main.java.ActionName;
import game.main.java.model.Board;
import game.main.java.model.GameModel;
import game.main.java.model.units.Axeman;
import game.main.java.model.units.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jonatan on 20/05/2016.
 */
public class CombatInfoStateTest {
    private GameModel gameModel;

    @Before
    public void setUp() throws Exception {
        Unit activeUnit = new Axeman(Unit.Allegiance.PLAYER, 1);
        Unit targetUnit = new Axeman(Unit.Allegiance.AI, 1);
        gameModel = GameModel.getInstance();
        Board board = new Board("testAssets/Scenarios/testboard.txt");
        gameModel.setBoard(board);
        gameModel.setActiveUnit(activeUnit);
        gameModel.setTargetUnit(targetUnit);
        board.getTile(0, 0).setUnit(activeUnit);
        board.getTile(0, 1).setUnit(targetUnit);
    }


    @Test
    public void enterAction() throws Exception {
        gameModel.setState(GameModel.StateName.COMBAT_INFO);
        gameModel.performAction(ActionName.ENTER);
        assertTrue("Doesn\'t switch to combat state correctly!" , gameModel.getState().getName() == GameModel.StateName.COMBAT);
    }

    @Test
    public void backAction() throws Exception {
        gameModel.setState(GameModel.StateName.COMBAT_INFO);
        gameModel.performAction(ActionName.BACK);
        assertTrue("Doesn\'t switch to main state correctly!" , gameModel.getState().getName() == GameModel.StateName.CHOOSE_TARGET);
    }

    @Test
    public void activate() throws Exception {
        gameModel.setState(GameModel.StateName.COMBAT_INFO);
        CombatInfoState cIS = (CombatInfoState)gameModel.getState();
        assertTrue("Doesn\'t activate properly!",
                (!(cIS.getActiveUnit() == null)
                && !(cIS.getActiveDmg() < 0)
                && !(cIS.getActiveHitChance() < 0)
                && !(cIS.getActiveCritChance() < 0)
                && !(cIS.getTargetUnit() == null)
                && !(cIS.getTargetDmg() < 0)
                && !(cIS.getTargetHitChance() < 0)
                && !(cIS.getTargetCritChance() < 0)));
    }

    @Test
    public void getName() throws Exception {
        gameModel.setState(GameModel.StateName.COMBAT_INFO);
        assertTrue("Doesn't return the right name!", gameModel.getState().getName() == GameModel.StateName.COMBAT_INFO);
    }
}