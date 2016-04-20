package pajk.game.main.java.model;

import java.util.ArrayList;

/**
 * Created by palm on 2016-04-15.
 */
class Unit {
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
    //private UnitAffinity affinity = UnitAfinity.;
    private Weapon weapon ;
    private ArrayList<Item> itemList;
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
    Unit(/*int level,
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


    }
    //Getters
    public int getLevel(){ return level;}
    public int getExperience(){ return experience;}
    public int getStrenght(){ return strenght;}
    public int getSkill(){return skill;}
    public int getSpeed(){return speed;}
    public int getLuck(){return luck;}
    public int getDefence(){return defence;}
    public int getResistance(){return resistance;}
    public int getMovement(){return movement;}
    public int getConstitution(){return constitution;}
    public int getAid(){return aid;}
    public UnitState getUnitState(){return state;}
    public UnitMovementType getMovementType(){return movementType;}
    //private UnitAffinity affinity = UnitAfinity.;
    public Weapon getWeapon(){return weapon;}
    public ArrayList<Item> getItemList(){return itemList;}

    public void addExperience(int experience){this.experience+=experience;}

}
