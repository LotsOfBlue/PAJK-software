package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 20/05/2016.
 */
public class Mage extends Unit {
    public Mage(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Mage";
        maxHealth = 14;
        strength = 10;
        skill = 10;
        speed = 8;
        luck = 5;
        defence = 2;
        resistance = 5;
        movement = 5;
        movementType = MovementType.WALKING;

        maxHealthGrowth = 50;
        strengthGrowth = 70;
        skillGrowth = 60;
        speedGrowth = 60;
        luckGrowth = 55;
        defenceGrowth = 30;
        resistanceGrowth = 40;

        addExperience((level - 1) * 100);
        health = maxHealth;
    }
}
