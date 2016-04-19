package pajk.game.main.java.model;

/**
 * Created by palm on 2016-04-15.
 */
public class Tile {
    private final int x;
    private final int y;
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "Tile at " +x +" "+y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
