package pajk.game.main.java.model.terrain;

/**
 * Created by Johan on 2016-04-28.
 */
public class Forest implements Terrain {
    @Override
    public int getMovementCost() {
        return 2;
    }

    @Override
    public int getEvasion() {
        return 20;
    }
}
