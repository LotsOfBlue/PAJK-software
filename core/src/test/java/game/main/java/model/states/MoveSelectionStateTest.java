package game.main.java.model.states;

import org.junit.Before;
import org.junit.Test;
import game.main.java.model.Board;
import game.main.java.model.GameModel;
import game.main.java.model.Tile;
import game.main.java.model.units.Axeman;
import game.main.java.model.units.Swordsman;
import game.main.java.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palm on 2016-05-20.
 */
public class MoveSelectionStateTest {

    private GameModel gameModel;
    private MoveSelectionState state;

    @Before
    public void setUp() throws Exception{       //TODO remove getMenuState
        gameModel = GameModel.getInstance();
        List<Unit> unitList = new ArrayList<>();
        Unit swordsman = new Swordsman(Unit.Allegiance.PLAYER,5);
        Unit axeman = new Axeman(Unit.Allegiance.PLAYER,5);
        unitList.add(swordsman);
        unitList.add(axeman);
        gameModel.setActiveUnit(axeman);
        gameModel.setUnitList(unitList);

        gameModel.setBoard(new Board("testAssets/map1.txt"));
        gameModel.getBoard().getTile(0,0).setUnit(swordsman);
        gameModel.getBoard().getTile(0,1).setUnit(axeman);
        gameModel.setState(GameModel.StateName.MOVE_SELECT);
        state = (MoveSelectionState) gameModel.getState();
    }

    @Test
    public void enterAction() throws Exception {
        state.rightAction();
        state.enterAction();
        assertTrue(gameModel.getActiveUnit().getUnitState() == Unit.UnitState.MOVED);

    }

    @Test
    public void backAction() throws Exception {
        state.backAction();
        assertTrue(gameModel.getState().getClass() == UnitMenuState.class);
    }

    @Test
    public void upAction() throws Exception {

        gameModel.getBoard().setCursor(2,2);
        int cursorTileY = gameModel.getBoard().getCursorTile().getY();
        state.upAction();
        assertEquals(cursorTileY,gameModel.getBoard().getCursorTile().getY()+1);
    }

    @Test
    public void downAction() throws Exception {

        gameModel.getBoard().setCursor(2,2);
        int cursorTileY = gameModel.getBoard().getCursorTile().getY();
        state.downAction();
        assertEquals(cursorTileY,gameModel.getBoard().getCursorTile().getY()-1);
    }

    @Test
    public void leftAction() throws Exception {
        gameModel.getBoard().setCursor(2,2);
        int cursorTileX = gameModel.getBoard().getCursorTile().getX();
        state.leftAction();
        assertEquals(cursorTileX,gameModel.getBoard().getCursorTile().getX()+1);
    }

    @Test
    public void rightAction() throws Exception {
        gameModel.getBoard().setCursor(2,2);
        int cursorTileX = gameModel.getBoard().getCursorTile().getX();
        state.rightAction();
        assertEquals(cursorTileX,gameModel.getBoard().getCursorTile().getX()-1);
    }

}