package model;

/**
 * Created by palm on 2016-04-15.
 */
public class Board {

    public enum Direction{
        NORTH, WEST, EAST, SOUTH
    }

    private Tile cursor;
    private Tile[][] tileMatrix;

    public Board(int size){
        initMatrix(size);
        cursor = tileMatrix[0][0];
    }

    private void initMatrix(int size){
        tileMatrix = new Tile[size][size];
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                tileMatrix[i][j] = new Tile(i,j);
            }
        }
    }

    public Tile getTile(int x, int y){
        return tileMatrix[x][y];
    }

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

    private void moveCursor(int deltaX, int deltaY){
        if (    cursor.getX() + deltaX >= 0 &&
                cursor.getX() + deltaX < tileMatrix.length &&
                cursor.getY() + deltaY >= 0 &&
                cursor.getY() + deltaY < tileMatrix[0].length){
            cursor = tileMatrix[cursor.getX() + deltaX][cursor.getY() + deltaY];
        }
        System.out.println(this.toString());
    }

    public String toString(){
        String result = cursor.getX() + (cursor.getY() + "\n");
        for (int i = 0; i < tileMatrix.length; i++) {
            for (int j = 0; j < tileMatrix[0].length; j++) {
                if (j == cursor.getX() && i == cursor.getY()){
                    result = result + "[X]";
                } else {
                    result = result + "[O]";
                }

            }
            result = result + "\n";
        }
        return result;
    }

    public Tile getCursorTile(){
        return cursor;
    }
}
