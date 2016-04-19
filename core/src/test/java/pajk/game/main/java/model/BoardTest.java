package pajk.game.main.java.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by palm on 2016-04-18.
 */
public class BoardTest {


    @Test
    public void testGetTile() throws Exception {
        Board board = new Board(10);
        Tile tile = board.getTile(4,2);
        assertTrue(tile.toString().equals("Tile at 4 2"));
    }

    @Test
    public void testMoveCursor() throws Exception {
        Board board = new Board(9);
        assertTrue(board.getCursorTile().getX() == 0 && board.getCursorTile().getY() == 0);
        board.moveCursor(Board.Direction.EAST);
        board.moveCursor(Board.Direction.EAST);
        board.moveCursor(Board.Direction.EAST);
        board.moveCursor(Board.Direction.SOUTH);
        board.moveCursor(Board.Direction.SOUTH);
        board.moveCursor(Board.Direction.WEST);
        board.moveCursor(Board.Direction.NORTH);
        assertTrue(board.getCursorTile().getX() == 2 && board.getCursorTile().getY() == 1);
    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testGetCursorTile() throws Exception {
        Board board = new Board(12);
        Tile tile = board.getCursorTile();
        assertTrue(tile.getX() == 0 && tile.getY() == 0);
    }
}