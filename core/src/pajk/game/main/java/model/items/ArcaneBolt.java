package pajk.game.main.java.model.items;

/**
 * Created by Gustav on 2016-05-18.
 */
public class ArcaneBolt extends Tome {
    public ArcaneBolt(){
        name = "Arcane Bolt";
        minRange = 1;
        maxRange = 2;
        damage = 5;
        critChance = 1;
        accuracy = 90;
    }

    @Override
    public String getDescription() {
        return "A powerful bolt of arcane magic that will hurt your enemies.";
    }
}