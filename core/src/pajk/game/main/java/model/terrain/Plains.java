package pajk.game.main.java.model.terrain;

/**
 * Created by Johan on 2016-04-28.
 */
public class Plains implements Terrain {

    @Override
    public int getMovementCost() {
        return 1;
    }

    @Override
    public int getEvasion() {
        return 0;
    }
}
