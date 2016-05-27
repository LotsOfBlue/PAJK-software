package game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public class BrokenKnife extends Sword {
    public BrokenKnife(){
        name = "Broken Knife";
        minRange = 1;
        maxRange = 1;
        damage = 2;
        critChance = 0;
        accuracy = 65;
    }

    @Override
    public String getDescription() {
        return "A little broken dagger. Not very useful.";
    }
}
