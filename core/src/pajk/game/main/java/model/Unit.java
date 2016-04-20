package pajk.game.main.java.model;

import java.util.ArrayList;

/**
 * Created by palm on 2016-04-15.
 * The unit class is the representation of a carachter on the gameboard, it has stats & stuff
 */
class Unit {
    //Stats of a unit
    private int level = 1;
    private int experience = 1;
    private int strenght = 1;
    private int skill = 1;
    private int speed = 1;
    private int luck = 1;
    private int defence = 1;
    private int resistance = 1;
    private int movement = 1;
    private int constitution = 1;
    private int aid = 1;
    private UnitState state = UnitState.isReady;
    private UnitMovementType movementType = UnitMovementType.walking;
    private Weapon weapon = new Weapon(Weapon.Type.Pike, );
    private ArrayList<Item> itemList;
    private Allegiance allegiance;
    public enum Allegiance{
        human,
        ai
    }
    public enum UnitState{
        isReady,
        isMoved,
        isDone
    }
    public enum UnitMovementType{
        walking,
        riding,
        flying
    }
    Unit(Allegiance allegiance
                    /*int level,
                    int experience,
                    int strenght,
                    int skill,
                    int speed,
                    int luck,
                    int defence,
                    int resistance,
                    int movement,
                    int constitution,
                    int aid,
                    ConditionType condition,
                    MovementType movementType,
                    AffinityType affinity*/){
        this.allegiance = allegiance;

    }
    //----------------------------------------------Getters

    /**
     * @return the Level of the unit
     */
    public int getLevel(){ return level;}

    /**
     * @return the Experience of the unit
     */
    public int getExperience(){ return experience;}

    /**
     * @return the Strenght of the unit
     */
    public int getStrenght(){ return strenght;}

    /**
     * @return the Skill of the unit
     */
    public int getSkill(){return skill;}

    /**
     * @return the Speed of the unit
     */
    public int getSpeed(){return speed;}

    /**
     * @return the Luck of the unit
     */
    public int getLuck(){return luck;}

    /**
     * @return the Defence of the unit
     */
    public int getDefence(){return defence;}

    /**
     * @return the Resistance of the unit
     */
    public int getResistance(){return resistance;}

    /**
     * @return the Movement of the unit
     */
    public int getMovement(){return movement;}

    /**
     * @return the Constitution of the unit
     */
    public int getConstitution(){return constitution;}

    /**
     * @return the Aid of the unit
     */
    public int getAid(){return aid;}

    /**
     * @return the MovementType of the unit
     */
    public UnitMovementType getMovementType(){return movementType;}
    //private UnitAffinity affinity = UnitAfinity.;



    /**
     * @return all the Items of the unit
     */
    public ArrayList<Item> getItemList(){return itemList;}

    /**
     * @return the Weapon Type of the unit
     */
    public Weapon.Type getWeaponType(){return getWeaponType();}

    /**
     * @return the weapon damage of the unit
     */
    public int getWeaponDamage() {
        return weapon.getDamage();
    }

    /**
     * @return the attack Range of the unit
     */
    public int getWeaponRange(){return weapon.getRange();}

    /**
     * @return the current State of the unit, see @UnitState
     */
    public UnitState getState() {return state;}

    /**
     * @return the Allegiance of the unit, ethier human or ai
     */
    public Allegiance getAllegiance() {return allegiance;}
    //--------------------------------------------------

    /**
     * Increases the experience of the unit by the @param value
     * @param experience
     */
    public void addExperience(int experience){this.experience+=experience;}


}
