package pajk.game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public class Bow extends Weapon {
    @Override
    public int getAdvantageModifier(Weapon weapon) {
        if (weapon instanceof Sword){
            return -2;
        }
        if (weapon instanceof Pike){
            return +2;
        }
        return 0;
    }
}
