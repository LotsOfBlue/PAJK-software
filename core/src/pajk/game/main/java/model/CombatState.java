package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.Random;

//TODO make combat make sense after the refactoring
/**
 * Created by jonatan on 28/04/2016.
 */
public class CombatState implements State {

    private GameModel gameModel;
    private Unit activeUnit;
    private Unit enemyUnit;
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

    @Override
    public void performAction(ActionName action) {
        if(action.equals(ActionName.COMBAT_DONE)){
            if (enemyUnit.getHealth() < 1) {
            board.getPos(enemyUnit).setUnit(null);
        }
            if (activeUnit.getHealth() < 1) {
                board.getPos(activeUnit).setUnit(null);

            }
            flush();

            //Return to the main state when done
            gameModel.setState(GameModel.StateName.MAIN_STATE);
        }
    }

    /**
     *  Flush is a cleanup function meant to reset every "case sensitive" variable in combat
     */
    private void flush(){
        activeUnit = null;
        enemyUnit = null;
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

    @Override
    public void activate(){
        gameModel = GameModel.getInstance();
        this.board = GameModel.getInstance().getBoard();
        activeUnit = gameModel.getActiveUnit();
        //Enemy chosen by user
        enemyUnit = gameModel.getTargetUnit();
        //TODO make fight club great again

        if (firstHitFromActiveUnit = doesThisHitThat(activeUnit, enemyUnit)) {
            //Hit enemy
            // TODO howto nice boolean fallout
            int critMult = 1;
            if(firstCritFromActiveUnit = doesThisCritThat(activeUnit, enemyUnit)){
                critMult = 2;
            }
            firstDamageFromActiveUnit = critMult*calcDamageThisToThat(activeUnit, enemyUnit);
            enemyUnit.takeDamage(firstDamageFromActiveUnit);
        }

        System.out.println("attacker:" + firstDamageFromActiveUnit);//TODO REMOVE

        //If enemy still alive, hit active
        if (enemyUnit.getHealth() > 0) {
            attackFromEnemyUnit = true;
            if(hitFromEnemyUnit = doesThisHitThat(enemyUnit, activeUnit)){
                // TODO howto nice boolean fallout
                int critMult = 1;
                if(critFromEnemyUnit = doesThisCritThat(enemyUnit, activeUnit)){
                    critMult = 2;
                }
                damageFromEnemyUnit = critMult*calcDamageThisToThat(enemyUnit, activeUnit);
                activeUnit.takeDamage(damageFromEnemyUnit);
            }

            System.out.println("defender:" + damageFromEnemyUnit);//TODO REMOVE


        }

        //If active still alive and fast enough, hit enemy again
        if (activeUnit.getHealth() > 0) {
            if (activeUnit.getSpeed() >= (enemyUnit.getSpeed() + 4)) {
                secondAttackFromActiveUnit = true;
                if(secondHitFromActiveUnit = doesThisHitThat(activeUnit, enemyUnit)){
                    // TODO howto nice boolean fallout
                    int critMult = 1;
                    if(secondCritFromActiveUnit = doesThisCritThat(activeUnit, enemyUnit)){
                        critMult = 2;
                    }
                    secondDamageFromActiveUnit = critMult*calcDamageThisToThat(activeUnit, enemyUnit);
                    enemyUnit.takeDamage(secondDamageFromActiveUnit);
                }

                System.out.println("attacker:" + secondDamageFromActiveUnit);//TODO REMOVE
            }
        }

        calcDone = true; //Signals that graphics can now be presented
        //If the previously active unit wasn't done, force it to finish its turn anyway //TODO understand dis... why prev? should be curr?
        /*Unit prev = gameModel.getPrevActiveUnit();
        if (prev != null && prev.getUnitState() == Unit.UnitState.MOVED) {
            prev.setUnitState(Unit.UnitState.ATTACKED);
        }*/
        activeUnit.setUnitState(Unit.UnitState.ATTACKED);
        //gameModel.setState(GameModel.StateName.MAIN_STATE);
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.COMBAT_STATE;
    }

    //----------------------------------------------------------------------------

    public int calcDamageThisToThat(Unit attackerUnit, Unit defenderUnit) {
        int damage;
        if (attackerUnit.getWeaponType() == Weapon.WeaponType.BOOK) {
            damage = attackerUnit.getWeaponDamage()
                    + attackerUnit.getMight()
                    + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                    - defenderUnit.getResistance();
        } else {
            damage = attackerUnit.getWeaponDamage()
                    + attackerUnit.getStrength()
                    + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                    - defenderUnit.getDefence();
        }


        return damage;
    }

    private boolean doesThisCritThat(Unit attackerUnit, Unit defenderUnit) {
        Random random = new Random();
        return ((attackerUnit.getWeaponCritChance()
                + attackerUnit.getSkill()
                - defenderUnit.getLuck())
                > random.nextInt(100));
    }

    private boolean doesThisHitThat(Unit attackerUnit, Unit defenderUnit) {
        Random random = new Random();
        return ((attackerUnit.getWeaponAccuracy()
                + attackerUnit.getSkill()
                + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                - defenderUnit.getSpeed())
                >
                (random.nextInt(10)//TODO change to correct value instead of test
                + board.getPos(defenderUnit).getEvasion()));
    }

    private int getWeaponAdvantageThisToThat(Unit attackerUnit, Unit defenderUnit) {

        int bonusVal = 0;
        if (attackerUnit.getWeaponType() == Weapon.WeaponType.AXE) {
            if (defenderUnit.getWeaponType() == Weapon.WeaponType.PIKE) {
                bonusVal = 10;
            }else if (defenderUnit.getWeaponType() == Weapon.WeaponType.SWORD){
                bonusVal = -10;
            }
        } else if (attackerUnit.getWeaponType() == Weapon.WeaponType.PIKE) {
            if (defenderUnit.getWeaponType() == Weapon.WeaponType.SWORD) {
                bonusVal = 10;
            }else if (defenderUnit.getWeaponType() == Weapon.WeaponType.AXE){
                bonusVal = -10;
            }
        } else if (attackerUnit.getWeaponType() == Weapon.WeaponType.SWORD) {
            if (defenderUnit.getWeaponType() == Weapon.WeaponType.AXE) {
                bonusVal = 10;
            }else if (defenderUnit.getWeaponType() == Weapon.WeaponType.PIKE){
                bonusVal = -10;
            }
        } else if (attackerUnit.getWeaponType() == Weapon.WeaponType.BOOK) {
            if (defenderUnit.getWeaponType() == Weapon.WeaponType.BOOK) {
                bonusVal = 10;
            }
        } else if (attackerUnit.getWeaponType() == Weapon.WeaponType.BOW) {
            if (defenderUnit.getMovementType() == Unit.MovementType.FLYING) {
                bonusVal = 10;
            }
        }

        return bonusVal;
    }
    //----------------------------------------------------------------------------


    public boolean isAttackFromEnemyUnit() { return attackFromEnemyUnit; }

    public boolean isSecondAttackFromActiveUnit() { return secondAttackFromActiveUnit; }

    public Unit getActiveUnit() {
        return activeUnit;
    }

    public Unit getEnemyUnit() {
        return enemyUnit;
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

    public boolean isCalcDone() { return calcDone; }

    public boolean isCritFromEnemyUnit() { return critFromEnemyUnit; }

    public boolean isFirstCritFromActiveUnit() { return firstCritFromActiveUnit; }

    public boolean isFirstHitFromActiveUnit() { return firstHitFromActiveUnit; }

    public boolean isHitFromEnemyUnit() { return hitFromEnemyUnit; }

    public boolean isSecondCritFromActiveUnit() { return secondCritFromActiveUnit; }

    public boolean isSecondHitFromActiveUnit() { return secondHitFromActiveUnit; }

    //----------------------------------------------------------------------------
}

