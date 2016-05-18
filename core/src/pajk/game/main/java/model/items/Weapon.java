package pajk.game.main.java.model.items;

import pajk.game.main.java.model.items.Item;

/**
 * Created by jonatan on 20/04/2016.
 * Weapons are special items of a special type, with a minRange, maxrange damage and crit chance value.
 */
public class Weapon extends Item {
    private WeaponType weaponType;
    private int minRange, maxRange, damage, critChance, accuracy;

    public enum WeaponType{
        SWORD,
        AXE,
        PIKE,
        BOW,
        STAFF,
        BOOK
    }

    public Weapon(WeaponType weaponType, int minRange, int maxRange, int damage, int critChance, int accuracy){
        this.weaponType = weaponType;
        this.critChance = critChance;
        this.damage = damage;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.accuracy = accuracy;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getCritChance() {return critChance;}

    public int getDamage() {return damage;}

    public int getMinRange() {return minRange;}

    public int getAccuracy() {return accuracy;}

    public WeaponType getWeaponType() {return weaponType;}
}
