package pajk.game.main.java.model;

import java.awt.*;

/**
 * Created by palm on 2016-04-15.
 */
public class Tile{
    private final int x;
    private final int y;
    private Unit unit;
    private Overlay overlay = Overlay.NONE;

    public enum Overlay{
        MOVEMENT, TARGET, NONE
    }

    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public boolean hasUnit(){
        return unit != null;
    }

    public int getY(){
        return y;
    }

    Unit getUnit() {
        return unit;
    }

    void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public void setOverlay(Overlay overlay) {
        this.overlay = overlay;
    }

    public String toString(){
        return "Tile at " +x+" "+y;
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
