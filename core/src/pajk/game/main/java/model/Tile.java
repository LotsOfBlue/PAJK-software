package pajk.game.main.java.model;

/**
 * Created by palm on 2016-04-15.
 */
class Tile{
    private final int x;
    private final int y;
    private Unit unit;

    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    Unit getUnit() {
        return unit;
    }

    void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String toString(){
        return "Tile at " +x +" "+y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile otherTile = (Tile) obj;
            return (otherTile.getX() == this.getX() &&
                    otherTile.getY() == this.getY());
        }
        return false;
    }
}
