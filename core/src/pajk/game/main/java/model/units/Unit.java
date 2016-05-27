package game.main.java.model.units;

import game.main.java.model.items.IronPike;
import game.main.java.model.items.Item;
import game.main.java.model.items.Weapon;
import game.main.java.model.utils.NameGenerator;

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
    protected String profession;
    protected String textureFilePath;
    protected String grayTextureFilePath;
    protected String animationFilePath;
    protected String portraitFilePath;
    protected int level = 1;
    protected int experience = 1;
    protected int health = 20;
    protected int maxHealth = 20;
    protected int maxHealthGrowth;
    protected int strength = 5;
    protected int strengthGrowth;
    protected int skill = 5;
    protected int skillGrowth;
    protected int speed = 5;
    protected int speedGrowth;
    protected int luck = 5;
    protected int luckGrowth;
    protected int defence = 5;
    protected int defenceGrowth;
    protected int resistance = 5;
    protected int resistanceGrowth;
    protected int movement = 3;

    private UnitState unitState;
    protected MovementType movementType;
    private List<Item> inventory = new ArrayList<>();
    private Weapon weapon;
    private Allegiance allegiance;
    private boolean defender = false;

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

    public Unit(Allegiance allegiance, int level) {
        this.name = NameGenerator.getRandomName(allegiance);
        this.allegiance = allegiance;
        this.unitState = UnitState.READY;
    }

    //----------------------------------------------Getters

    public int getMaxHealth() {
        return maxHealth;
    }

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

    public String getProfession(){
        return profession;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public List<Item> getInventory() {
        return inventory;
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

    public boolean isDefender() {
        return defender;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public String getTextureFilePath(){ return  textureFilePath; }

    public String getAnimationFilePath(){ return  animationFilePath; }

    public String getGrayTextureFilePath(){ return grayTextureFilePath; }

    public String getPortraitFilePath(){ return portraitFilePath; }

    //--------------------------------------------------Setters

    public void setDefender(boolean defender) {
        this.defender = defender;
    }

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
     * @param experience The amount of experience points to add
     */
    public void addExperience(int experience) {
        this.experience += experience;
        while (this.experience >= 100) {
            this.experience -= 100;
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        Random random = new Random();
        if (random.nextInt(100) < maxHealthGrowth){
            maxHealth++;
        }
        if (random.nextInt(100) < strengthGrowth){
            strength++;
        }
        if (random.nextInt(100) < skillGrowth){
            skill++;
        }
        if (random.nextInt(100) < speedGrowth){
            speed++;
        }
        if (random.nextInt(100) < luckGrowth){
            luck++;
        }
        if (random.nextInt(100) < defenceGrowth){
            defence++;
        }
        if (random.nextInt(100) < resistanceGrowth){
            resistance++;
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public String toString() {
        return name + ": " + profession;
    }
}
