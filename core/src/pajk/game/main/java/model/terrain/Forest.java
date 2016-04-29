package pajk.game.main.java.model.terrain;

import pajk.game.main.java.model.Unit;

/**
 * Created by Johan on 2016-04-28.
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
        return 20;
    }
}
