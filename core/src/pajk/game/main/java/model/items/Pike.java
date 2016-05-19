package pajk.game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public abstract class Pike extends Weapon {
    @Override
    public int getAdvantageModifier(Weapon weapon) {
        if (weapon instanceof Sword){
            return +2;
        }
        if (weapon instanceof Axe){
            return -2;
        }
        return 0;
    }
}
