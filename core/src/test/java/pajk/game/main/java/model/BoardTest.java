package pajk.game.main.java.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by palm on 2016-04-18.
 */
public class BoardTest {

    private Board testBoard;
    private Unit testUnit;


    @Before
    public void setUp() throws Exception {
        //should test with other maps.
        testBoard = new Board("map1.txt");
        testUnit = new Unit(Unit.Allegiance.PLAYER, 2, Unit.MovementType.WALKING, Unit.UnitClass.BOW);
    }

    @After
    public void tearDown() throws Exception {
        testBoard = null;
        testUnit = null;
    }


    @Test
    public void setCursor() throws Exception {
        int x = 3;
        int y = 2;
        testBoard.setCursor(x,y);
        //test if correct position
        assertTrue(testBoard.getCursorTile().getX() == x && testBoard.getCursorTile().getY() == y);
        //what if setCursor(x > width)??
    }


    @Test
    public void getTile() throws Exception {

        Tile tile = testBoard.getTile(0,0);
        assertNotNull(tile);
    }

    @Test
    public void getNeighbors() throws Exception {
        List<Tile> tileList = testBoard.getNeighbors(testBoard.getTile(0,0));
        assertTrue(tileList.get(0) instanceof Tile);
        assertEquals(tileList.size(),2);
        assertEquals(tileList.get(0),testBoard.getTile(0,1));
        assertEquals(tileList.get(1),testBoard.getTile(1,0));
        assertEquals(testBoard.getNeighbors(testBoard.getTile(3,3)).size(),4);
    }

    @Test
    public void moveAlongPath() throws Exception {
        List<Tile> tileList = new ArrayList<>();
        testBoard.placeUnit(testUnit,testBoard.getTile(0,0));
        tileList.add(testBoard.getTile(0,1));
        tileList.add(testBoard.getTile(0,2));
        tileList.add(testBoard.getTile(0,3));
        tileList.add(testBoard.getTile(0,4));
        tileList.add(testBoard.getTile(1,4));
        testBoard.moveAlongPath(tileList,testUnit);
        assertEquals(testBoard.getTile(1,4).getUnit(),testUnit);

    }

    @Test
    public void moveCursor() throws Exception {
        testBoard.setCursor(0,0);
        testBoard.moveCursor(Board.Direction.EAST);
        testBoard.moveCursor(Board.Direction.EAST);
        testBoard.moveCursor(Board.Direction.NORTH);
        testBoard.moveCursor(Board.Direction.SOUTH);
        testBoard.moveCursor(Board.Direction.WEST);
        testBoard.moveCursor(Board.Direction.SOUTH);
        assertTrue(testBoard.getCursorTile().getX() == 1 && testBoard.getCursorTile().getY() == 2);


    }

    @Test
    public void getCursorTile() throws Exception {
        testBoard.setCursor(0,1);
        assertTrue(testBoard.getCursorTile() != null);
    }

    @Test
    public void getTilesWithinMoveRange() throws Exception {
        testBoard.placeUnit(testUnit,testBoard.getTile(0,0));
        Set<Tile> tileSet = testBoard.getTilesWithinMoveRange(testUnit);
        assertTrue(tileSet.size() == 4);

    }

    @Test
    public void getTilesAround() throws Exception {
        Set<Tile> tileSet = testBoard.getTilesAround(testBoard.getTile(1,1),0,2);
        assertTrue(tileSet.contains(testBoard.getTile(0,0)));
        assertTrue(tileSet.contains(testBoard.getTile(0,1)));
        assertTrue(tileSet.contains(testBoard.getTile(0,2)));
        assertTrue(tileSet.contains(testBoard.getTile(1,0)));
        assertTrue(tileSet.contains(testBoard.getTile(1,2)));
        assertTrue(tileSet.contains(testBoard.getTile(1,3)));
        assertTrue(tileSet.contains(testBoard.getTile(2,0)));
        assertTrue(tileSet.contains(testBoard.getTile(2,1)));
        assertTrue(tileSet.contains(testBoard.getTile(2,2)));
        assertTrue(tileSet.contains(testBoard.getTile(3,1)));

    }

    @Test
    public void placeUnit() throws Exception {
        testBoard.placeUnit(testUnit,testBoard.getTile(0,0));
        assertTrue(testBoard.getTile(0,0).hasUnit());
    }

    @Test
    public void getPos() throws Exception {
        testBoard.placeUnit(testUnit,testBoard.getTile(2,1));
        assertTrue(testBoard.getPos(testUnit).getX()==2);
        assertTrue(testBoard.getPos(testUnit).getY()==1);
    }

    @Test
    public void moveUnit() throws Exception {
        testBoard.placeUnit(testUnit,testBoard.getTile(3,1));
        testBoard.moveUnit(testUnit,testBoard.getTile(3,3));
        assertTrue(testBoard.getTile(3,3).hasUnit());
        assertFalse(testBoard.getTile(3,1).hasUnit());

    }

}
