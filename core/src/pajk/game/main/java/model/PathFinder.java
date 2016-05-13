package pajk.game.main.java.model;

import java.util.*;

/**
 * This class has some utility functions for pathfinding.
 *
 * Created by Gustav on 2016-05-10.
 */
public abstract class PathFinder {

    /**
     * Returns the distance between two tiles.
     * @param start One of the tiles
     * @param goal The other tile
     * @return the distance between the two tiles start and goal.
     */

    public static double estimateDistance(Tile start, Tile goal){
        int dx = Math.abs(start.getX() - goal.getX());
        int dy = Math.abs(start.getY() - goal.getY());
        return dx + dy;
    }

    /**
     * A function returning the quickest path from tile start to tile goal, using the algorithm "A*", aka "A Star".
     * @param start
     * @param goal
     * @param unit
     * @return
     */
    public static List<Tile> getQuickestPath(Board board, Tile start, Tile goal, Unit unit){
        //A set containing the tiles to be searched.
        Set<Tile> open = new HashSet<>();
        //A set containing the tiles that have been searched.
        Set<Tile> closed = new HashSet<>();

        //Initialize the start node.
        //G is the cost to move to a tile from the starting tile.
        start.setPathG(0);
        //H is the distance from the node to the goal node, see the getDistance function.
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

            System.out.println(current.getX() + " " + current.getY());

            //This tile has been searched, remove it from the open set
            open.remove(current);
            closed.add(current);

            //Now add the neighboring nodes to the open set.
            for (Tile t : board.getNeighbors(current)) {
                //Calculate the cost to move to the next tile by adding the cost to move here with the cost to move
                //one step to the neighbor
                double nextGValue;
                //If the cost to move to the next square is greater than the unit can move even with full amount
                //of movement points left, then we can't take that path. Also don't path through enemy units.
                if ((t.hasUnit() && t.getUnit().getAllegiance() != unit.getAllegiance()) || (t.getMovementCost(unit.getMovementType()) > unit.getMovement())){
                    nextGValue = current.getPathG() + 10000;
                } else {
                    nextGValue = current.getPathG() + t.getMovementCost(unit.getMovementType());
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
        while (current != start){
            path.add(current);
            current = current.getPathParent();
        }
        //Add the last tile not added by the while loop.
        path.add(start);

        return path;
    }
}
