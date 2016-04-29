package pajk.game.main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by palm on 2016-04-15.
 * The unit class is the representation of a character on the game board, it has stats & stuff
 */
class Unit {
    //Stats of a unit
    private int level = 1;
    private int experience = 1;
    private int health = 1;
    private int strength = 1;
    private int might = 1;
    private int skill = 1;
    private int speed = 1;
    private int luck = 1;
    private int defence = 1;
    private int resistance = 1;
    private int movement = 1;
    private int constitution = 1;
    private int aid = 1;

    private UnitState state;
    private MovementType movementType;
    private List<Item> inventory = new ArrayList<>();
    private Weapon weapon = new Weapon(Weapon.WeaponType.Pike, 1, 1, 1, 2, 1);
    private Allegiance allegiance;

    public enum Allegiance {
        HUMAN,
        AI
    }

    public enum UnitState {
        READY,
        MOVED,
        ATTACKED,
        DONE
    }

    public enum MovementType {
        WALKING,
        RIDING,
        FLYING
    }

    Unit(Allegiance allegiance,
                    /*int level,
                    int experience,
                    int health,
                    int strength,
                    int skill,
                    int speed,
                    int luck,
                    int defence,
                    int resistance,
                    */int movement,/*,
                    int constitution,
                    int aid,
                    ConditionType condition,
                    */MovementType movementType/*,
                    AffinityType affinity*/) {
        this.allegiance = allegiance;
        this.movement = movement;
        this.movementType = movementType;
        this.state = UnitState.READY;
    }

    //----------------------------------------------Getters

    /**
     * @return the Level of the unit
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the Experience of the unit
     */
    public int getExperience() {
        return experience;
    }

    /**
     * @return the Strength of the unit
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @return the Skill of the unit
     */
    public int getSkill() {
        return skill;
    }

    /**
     * @return the Speed of the unit
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return the Luck of the unit
     */
    public int getLuck() {
        return luck;
    }

    /**
     * @return the Defence of the unit
     */
    public int getDefence() {
        return defence;
    }

    /**
     * @return the Resistance of the unit
     */
    public int getResistance() {
        return resistance;
    }

    /**
     * @return the Movement of the unit
     */
    public int getMovement() {
        return movement;
    }

    /**
     * @return the Constitution of the unit
     */
    public int getConstitution() {
        return constitution;
    }

    /**
     * @return the Aid of the unit
     */
    public int getAid() {
        return aid;
    }

    /**
     * @return the MovementType of the unit
     */
    public MovementType getMovementType() {
        return movementType;
    }

    /**
     * @return all the Items of the unit
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * @return the Weapon Type of the unit
     */
    public Weapon.WeaponType getWeaponType() {
        return weapon.getWeaponType();
    }

    /**
     * @return the weapon damage of the unit
     */
    public int getWeaponDamage() {
        return weapon.getDamage();
    }

    /**
     * @return the attack Range of the unit
     */
    public int getWeaponMinRange() {
        return weapon.getMinRange();
    }

    /**
     * @return the maximum attack range of the unit.
     */
    public int getWeaponMaxRange() {
        return weapon.getMaxRange();
    }

    /**
     * @return the current State of the unit, see @UnitState
     */
    public UnitState getState() {
        return state;
    }

    /**
     * @return the Allegiance of the unit, either HUMAN or AI
     */
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

    public void setState(UnitState state) {
        this.state = state;
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
            //TODO unit death throwing
        }
    }

    public int calcAttackDamage(Unit enemy) {
        int damage;
        if (doesHit(enemy)) {
            int critMultiplier = 1;
            if (this.doesCrit(enemy)) {
                critMultiplier = 2;
            }
            if (this.getWeaponType() == Weapon.WeaponType.Book) {
                damage = critMultiplier * (this.getWeaponDamage()
                        + this.getMight()
                        + this.getWeaponAdvantage(enemy)
                        - enemy.getResistance());
            } else {
                damage = critMultiplier * (this.getWeaponDamage()
                        + this.getStrength()
                        + this.getWeaponAdvantage(enemy)
                        - enemy.getDefence());
            }
        } else {
            damage = 0;
        }

        return damage;
    }

    private boolean doesCrit(Unit enemy) {
        Random random = new Random();
        return ((this.getWeaponCritChance()
                + this.getSkill()
                - enemy.getLuck())
                > random.nextInt(100));
    }

    private boolean doesHit(Unit enemy) {
        Random random = new Random();
        return ((this.getWeaponAccuracy()
                + this.getSkill()
                + this.getWeaponAdvantage(enemy)
                - enemy.getSpeed())
                > random.nextInt(100));
    }

    private int getWeaponAdvantage(Unit enemy) {

        int bonusVal = 0;
        if (this.getWeaponType() == Weapon.WeaponType.Axe) {
            if (enemy.getWeaponType() == Weapon.WeaponType.Pike) {
                bonusVal = 10;
            }
        } else if (this.getWeaponType() == Weapon.WeaponType.Pike) {
            if (enemy.getWeaponType() == Weapon.WeaponType.Sword) {
                bonusVal = 10;
            }
        } else if (this.getWeaponType() == Weapon.WeaponType.Sword) {
            if (enemy.getWeaponType() == Weapon.WeaponType.Axe) {
                bonusVal = 10;
            }
        } else if (this.getWeaponType() == Weapon.WeaponType.Book) {
            if (enemy.getWeaponType() == Weapon.WeaponType.Book) {
                bonusVal = 10;
            }
        } else if (this.getWeaponType() == Weapon.WeaponType.Bow) {
            if (enemy.getMovementType() == MovementType.FLYING) {
                bonusVal = 10;
            }
        }

        return bonusVal;
    }

}
