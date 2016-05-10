package pajk.game.main.java.model;

import java.util.*;

/**
 * Created by Gustav on 2016-05-10.
 */
public class PathFinder {
    private Board board;
    SortedSet<Tile> open;
    SortedSet<Tile> closed;

    public PathFinder(Board board){
        this.board = board;
    }

    /**
     * Returns the distance between two nodes as if all the tiles between them had a cost of 1.
     * @param start One of the tiles
     * @param goal The other tile
     * @return the distance between two nodes as if all the tiles between them had a cost of 1.
     */
    private double heuristic(Tile start, Tile goal){
        int dx = Math.abs(start.getX() + goal.getX());
        int dy = Math.abs(start.getY() + goal.getY());
        return dx + dy;
    }

    public Set<Tile> getQuickestPath(Tile start, Tile goal, Unit unit){
        Comparator<Tile> comp = (o1, o2) -> {
            double cost1 = o1.getPthPrio();
            double cost2 = o2.getPthPrio();
            if (cost1 > cost2){
                return 1;
            } else if (cost2 > cost1){
                return -1;
            } else {
                return 0;
            }
        };
        open = new TreeSet<>(comp);
        closed = new TreeSet<>(comp);
        open.add(start);
        while (open.first() != goal){
            Tile current = open.first();
            open.remove(current);
            closed.add(current);

            Tile northTile = board.getTile(current.getX(), current.getY() - 1);
            if (northTile != null){
                double cost = current.getPthCost() + northTile.getMovementCost(unit.getMovementType());
                if (open.contains(northTile) && cost < northTile.getPthCost()){
                    open.remove(northTile);
                }
                if (closed.contains(northTile) && cost < northTile.getPthCost()){
                    closed.remove(northTile);
                }
                if (!closed.contains(northTile) && !open.contains(northTile)){
                    northTile.setPthCost(cost);
                    open.add(northTile);
                    northTile.setPthPrio(northTile.getPthCost() + heuristic(northTile, goal));
                    northTile.setPthParent(current);
                }
            }

        }
        return new HashSet<>();
    }

}
