package game.main.java.model.terrain;

import game.main.java.model.units.Unit;

/**
 * @author Johan
 */
public class Mountain implements Terrain {
    @Override
    public int getMovementCost(Unit.MovementType movType) {
        switch (movType){
            case FLYING:
                return 1;
            case RIDING:
                return 8;
            case WALKING:
                return 8;
            default:
                return 100;
        }
    }

    @Override
    public int getEvasion() {
        return 30;
    }

    @Override
    public String getType() {
        return "Mountain";
    }
}
