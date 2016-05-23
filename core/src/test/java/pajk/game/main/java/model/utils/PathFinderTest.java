package pajk.game.main.java.model.utils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.Tile;
import pajk.game.main.java.model.terrain.*;
import pajk.game.main.java.model.units.FlyingSwordsman;
import pajk.game.main.java.model.units.RidingSwordsman;
import pajk.game.main.java.model.units.Swordsman;
import pajk.game.main.java.model.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Gustav on 2016-05-23.
 */
public class PathFinderTest {
    private Board board;
    private Unit walkingUnit;
    private Unit ridingUnit;
    private Unit flyingUnit;

    @Before
    public void setUp() throws Exception{
        board = new Board("Scenarios/PathFindTestMap.txt");
        walkingUnit = new Swordsman(Unit.Allegiance.PLAYER, 1);
        ridingUnit = new RidingSwordsman(Unit.Allegiance.PLAYER, 1);
        flyingUnit = new FlyingSwordsman(Unit.Allegiance.PLAYER, 1);
    }

    @Test
    public void estimateDistance() throws Exception {

    }

    @Test
    public void getQuickestPath() throws Exception {


        List<Tile> path;
        //Make sure we walk arounds mountains.
        path = PathFinder.getQuickestPath(board, board.getTile(0, 0), board.getTile(7, 1), walkingUnit);
        assert(PathFinder.getPathLength(path, walkingUnit) == 11);

        //Make sure the path has one tile at one end and the other at the other end.
        assert (path.get(path.size() - 1) == board.getTile(0, 0));
        assert (path.get(0) == board.getTile(7, 1));

        //Make sure the tiles are linked together.
        Tile t = path.get(0);
        while(t.getPathParent() != null){
            //Make sure we never take steps larger than one.
            assert (PathFinder.estimateDistance(t, t.getPathParent()) == 1);
            t = t.getPathParent();
        }
        assert (t == board.getTile(0, 0));

        //Make sure we ride around the forest, forests cost a lot for mounted units.
        path = PathFinder.getQuickestPath(board, board.getTile(1, 1), board.getTile(1, 5), ridingUnit);
        assert(PathFinder.getPathLength(path, ridingUnit) == 7);

        //Make sure we through the forest if we save time by doing so.
        path = PathFinder.getQuickestPath(board, board.getTile(1, 5), board.getTile(1, 7), walkingUnit);
        assert(PathFinder.getPathLength(path, walkingUnit) == 4);

        //Make sure the path to a bloacked off area costs a boatload.
        path = PathFinder.getQuickestPath(board, board.getTile(1, 1), board.getTile(13, 1), walkingUnit);
        assert(PathFinder.getPathLength(path, walkingUnit) >= 100);

        //Make sure we walk through allied units.
        board.moveUnit(walkingUnit, board.getTile(1, 0));
        path = PathFinder.getQuickestPath(board, board.getTile(0, 0), board.getTile(2, 0), walkingUnit);
        assert(PathFinder.getPathLength(path, walkingUnit) == 3);

        //Make sure we walk around enemy units.
        Unit enemyWalker = new Swordsman(Unit.Allegiance.AI, 1);
        board.moveUnit(enemyWalker, board.getTile(1, 0));
        path = PathFinder.getQuickestPath(board, board.getTile(0, 0), board.getTile(2, 0), walkingUnit);
        assert(PathFinder.getPathLength(path, walkingUnit) == 5);

    }

    @Test
    public void getPathLength() throws Exception {
        List<Tile> path = new ArrayList<>();

        assert (PathFinder.getPathLength(path, walkingUnit) == 0);

        path.add(new Tile(0,0,new Plains()));
        path.add(new Tile(0,0,new Plains()));

        assert (PathFinder.getPathLength(path, walkingUnit) == 2);
        assert (PathFinder.getPathLength(path, ridingUnit) == 2);
        assert (PathFinder.getPathLength(path, flyingUnit) == 2);

        path.add(new Tile(0,0,new Forest()));

        assert (PathFinder.getPathLength(path, walkingUnit) == 4);
        assert (PathFinder.getPathLength(path, ridingUnit) == 5);
        assert (PathFinder.getPathLength(path, flyingUnit) == 3);

        path.add(new Tile(0,0,new Mountain()));

        assert (PathFinder.getPathLength(path, walkingUnit) >= 100);
        assert (PathFinder.getPathLength(path, ridingUnit) >= 100);
        assert (PathFinder.getPathLength(path, flyingUnit) == 4);

        path.add(new Tile(0,0,new Wall()));

        assert (PathFinder.getPathLength(path, walkingUnit) >= 100);
        assert (PathFinder.getPathLength(path, ridingUnit) >= 100);
        assert (PathFinder.getPathLength(path, flyingUnit) >= 100);
    }

}