package pajk.game.main.java.model.terrain;

import pajk.game.main.java.model.Unit;

/**
 * Created by Johan on 2016-04-28.
 */
public class Forest implements Terrain {
    @Override
    public int getMovementCost(Unit.UnitMovementType movType) {
        switch (movType){
            case flying:
                return 1;
            case riding:
                return 3;
            case walking:
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
