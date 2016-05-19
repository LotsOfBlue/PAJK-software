package pajk.game.main.java.model.units;

import pajk.game.main.java.model.items.IronPike;
import pajk.game.main.java.model.items.Item;
import pajk.game.main.java.model.items.Weapon;
import pajk.game.main.java.model.utils.NameGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by palm on 2016-04-15.
 * The unit class is the representation of a character on the game board, it has stats & stuff
 */
public abstract class Unit {
    //Stats of a unit
    protected String name;
    protected int level = 1;
    protected int experience = 1;
    protected int health = 20;
    protected int maxHealth = 20;
    protected int maxHealthGrowth;
    protected int strength = 5;
    protected int might = 5;
    protected int skill = 5;
    protected int speed = 5;
    protected int luck = 5;
    protected int defence = 5;
    protected int resistance = 5;
    protected int movement = 3;
    protected int constitution = 5;
    protected int aid = 5;

    private UnitState unitState;
    protected MovementType movementType;
    private List<Item> inventory = new ArrayList<>();
    private Weapon weapon = new IronPike();
    private Allegiance allegiance;



    /**
     * Which entity the unit belongs to.
     */
    public enum Allegiance {
        PLAYER,
        AI
    }

    public enum UnitState {
        READY,
        MOVED,
        DONE
    }

    public enum MovementType {
        WALKING,
        RIDING,
        FLYING
    }

    public Unit(Allegiance allegiance) {
        this.name = NameGenerator.getRandomName(allegiance);
        this.allegiance = allegiance;

        this.unitState = UnitState.READY;

    }

    //----------------------------------------------Getters

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getUnitClassName(){ return unitClass;}

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }


    public int getExperience() {
        return experience;
    }


    public int getStrength() {
        return strength;
    }


    public int getSkill() {
        return skill;
    }


    public int getSpeed() {
        return speed;
    }


    public int getLuck() {
        return luck;
    }


    public int getDefence() {
        return defence;
    }


    public int getResistance() {
        return resistance;
    }


    public int getMovement() {
        return movement;
    }


    public int getConstitution() {
        return constitution;
    }


    public int getAid() {
        return aid;
    }


    public MovementType getMovementType() {
        return movementType;
    }


    public List<Item> getInventory() {
        return inventory;
    }

    public int getWeaponDamage() {
        return weapon.getDamage();
    }


    public int getWeaponMinRange() {
        return weapon.getMinRange();
    }


    public int getWeaponMaxRange() {
        return weapon.getMaxRange();
    }


    public UnitState getUnitState() {
        return unitState;
    }


    public Allegiance getAllegiance() {
        return allegiance;
    }


    public int getHealth() {
        return health;
    }


    public int getWeaponAccuracy() {
        return weapon.getAccuracy();
    }


    public int getWeaponCritChance() {
        return weapon.getCritChance();
    }


    public int getMight() {
        return might;
    }


    public Weapon getWeapon() {
        return weapon;
    }


    //--------------------------------------------------Setters

    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public void setAllegiance(Allegiance allegiance) {
        this.allegiance = allegiance;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    //--------------------------------------------------

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    /**
     * Increases the experience of the unit by the @param value, can cause lvl up in unit
     * @param experience
     */
    public void addExperience(int experience) {
        this.experience += experience;
        while (this.experience > (level - 1) * 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        Random random = new Random();
        int stats = random.nextInt(10);
        //TODO lvl up throwing?
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health > 0) {
            //TODO unit death throwing?
        }
    }

    @Override
    public String toString() {
        return name + ": " + movementType; //TODO make better or remove
    }
}
