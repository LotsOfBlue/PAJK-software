package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by jonatan on 28/04/2016.
 */
public class CombatState implements State {

    private Unit activeUnit;
    private Unit enemyUnit;
    private Board board;
    int firstDamageFromActiveUnit = 0;
    int secondDamageFromActiveUnit = 0;
    int damageFromEnemyUnit = 0;


    public CombatState(Board board){this.board = board;}

    @Override
    public void performAction(ActionName action) {}

    @Override
    public void activate(){
        activeUnit = GameModel.getInstance().getActiveUnit();
        //Enemy chosen by user
        enemyUnit = board.getCursorTile().getUnit();
        //TODO make fight club great again

        firstDamageFromActiveUnit = activeUnit.calcAttackDamage(enemyUnit);
        enemyUnit.takeDamage(firstDamageFromActiveUnit);

        if(enemyUnit.getHealth()>0){
            damageFromEnemyUnit = enemyUnit.calcAttackDamage(activeUnit);
            activeUnit.takeDamage(damageFromEnemyUnit);

            if( (activeUnit.getHealth()>0) && (activeUnit.getSpeed()>=(enemyUnit.getSpeed()+4))){
                secondDamageFromActiveUnit = activeUnit.calcAttackDamage(enemyUnit);
                enemyUnit.takeDamage(secondDamageFromActiveUnit);

            }else{
                board.getPos(activeUnit).setUnit(null);
            }

        }else{
            board.getPos(enemyUnit).setUnit(null);
        }

        activeUnit.setUnitState(Unit.UnitState.ATTACKED);
        GameModel.getInstance().setState(GameModel.StateName.MAIN_STATE);
    }

    public Unit getActiveUnit() { return activeUnit; }

    public Unit getEnemyUnit() { return enemyUnit; }

    public int getDamageFromEnemyUnit() { return damageFromEnemyUnit; }

    public int getSecondDamageFromActiveUnit() { return secondDamageFromActiveUnit; }

    public int getFirstDamageFromActiveUnit() { return firstDamageFromActiveUnit; }
}

