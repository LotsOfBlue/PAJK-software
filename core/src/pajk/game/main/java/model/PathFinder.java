package pajk.game.main.java.model;

import java.util.*;

/**
 * Created by Gustav on 2016-05-10.
 */
public class PathFinder {
    private Board board;

    public PathFinder(Board board){
        this.board = board;
    }

    /**
     * Returns the distance between two nodes as if all the tiles between them had a cost of 1.
     * @param start One of the tiles
     * @param goal The other tile
     * @return the distance between two nodes as if all the tiles between them had a cost of 1.
     */
    private double estimateDistance(Tile start, Tile goal){
        int dx = Math.abs(start.getX() + goal.getX());
        int dy = Math.abs(start.getY() + goal.getY());
        return dx + dy;
    }

    /**
     * A function returning the quickest path from tile start to tile goal, using the algorithm A* (or A star).
     * @param start
     * @param goal
     * @param unit
     * @return
     */
    public Set<Tile> getQuickestPath(Tile start, Tile goal, Unit unit){
        //A set containing the tiles to be searched.
        Set<Tile> open = new HashSet<Tile>();
        //A set containing the tiles that have been searched.
        Set<Tile> closed = new HashSet<Tile>();

        //Initialize the start node.
        //G is the cost to move to a tile from the starting tile.
        start.setPthG(0);
        //H is the distance from the node to the goal node, see the estimateDistance function.
        start.setPthH(estimateDistance(start, goal));
        //F is the combined value of H and G of a tile, and helps us find the best path.
        start.setPthF(start.getPthH());
        open.add(start);

        while (true){
            Tile current = null;

            //If there are no more tiles availible to search before we found the goal tile, there is no path.
            if (open.size() == 0){
                throw new RuntimeException("No path found");
            }

            //Search from the tile with the lowest F value.
            for (Tile t :
                    open) {
                if (current == null || t.getPthF() < current.getPthF()){
                    current = t;
                }
            }
            if (current == goal){
                break;
            }

            //This tile have been searched, move it.
            open.remove(current);
            closed.add(current);

            //Now add the neighboring nodes to the open set.
            for (Tile t :
                    board.getNeighbors(current)) {
                //Calculate the cost to move to the next tile by adding the cost to move here with the cost to move
                //one step to the neighbor
                double nextGValue = current.getPthG() + t.getMovementCost(unit.getMovementType());

                //If it's cheaper to move to the neighbor from this tile instead of from the way it was found previously,
                //remove it from both open and closed so that we can add it to open again further down.
                if (nextGValue < t.getPthG()){
                    open.remove(t);
                    closed.remove(t);
                }

                //TODO:Keep implementing stuff
            }
        }

        return new HashSet<>();
    }

}
