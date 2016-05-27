package game.main.java.model.terrain;

import game.main.java.model.units.Unit;

/**
 * Created by Johan on 2016-04-28.
 */
public class Wall implements Terrain {
    @Override
    public int getMovementCost(Unit.MovementType movType) {
        switch (movType){
            case FLYING:
                return 100;
            case RIDING:
                return 100;
            case WALKING:
                return 100;
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
        return "Wall";
    }
}
