package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.items.Tome;
import pajk.game.main.java.model.items.Weapon;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.utils.PathFinder;

import java.util.Random;

//TODO make combat make sense after the refactoring

/**
 * Combat state is the part of the game model which deals with the combat of two units.
 * Where activeUnit is the unit which initiated combat and targetUnit is the defendant.
 * These units are delivered by the game model singleton.
 * Refer to docs for combat turn breakdown.
 *  needs to perform combat on it's active and target units
 */
public class CombatState extends State {

    private GameModel gameModel;
    private Unit activeUnit;
    private Unit targetUnit;
    private Board board;
    private boolean calcDone = false;
    private int firstDamageFromActiveUnit = 0;
    private boolean firstHitFromActiveUnit = false;
    private boolean firstCritFromActiveUnit = false;
    private int secondDamageFromActiveUnit = 0;
    private boolean secondHitFromActiveUnit = false;
    private boolean secondCritFromActiveUnit = false;
    private int damageFromEnemyUnit = 0;
    private boolean hitFromEnemyUnit = false;
    private boolean critFromEnemyUnit = false;
    private boolean secondAttackFromActiveUnit = false;
    private boolean attackFromEnemyUnit = false;

    /**
     * The CombatState performAction is called when the player (or another part of the program) is ready to move on.
     * For anything to happen a COMBAT_DONE action must be sent as action
     * @param action is the input from user or other parts of the program
     */
    @Override
    public void performAction(ActionName action) {

        if(action.equals(ActionName.COMBAT_ACTIVE_HIT)) {
            targetUnit.takeDamage(firstDamageFromActiveUnit);

        } else if(action.equals(ActionName.COMBAT_TARGET_HIT)){
            activeUnit.takeDamage(damageFromEnemyUnit);

        } else if(action.equals(ActionName.COMBAT_DONE)){
            targetUnit.takeDamage(secondDamageFromActiveUnit);

            if (targetUnit.getHealth() < 1) {
                gameModel.removeUnit(targetUnit);

            }
            if (activeUnit.getHealth() < 1) {
                gameModel.removeUnit(activeUnit);

            }
            Unit myUnit = activeUnit;
            flush();

            if (myUnit.getAllegiance() == Unit.Allegiance.PLAYER) {
                gameModel.setState(GameModel.StateName.MAIN);

            }
            else {
                gameModel.setState(GameModel.StateName.ENEMY_TURN);
            }
        }
    }

    /*
     *  Flush is a cleanup function meant to reset every "case sensitive" variable in combat
     */
    private void flush(){
        activeUnit = null;
        targetUnit = null;
        calcDone = false;
        firstDamageFromActiveUnit = 0;
        firstHitFromActiveUnit = false;
        firstCritFromActiveUnit = false;
        secondDamageFromActiveUnit = 0;
        secondHitFromActiveUnit = false;
        secondCritFromActiveUnit = false;
        damageFromEnemyUnit = 0;
        hitFromEnemyUnit = false;
        critFromEnemyUnit = false;
        secondAttackFromActiveUnit = false;
        attackFromEnemyUnit = false;
    }

    /**
     * The CombatState activate function should be called when the game model needs to perform
     * combat on it's activeUnit and targetUnit.
     */
    @Override
    public void activate(){

        this.gameModel = GameModel.getInstance();
        this.board = gameModel.getBoard();

        activeUnit = gameModel.getActiveUnit();
        //Enemy chosen by user
        targetUnit = gameModel.getTargetUnit();



        //If the first hit lands
        if (firstHitFromActiveUnit = doesThisHitThat(activeUnit, targetUnit)) {

            //Get critmultiplier & save value
            int critMult = (firstCritFromActiveUnit = doesThisCritThat(activeUnit, targetUnit))? 2:1;

            firstDamageFromActiveUnit = critMult*calcDamageThisToThat(activeUnit, targetUnit);

        }


        //If enemy still alive, hit active
        if (targetUnit.getHealth()-firstDamageFromActiveUnit > 0) {
            //Check if can reach
            double range = PathFinder.estimateDistance(board.getPos(gameModel.getTargetUnit()), board.getPos(gameModel.getActiveUnit()));
            double minRange = targetUnit.getWeapon().getMinRange();
            double maxRange = targetUnit.getWeapon().getMaxRange();
            if(range >= minRange && range <= maxRange){

                //Try to hit
                attackFromEnemyUnit = true;
                if(hitFromEnemyUnit = doesThisHitThat(targetUnit, activeUnit)){

                    //Get crit multiplier & save value
                    int critMult = (critFromEnemyUnit = doesThisCritThat(targetUnit, activeUnit)) ? 2 : 1;
                    damageFromEnemyUnit = critMult*calcDamageThisToThat(targetUnit, activeUnit);

                }
            }



        }

        //If active still alive and fast enough, hit enemy again
        if (activeUnit.getHealth()-damageFromEnemyUnit > 0) {
            if (activeUnit.getSpeed() >= (targetUnit.getSpeed() + 4)) {
                secondAttackFromActiveUnit = true;
                //Try to hit
                if(secondHitFromActiveUnit = doesThisHitThat(activeUnit, targetUnit)){
                    int critMult = (secondCritFromActiveUnit = doesThisCritThat(activeUnit, targetUnit)) ? 2 : 1;
                    secondDamageFromActiveUnit = critMult*calcDamageThisToThat(activeUnit, targetUnit);

                }
            }
        }

        calcDone = true;
        activeUnit.setUnitState(Unit.UnitState.DONE);

    }

    /**
     * Returns the GameModel.StateName of the state (COMBAT)
     * @return name of the state (COMBAT)
     */
    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.COMBAT;
    }

    //----------------------------------------------------------------------------

    /**
     * Calculates the damage attackerUnit would do to defenderUnit with a normal attack (crit and miss exluded).
     * Takes attacker weaponDamage, might or strength, weaponAdvantage & defender resistance or defence into consideration.
     * @param attackerUnit the unit performing the attack.
     * @param defenderUnit the unit being attacked.
     * @return the damage that attacker will deal (if attack hit)
     */
    public static int calcDamageThisToThat(Unit attackerUnit, Unit defenderUnit) {
        int damage;
        if (attackerUnit.getWeapon() instanceof Tome) {
            damage = attackerUnit.getWeapon().getDamage()
                    + attackerUnit.getStrength()
                    + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                    - defenderUnit.getResistance();
        } else {
            damage = attackerUnit.getWeapon().getDamage()
                    + attackerUnit.getStrength()
                    + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                    - defenderUnit.getDefence();
        }

        //The lower bound of damage is 0
        if (damage < 0) {
            return 0;
        } else {
            return damage;
        }
    }

    /*
     * Determines if attackerUnit criticaly hits defenderUnit (with rng)
     */
    private boolean doesThisCritThat(Unit attackerUnit, Unit defenderUnit) {
        Random random = new Random();
        return ((attackerUnit.getWeapon().getCritChance()
                + attackerUnit.getSkill()
                + attackerUnit.getLuck()
                - defenderUnit.getLuck())
                > random.nextInt(100));
    }

    /*
     * Determines if attackerUnit hits defenderUnit (with rng)
     */
    private boolean doesThisHitThat(Unit attackerUnit, Unit defenderUnit) {
        Random random = new Random();
        return ((attackerUnit.getWeapon().getAccuracy()
                + attackerUnit.getSkill()
                + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                - defenderUnit.getSpeed())
                >
                (random.nextInt(10)//TODO change to correct value instead of test
                + board.getPos(defenderUnit).getEvasion()));
    }

    /*
     * Determines how much extra damage attackerUnit does to defenderUnit due to weapon types
     */
    public static int getWeaponAdvantageThisToThat(Unit attackerUnit, Unit defenderUnit) {
        //TODO make part of weapon classes instead
        //asdk for active unit weapon bonus vs defender weapon
        int bonusVal = attackerUnit.getWeapon().getAdvantageModifier(defenderUnit.getWeapon());

        return bonusVal;
    }
    //----------------------------------------------------------------------------


    public boolean isAttackFromEnemyUnit() {
        return attackFromEnemyUnit;
    }

    public boolean isSecondAttackFromActiveUnit() {
        return secondAttackFromActiveUnit;
    }

    public int getDamageFromEnemyUnit() {
        return damageFromEnemyUnit;
    }

    public int getSecondDamageFromActiveUnit() {
        return secondDamageFromActiveUnit;
    }

    public int getFirstDamageFromActiveUnit() {
        return firstDamageFromActiveUnit;
    }

    public boolean isCalcDone() {
        return calcDone;
    }

    public boolean isCritFromEnemyUnit() {
        return critFromEnemyUnit;
    }

    public boolean isFirstCritFromActiveUnit() {
        return firstCritFromActiveUnit;
    }

    public boolean isFirstHitFromActiveUnit() {
        return firstHitFromActiveUnit;
    }

    public boolean isHitFromEnemyUnit() {
        return hitFromEnemyUnit;
    }

    public boolean isSecondCritFromActiveUnit() {
        return secondCritFromActiveUnit;
    }

    public boolean isSecondHitFromActiveUnit() {
        return secondHitFromActiveUnit;
    }

    //----------------------------------------------------------------------------
}

