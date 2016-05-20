package pajk.game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public class IronSword extends Sword {
    public IronSword(){
        name = "Iron Sword";
        minRange = 1;
        maxRange = 1;
        damage = 5;
        critChance = 1;
        accuracy = 90;
    }

    @Override
    public String getDescription() {
        return "Sword made of iron. Is stronger in the afternoon.";
    }
}
