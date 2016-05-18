package pajk.game.main.java.model.terrains;

import pajk.game.main.java.model.units.Unit;

/**
 * Represents a type of terrains with its specific parameters.
 * @author Johan Blomberg
 */
public interface Terrain {

    /**
     * Determine the movement cost of the tile
     * based on the type of unit crossing it.
     * @param movType The movement type of the unit.
     * @return The movement cost of this terrains type.
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
     * @return The terrains type's name.
     */
    String getType();
}
