package pajk.game.main.java.model;

/**
 * Created by jonatan on 20/04/2016.
 * Weapons are special items of a special type, with a range, damage and critmultiplier value.
 */
class Weapon extends Item{
    private Type weaponType;
    private int range, damage, critMult;
    public enum Type{
        Sword,
        Axe,
        Pike,
        Bow,
        Staff,
        Book
    }

    Weapon(Type weaponType, int range, int damage, int criticalMultiplier){
        this.weaponType = weaponType;
        this.critMult = critMult;
        this.damage = damage;
        this.range = range;
    }

    public int getCritMult() {return critMult;}

    public int getDamage() {return damage;}

    public int getRange() {return range;}

    public Type getWeaponType() {return weaponType;}
}
