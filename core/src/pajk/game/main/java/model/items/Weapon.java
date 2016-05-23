package pajk.game.main.java.model.items;

/**
 * Created by jonatan on 20/04/2016.
 * Weapons are special items of a special type, with a minRange, maxrange damage and crit chance value.
 */
public abstract class Weapon extends Item {
    protected int minRange, maxRange, damage, critChance, accuracy;
    protected String name;

    public abstract int getAdvantageModifier(Weapon weapon);

    public int getMaxRange() {
        return maxRange;
    }

    public int getCritChance() {return critChance;}

    public int getDamage() {return damage;}

    public int getMinRange() {return minRange;}

    public int getAccuracy() {return accuracy;}

    public String getName(){ return name;}
}
