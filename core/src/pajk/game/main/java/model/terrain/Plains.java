package game.main.java.model.terrain;

import game.main.java.model.units.Unit;

/**
 * @author Johan
 */
public class Plains implements Terrain {

    @Override
    public int getMovementCost(Unit.MovementType movType) {
        switch (movType){
            case FLYING:
                return 1;
            case RIDING:
                return 1;
            case WALKING:
                return 1;
            default:
                return 100;
        }
    }

    @Override
    public int getEvasion() {
        return 0;
    }

    @Override
    public String getType() {
        return "Plains";
    }
}
