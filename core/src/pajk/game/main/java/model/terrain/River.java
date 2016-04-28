package pajk.game.main.java.model.terrain;

/**
 * Created by Johan on 2016-04-28.
 */
public class River implements Terrain {
    @Override
    public int getMovementCost() {
        return 10;
    }

    @Override
    public int getEvasion() {
        return 60;
    }
}
