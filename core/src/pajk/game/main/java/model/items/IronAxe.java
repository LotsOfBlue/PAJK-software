package pajk.game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public class IronAxe extends Axe{
    public IronAxe(){
        name = "Iron Axe";
        minRange = 1;
        maxRange = 2;
        damage = 8;
        critChance = 1;
        accuracy = 75;
    }

    @Override
    public String getDescription() {
        return "Axe made of iron. Hits hard when it eventually hits.";
    }
}
