package pajk.game.main.java.model;

import pajk.game.main.java.model.terrain.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by palm on 2016-04-15.
 */
public class Board {

    enum Direction{
        NORTH, WEST, EAST, SOUTH
    }

    private Tile cursor;
    private Tile[][] tileMatrix;

    Board(String fileName) {
        initMatrix(fileName);
        //initMatrix(x, y);
        cursor = tileMatrix[0][0];
    }

    private void initMatrix(String fileName){
        List<String> lines = readMapFile(fileName);
        int width = Integer.parseInt(lines.get(0));
        int height = Integer.parseInt(lines.get(1));
        tileMatrix = new Tile[width][height];
        for (int y = 0; y < height; y++){
            char[] row = lines.get(2 + y).toCharArray();
            for(int x = 0; x < width; x++){
                Terrain terra = new Plains();
                switch (row[x]){
                    case '0':
                        terra = new Plains();
                        break;
                    case '1':
                        terra = new Forest();
                        break;
                    case '2':
                        terra = new Mountain();
                        break;
                    case '3':
                        terra = new River();
                        break;
                }
                tileMatrix[x][y] = new Tile(x,y, terra);
            }
        }
    }

    private List<String> readMapFile(String fileName){
        try {
            Path path = Paths.get(fileName);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (Exception e){
            return null;
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
        else return null;
    }

    public Tile[][] getTileMatrix(){
        return tileMatrix;
    }


    private Boolean isWithinBoard (int x, int y) {
        return x >= 0 && x < getBoardWidth() &&
                y >= 0 && y < getBoardHeight();
    }

    /**
     * Moves the cursor in the given direction.
     * @param dir The direction to move in.
     *            Available values are NORTH, SOUTH, EAST and WEST.
     */
    void moveCursor(Direction dir){
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
     * Sets the cursor to the given coordinates
     * @param x X coordinate
     * @param y Y coordinate
     */
    void setCursor(int x, int y) {
        cursor = tileMatrix[x][y];
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
     * You can move through allied units but not enemy units. You can still not
     * stand on the same tile as a friendly unit.
     * @param unit The unit whose movement you want to check.
     * @return A set containing every tile that the unit can reach.
     */
    public Set<Tile> getTilesWithinMoveRange(Unit unit) {
        Set<Tile> tiles = new HashSet<>();
        tiles = calculateTiles(tiles, getPos(unit), getPos(unit), unit, unit.getMovement());
        return tiles;
    }

    private Set<Tile> calculateTiles(Set<Tile> tiles, Tile origin, Tile previous, Unit unit, int range){
        if (!origin.hasUnit()){
            tiles.add(origin);
        }
        if (range < 1){
            return tiles;
        }

        if (isWithinBoard(origin.getX(), origin.getY() - 1)){
            Tile northTile = getTile(origin.getX(), origin.getY() - 1);
            if (    previous != northTile && //Don't walk backwards
                    (!northTile.hasUnit() || (northTile.getUnit().getAllegiance() == Unit.Allegiance.HUMAN)) && //Don't walk through enemy units, walk through allied.
                    range >= northTile.getMovementCost(unit.getMovementType())) { //Make sure he has enough movement left to walk there.
                tiles.addAll(calculateTiles(tiles, northTile, origin, unit, range - northTile.getMovementCost(unit.getMovementType())));
            }
        }
        //Repeat for the other directions.
        if (isWithinBoard(origin.getX() - 1, origin.getY())){
            Tile westTile = getTile(origin.getX() - 1, origin.getY());
            if (    previous != westTile &&
                    (!westTile.hasUnit() || (westTile.getUnit().getAllegiance() == Unit.Allegiance.HUMAN)) &&
                    range >= westTile.getMovementCost(unit.getMovementType())) {
                tiles.addAll(calculateTiles(tiles, westTile, origin, unit, range - westTile.getMovementCost(unit.getMovementType())));
            }
        }
        if (isWithinBoard(origin.getX(), origin.getY() + 1)){
            Tile southTile = getTile(origin.getX(), origin.getY() + 1);
            if (    previous != southTile &&
                    (!southTile.hasUnit() || (southTile.getUnit().getAllegiance() == Unit.Allegiance.HUMAN)) &&
                    range >= southTile.getMovementCost(unit.getMovementType())) {
                tiles.addAll(calculateTiles(tiles, southTile, origin, unit, range - southTile.getMovementCost(unit.getMovementType())));
            }
        }
        if (isWithinBoard(origin.getX() + 1, origin.getY())){
            Tile eastTile = getTile(origin.getX() + 1, origin.getY());
            if (    previous != eastTile &&
                    (!eastTile.hasUnit() || (eastTile.getUnit().getAllegiance() == Unit.Allegiance.HUMAN)) &&
                    range >= eastTile.getMovementCost(unit.getMovementType())) {
                tiles.addAll(calculateTiles(tiles, eastTile, origin, unit, range - eastTile.getMovementCost(unit.getMovementType())));
            }
        }
        return tiles;
    }

    /**
     * Returns the tiles that are at at least minRange steps away from the center tile but no more than maxRange steps away.
     * Does not take tile movement costs into effect. For movement, see getTilesWithinMoveRange
     * @param center The tile oyu want to get the surrounding tiles of.
     * @param minRange The minimum movement range to the returned tiles.
     * @param maxRange The maximum movement range to the returned tiles.
     * @return
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

    /**
     * TODO delete?
     * @param unit
     */
    void moveToCursor(Unit unit) {
        Tile old = getPos(unit);
        cursor.setUnit(unit);
        old.setUnit(null);
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
     * Checks if there is a tile containing the specified unit.
     * @param unit The unit to check for
     * @return The Tile that the unit is standing on,
     * null if the unit can't be found.
     */
    Tile getPos(Unit unit) {
        for (Tile[] tCol : tileMatrix) {
            for (Tile t : tCol) {
                if(t.getUnit() == unit) {
                    return t;
                }
            }
        }
        return null;
    }

    void moveUnit(Unit unit, Tile dest) {
        for (Tile[] arr:
             tileMatrix) {
            for (Tile t:
                 arr) {
                if (t.hasUnit() && t.getUnit() == unit){
                    t.setUnit(null);
                }
            }
        }
        dest.setUnit(unit);
    }

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
