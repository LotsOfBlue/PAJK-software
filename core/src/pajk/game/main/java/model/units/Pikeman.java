package pajk.game.main.java.model.units;

/**
 * Created by Gustav on 2016-05-19.
 */
public class Pikeman extends Unit {
    public Pikeman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Pikeman";
        maxHealth = 19;
        strength = 7;
        skill = 4;
        speed = 5;
        luck = 3;
        defence = 8;
        resistance = 0;
        movement = 5;
        movementType = MovementType.WALKING;

        maxHealthGrowth = 90;
        strengthGrowth = 60;
        skillGrowth = 45;
        speedGrowth = 35;
        luckGrowth = 30;
        defenceGrowth = 50;
        resistanceGrowth = 25;

        addExperience((level - 1) * 100);
        health = maxHealth;
    }
}
