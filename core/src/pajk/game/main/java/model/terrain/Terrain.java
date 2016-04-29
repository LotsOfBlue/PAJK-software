package pajk.game.main.java.model.terrain;

import pajk.game.main.java.model.Unit;

/**
 * Created by Johan on 2016-04-28.
 */
public interface Terrain {

    int getMovementCost(Unit.UnitMovementType movType);

    int getEvasion();
}
