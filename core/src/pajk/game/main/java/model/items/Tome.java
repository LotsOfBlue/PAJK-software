package pajk.game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public abstract class Tome extends Weapon {
    @Override
    public int getAdvantageModifier(Weapon weapon) {
        return 0;
    }
}
