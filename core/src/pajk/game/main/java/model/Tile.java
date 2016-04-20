package pajk.game.main.java.model;

/**
 * Created by palm on 2016-04-15.
 */
class Tile {
    private final int x;
    private final int y;
    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "Tile at " +x +" "+y;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
}
