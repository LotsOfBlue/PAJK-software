package game.main.java.model.terrain;

import game.main.java.model.units.Unit;

/**
 * Represents a type of terrain with its specific parameters.
 * @author Johan
 */
public interface Terrain {

    /**
     * Determine the movement cost of the tile
     * based on the type of unit crossing it.
     * @param movType The movement type of the unit.
     * @return The movement cost of this terrain type.
     */
    int getMovementCost(Unit.MovementType movType);

    /**
     * Determine the evasion bonus/penalty given to
     * units on this tile in combat.
     * @return The added/decreased evasion as a percentage value.
     * Bonuses are positive, penalties are negative.
     */
    int getEvasion();

    /**
     * @return The terrain type's name.
     */
    String getType();
}
