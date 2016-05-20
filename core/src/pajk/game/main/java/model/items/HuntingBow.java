package pajk.game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public class HuntingBow extends Bow {
    public HuntingBow(){
        name = "Hunting Bow";
        minRange = 2;
        maxRange = 2;
        damage = 5;
        critChance = 1;
        accuracy = 80;
    }

    @Override
    public String getDescription() {
        return "Used to be used for hunting birds. Might hit if you are lucky.";
    }
}
