package game.main.java.model.terrain;

import game.main.java.model.units.Unit;

/**
 * @author Johan
 */
public class Forest implements Terrain {
    @Override
    public int getMovementCost(Unit.MovementType movType) {
        switch (movType){
            case FLYING:
                return 1;
            case RIDING:
                return 3;
            case WALKING:
                return 2;
            default:
                return 100;
        }
    }

    @Override
    public int getEvasion() {
        return 10;
    }

    @Override
    public String getType() {
        return "Forest";
    }
}
