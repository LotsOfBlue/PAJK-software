package pajk.game.main.java.model;

import pajk.game.main.java.model.terrain.Terrain;

/**
 * Created by palm on 2016-04-15.
 */
public class Tile{
    private final int x;
    private final int y;
    private final Terrain terrain;
    private Unit unit;
    private Overlay overlay = Overlay.NONE;

    private double pthCost = 0;
    private Tile pthParent = null;
    private double pthPrio = 0;

    public Tile getPthParent() {
        return pthParent;
    }

    public void setPthParent(Tile pthParent) {
        this.pthParent = pthParent;
    }

    public double getPthCost() {
        return pthCost;
    }

    public void setPthCost(double pthCost) {
        this.pthCost = pthCost;
    }

    public double getPthPrio() {
        return pthPrio;
    }

    public void setPthPrio(double pthPrio) {
        this.pthPrio = pthPrio;
    }

    public enum Overlay{
        MOVEMENT, TARGET, NONE
    }

    Tile(int x, int y, Terrain terrain){
        this.x = x;
        this.y = y;
        this.terrain = terrain;
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

    int getMovementCost(Unit.MovementType movType) {
        return terrain.getMovementCost(movType);
    }

    public String getTerrainType(){
        return terrain.getType();
    }

    int getEvasion() {
        return terrain.getEvasion();
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
