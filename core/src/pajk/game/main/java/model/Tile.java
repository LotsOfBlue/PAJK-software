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

    private double pathH;
    private Tile pathParent = null;
    private double pathG;
    private double pathF;

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

    public Unit getUnit() {
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

    public Tile getPathParent() {
        return pathParent;
    }

    public void setPathParent(Tile pathParent) {
        this.pathParent = pathParent;
    }

    public double getPathG() {
        return pathG;
    }

    public void setPathG(double pathG) {
        this.pathG = pathG;
    }

    public double getPathF() {
        return pathF;
    }

    public void setPathF(double pathF) {
        this.pathF = pathF;
    }

    public double getPathH() {
        return pathH;
    }

    public void setPathH(double pathH) {
        this.pathH = pathH;
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
