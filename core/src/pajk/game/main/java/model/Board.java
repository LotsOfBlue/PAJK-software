package pajk.game.main.java.model;

import pajk.game.main.java.model.terrain.*;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.utils.FileReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the world, this class holds all the tiles in the game. This class also manages the moving
 * of units between tiles and the calculations of movement ranges since it knows the relations between tiles.
 *
 * Created by palm on 2016-04-15.
 */
public class Board {

    public enum Direction{
        NORTH, WEST, EAST, SOUTH
    }

    private Tile cursor;
    private Tile[][] tileMatrix;
    private Unit unitHolder;

    /**
     * Creates a board from the given map file.
     * @param fileName The name of the file containing the information needed to create a map.
     */
    public Board(String fileName) {
        initMatrix(fileName);
        cursor = tileMatrix[0][0];
    }

    /**
     * Fills the board's tile matrix with tiles by reading a map file.
     * Map files are structured in this way:
     * Row one contains the width of the map.
     * Row two contains the height of the map.
     * Then follows an amount of rows equal to the height of the map.
     * Each of those rows contain a number of chars equal to the width of the map.
     * Each of those chars specify what tile type should be present there.
     * 0 = plains, 1= forest, 2 = mountain, 3=river.
     * @param fileName The name of the map file to be read from.
     */
    private void initMatrix(String fileName){
        List<String> lines = FileReader.readFile(fileName);
        int width = 0;
        int height = 0;
        if (lines != null) {
            width = Integer.parseInt(lines.get(0));
            height = Integer.parseInt(lines.get(1));
        }
        tileMatrix = new Tile[width][height];
        for (int y = 0; y < height; y++){
            char[] row = lines.get(y + 2).toCharArray();
            for(int x = 0; x < width; x++){
                Terrain terrain = new Plains();
                switch (row[x]){
                    case '0':
                        terrain = new Plains();
                        break;
                    case '1':
                        terrain = new Forest();
                        break;
                    case '2':
                        terrain = new Mountain();
                        break;
                    case '3':
                        terrain = new River();
                        break;
                    case 'f':
                        terrain = new Floor();
                        break;
                    case 'w':
                        terrain = new Wall();
                        break;
                }
                tileMatrix[x][y] = new Tile(x,y, terrain);
            }
        }
    }

    /**
     * Get the tile with the given coordinates, if there is one.
     * @param x X coordinate
     * @param y Y coordinate
     * @return The tile with the given coordinates,
     * null if the coordinates are illegal
     */
    public Tile getTile(int x, int y) {
        if (isWithinBoard(x, y)) {
            return tileMatrix[x][y];
        }
        return null;
    }

    /**
     * Returns the four tiles surrounding this tile.
     * @param t The center tile.
     * @return The surrounding tiles of t.
     */
    public List<Tile> getNeighbors(Tile t){
        List<Tile> result = new ArrayList<>();
        if (isWithinBoard(t.getX(), t.getY() - 1)){
            result.add(getTile(t.getX(), t.getY() - 1));
        }
        if (isWithinBoard(t.getX(), t.getY() + 1)){
            result.add(getTile(t.getX(), t.getY() + 1));
        }
        if (isWithinBoard(t.getX() - 1, t.getY())){
            result.add(getTile(t.getX() - 1, t.getY()));
        }
        if (isWithinBoard(t.getX() + 1, t.getY())){
            result.add(getTile(t.getX() + 1, t.getY()));
        }
        return result;
    }

    /**
     * Moves a unit one step along a given path, while also holding on to units it's passing through so that
     * it can be put down again when he moves away from the square the other unit occupied. One must make
     * sure that the last step of the path is not already occupied and that you repeatedly call this function until the
     * path is empty, otherwise you might accidentally permanently remove units you pass through.
     * @param path The path to move along.
     * @param unit The unit to move.
     */
    public void moveAlongPath(List<Tile> path, Unit unit){
        if (!path.isEmpty()) {
            Tile currentTarget = path.get(path.size() - 1);
            if (unitHolder != null){
                placeUnit(unitHolder, getPos(unit));
            }
            if (currentTarget.hasUnit()){
                unitHolder = currentTarget.getUnit();
            }else{
                unitHolder = null;
            }
            moveUnit(unit, path.get(path.size() - 1));
            path.remove(path.size() - 1);
        }
    }

    /**
     * Checks whether the tile with the given coordinates exists within the board.
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if the tile has legal coordinates, False otherwise
     */
    private Boolean isWithinBoard (int x, int y) {
        return x >= 0 && x < getBoardWidth() &&
                y >= 0 && y < getBoardHeight();
    }

    /**
     * Moves the cursor in the given direction.
     * @param dir The direction to move in.
     *            Available values are NORTH, SOUTH, EAST and WEST.
     */
    public void moveCursor(Direction dir){
        switch (dir){
            case NORTH:
                moveCursor(0, -1);
                break;
            case WEST:
                moveCursor(-1, 0);
                break;
            case EAST:
                moveCursor(1, 0);
                break;
            case SOUTH:
                moveCursor(0, 1);
                break;
        }
    }

    /**
     * Checks if the cursor can be moved in the given direction
     * and moves it if that is the case.
     * @param deltaX The X coordinate of the new tile, relative to the current tile
     * @param deltaY The Y coordinate of the new tile, relative to the current tile
     */
    private void moveCursor(int deltaX, int deltaY){
        int newX = cursor.getX() + deltaX;
        int newY = cursor.getY() + deltaY;
        if (isWithinBoard(newX, newY)){
            setCursor(newX, newY);
        }
    }

    /**
     * Sets the cursor to the given coordinates.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setCursor(int x, int y) {
        cursor = tileMatrix[x][y];
    }

    /**
     * Sets the cursor position to the given tile.
     * @param t The tile to set the cursor to.
     */
    public void setCursor(Tile t) {
        cursor = t;
    }

    /**
     * @return The tile that the cursor is currently on.
     */
    public Tile getCursorTile(){
        return cursor;
    }

    /**
     * Checks every tile that can be reached from a given tile,
     * with the given movement range, and adds them to a set.
     * Units can move through allied units but not enemy units. They can still not
     * stand on the same tile as a friendly unit.
     * @param unit The unit whose movement you want to check.
     * @return A set containing every tile that the unit can reach.
     */
    public Set<Tile> getTilesWithinMoveRange(Unit unit) {
        Set<Tile> tiles = new HashSet<>();
        tiles = calculateTiles(tiles, getPos(unit), getPos(unit), unit, unit.getMovement());
        return tiles;
    }

    /**
     * Recursively checks every tile that can be reached from a given tile,
     * with the given movement range, and adds them to a set.
     * Units can move through allied units but not enemy units. They can still not
     * stand on the same tile as a friendly unit.z
     * @param tiles
     * @param origin
     * @param previous
     * @param unit
     * @param range
     * @return
     */
    private Set<Tile> calculateTiles(Set<Tile> tiles, Tile origin, Tile previous, Unit unit, int range){
        //Add the current tile to the set if it's not occupied
        if (!origin.hasUnit()){
            tiles.add(origin);
        }
        //When the unit can't move any more, the function is done
        if (range < 1){
            return tiles;
        }

        //Check if we can move to the neighbor.
        List<Tile> neighbors = getNeighbors(origin);
        for (Tile t : neighbors) {
            if (    previous != t && //Don't walk backwards
                    (!t.hasUnit() || (t.getUnit().getAllegiance() == unit.getAllegiance())) && //Don't walk through enemy units, walk through allied.
                    range >= t.getMovementCost(unit.getMovementType())) { //Make sure the unit has enough movement left to walk there.
                tiles.addAll(calculateTiles(tiles, t, origin, unit, range - t.getMovementCost(unit.getMovementType())));
            }
        }

        return tiles;
    }

    /**
     * Returns the tiles that are at at least minRange steps away from the center tile but no more than maxRange steps away.
     * Does not take tile movement costs into effect. For movement, see getTilesWithinMoveRange
     * @param center The tile you want to get the surrounding tiles of.
     * @param minRange The minimum movement range to the returned tiles.
     * @param maxRange The maximum movement range to the returned tiles.
     * @return A set containing all eligible tiles.
     */
    public Set<Tile> getTilesAround(Tile center, int minRange, int maxRange){
        Set<Tile> result = new HashSet<>();
        //I add the tiles in "rings" around the center tile, starting from i steps to the left, right, above and below,
        // then going clockwise adding tiles.
        for (int i = minRange; i <= maxRange; i++) {
            for (int j = 0; j < i; j++) {
                if (isWithinBoard(center.getX() - i + j, center.getY() - j)){
                    result.add(getTile(center.getX() - i + j, center.getY() - j));
                }
                if (isWithinBoard(center.getX()+ j, center.getY() - i + j)){
                    result.add(getTile(center.getX()+ j, center.getY() - i + j));
                }
                if (isWithinBoard(center.getX() + i - j, center.getY() + j)){
                    result.add(getTile(center.getX() + i - j, center.getY() + j));
                }
                if (isWithinBoard(center.getX() - j, center.getY() + i - j)){
                    result.add(getTile(center.getX() - j, center.getY() + i - j));
                }
            }
        }
        return result;
    }

    public int getBoardWidth(){
        return tileMatrix.length;
    }

    public int getBoardHeight(){
        return tileMatrix[0].length;
    }

    void placeUnit(Unit unit, Tile tile) {
        tile.setUnit(unit);
    }

    /**
     * Checks if there is a tile containing the specified unit. If there is, returns it.
     * @param unit The unit to check for
     * @return The Tile that the unit is standing on,
     * null if the unit can't be found.
     */
    public Tile getPos(Unit unit) {
        for (Tile[] tCol : tileMatrix) {
            for (Tile t : tCol) {
                if(t.getUnit() == unit) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * Moves the unit to the selected tile. Removes him from the tile he's currently standing on and then places him on
     * the new tile.
     * @param unit The unit to move.
     * @param dest The tile to move to.
     */
    public void moveUnit(Unit unit, Tile dest) {
        for (Tile[] arr : tileMatrix) {
            for (Tile t : arr) {
                if (t.hasUnit() && t.getUnit() == unit){
                    t.setUnit(null);
                }
            }
        }
        dest.setUnit(unit);
    }

    /**
     * Returns a String representation of the tile matrix. THe cursor is marked as an X, units as U and everything else with O.
     * @return A string representation of the board's tile matrix.
     */
    public String toString(){
        String result = "(" + cursor.getX() + ", " + cursor.getY() + ")\n";
        for (int i = 0; i < getBoardHeight(); i++) {
            for (int j = 0; j < getBoardWidth(); j++) {
                if (j == cursor.getX() && i == cursor.getY()){
                    result = result + "[X]";
                } else if (getTile(j, i).hasUnit()){
                    result = result + "[U]";
                } else{
                    result = result + "[O]";
                }

            }
            result = result + "\n";
        }
        return result;
    }
}
