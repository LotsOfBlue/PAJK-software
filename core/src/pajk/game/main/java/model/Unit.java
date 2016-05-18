package pajk.game.main.java.model;

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
public class Unit {
    //Stats of a unit
    private String name;
    private int level = 1;
    private int experience = 1;
    private int health = 20;
    private int maxHealth = 20;
    private int strength = 5;
    private int might = 5;
    private int skill = 5;
    private int speed = 5;
    private int luck = 5;
    private int defence = 5;
    private int resistance = 5;
    private int movement = 3;
    private int constitution = 5;
    private int aid = 5;

    private UnitState unitState;
    private MovementType movementType;
    private List<Item> inventory = new ArrayList<>();
    private Weapon weapon = new IronPike();
    private Allegiance allegiance;
    private UnitClass unitClass = UnitClass.SWORD;



    /**
     * Which entity the unit belongs to.
     */
    public enum Allegiance {
        PLAYER,
        AI
    }

    public enum UnitClass {
        SWORD,
        AXE,
        PIKE,
        BOW
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

    public Unit(   Allegiance allegiance,
            int level,
            int experience,
            int health,
            int strength,
            int skill,
            int speed,
            int luck,
            int defence,
            int resistance,
            int movement,
            int constitution,
            int aid,
            MovementType movementType,
            UnitClass unitClass) {
        this.name = NameGenerator.getRandomName(allegiance);

        this.allegiance = allegiance;
        this.level = level;
        this.experience = experience;
        this.maxHealth = health;
        this.health = health;
        this.strength = strength;
        this.skill = skill;
        this.speed = speed;
        this.luck = luck;
        this.defence = defence;
        this.resistance = resistance;
        this.movement = movement;
        this.constitution = constitution;
        this.aid = aid;
        this.movementType = movementType;
        this.unitClass = unitClass;

        this.unitState = UnitState.READY;

    }

    //----------------------------------------------Getters

    public int getMaxHealth() {
        return maxHealth;
    }

    public UnitClass getUnitClass(){ return unitClass;}

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
