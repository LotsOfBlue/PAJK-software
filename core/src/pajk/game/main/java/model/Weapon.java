package pajk.game.main.java.model;

/**
 * Created by jonatan on 20/04/2016.
 * Weapons are special items of a special type, with a range, damage and critmultiplier value.
 */
class Weapon extends Item{
    private WeaponType weaponType;
    private int range, damage, critChance, accuracy;
    public enum WeaponType{
        Sword,
        Axe,
        Pike,
        Bow,
        Staff,
        Book
    }

    Weapon(WeaponType weaponType, int range, int damage, int critChance, int accuracy){
        this.weaponType = weaponType;
        this.critChance = critChance;
        this.damage = damage;
        this.range = range;
        this.accuracy = accuracy;
    }

    public int getCritChance() {return critChance;}

    public int getDamage() {return damage;}

    public int getRange() {return range;}

    public int getAccuracy() {return accuracy;}

    public WeaponType getWeaponType() {return weaponType;}
}
