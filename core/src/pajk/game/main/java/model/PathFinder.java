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
        int dx = Math.abs(start.getX() - goal.getX());
        int dy = Math.abs(start.getY() - goal.getY());
        return dx + dy;
    }

    /**
     * A function returning the quickest path from tile start to tile goal, using the algorithm A* (or A star).
     * @param start
     * @param goal
     * @param unit
     * @return
     */
    public List<Tile> getQuickestPath(Tile start, Tile goal, Unit unit){
        //A set containing the tiles to be searched.
        Set<Tile> open = new HashSet<>();
        //A set containing the tiles that have been searched.
        Set<Tile> closed = new HashSet<>();

        //Initialize the start node.
        //G is the cost to move to a tile from the starting tile.
        start.setPathG(0);
        //H is the distance from the node to the goal node, see the estimateDistance function.
        start.setPathH(estimateDistance(start, goal));
        //F is the combined value of H and G of a tile, and helps us find the best path.
        start.setPathF(start.getPathH());
        open.add(start);

        while (true){
            Tile current = null;

            //If there are no more tiles available to search before we found the goal tile, there is no path.
            if (open.size() == 0){
                throw new RuntimeException("No path found");
            }

            //Search from the tile with the lowest F value.
            for (Tile t : open) {
                if (current == null || t.getPathF() < current.getPathF()){
                    current = t;
                }
            }
            if (current == goal){
                break;
            }

            //This tile has been searched, remove it from the open set
            open.remove(current);
            closed.add(current);

            //Now add the neighboring nodes to the open set.
            for (Tile t : board.getNeighbors(current)) {
                //Calculate the cost to move to the next tile by adding the cost to move here with the cost to move
                //one step to the neighbor
                double nextGValue;
                //If the cost to move to the next square is greater than the unit can move even with full amount
                //of movement points left, then we can't take that path.
                if (current != null && t.getMovementCost(unit.getMovementType()) <= unit.getMovement()){
                    nextGValue = current.getPathG() + t.getMovementCost(unit.getMovementType());
                } else {
                    nextGValue = 10000;
                }

                //If it's cheaper to move to the neighbor from this tile instead of from the way it was found previously,
                //remove it from both open and closed so that we can add it to open again further down.
                if (nextGValue < t.getPathG()){
                    open.remove(t);
                    closed.remove(t);
                }

                //If the neighbor has not yet been searched or been marked for searching, mark it for searching.
                if (!open.contains(t) && !closed.contains(t)){
                    //Set the values of the tile.
                    t.setPathG(nextGValue);
                    t.setPathH(estimateDistance(t, goal));
                    //The 1.005 is to make the algorithm prefer tiles closer to the goal, makes for prettier paths.
                    t.setPathF(t.getPathG() + t.getPathH() * 1.02);
                    t.setPathParent(current);
                    open.add(t);
                }
            }
        }

        //Now we construct a path by backtracking through the tile's parents.
        List<Tile> path = new ArrayList<>();
        Tile current = goal;
        while (current.getPathParent() != null){
            path.add(current);
            current = current.getPathParent();
        }
        //The while loop stops before we can add the start node since it has no parent.
        path.add(start);

        return path;
    }

}
