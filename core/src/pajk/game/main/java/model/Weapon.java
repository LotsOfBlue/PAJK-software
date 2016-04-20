package pajk.game.main.java.model;

/**
 * Created by jonatan on 20/04/2016.
 */
class Weapon extends Item{
    private WeaponType weaponType;
    private int range, damage, critMult;
    Weapon(WeaponType weaponType, int range, int damage, int criticalMultiplier){
        this.weaponType = weaponType;
        this.critMult = critMult;
        this.damage = damage;
        this.range = range;
    }
    public enum WeaponType{
        Sword,
        Axe,
        Pike,
        Bow,
        Staff,
        Book
    }
}
