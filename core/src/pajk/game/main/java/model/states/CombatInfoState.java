package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.units.Unit;

/**
 * A state showing how much damage the units will deal to each other if you commence the attack, and also their chance
 * to hit and crit and other relevant info.
 * You can confirm the attack or back out.
 *
 * Created by Johan on 2016-04-25.
 */
public class CombatInfoState implements State {
    private GameModel gameModel;
    private Board board;

    private Unit activeUnit, targetUnit;

    private int activeDmg, targetDmg, activeHitChance, targetHitChance;

    @Override
    public void performAction(ActionName action) {
        switch (action) {
            case ENTER:
                GameModel.getInstance().setState(GameModel.StateName.COMBAT);
                break;

            case BACK:
                GameModel.getInstance().setState(GameModel.StateName.CHOOSE_TARGET);
                break;
        }
    }

    @Override
    public void activate() {
        //TODO get combat preview values here
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();

        activeUnit = gameModel.getActiveUnit();
        targetUnit = gameModel.getTargetUnit();

        activeDmg = CombatState.calcDamageThisToThat(activeUnit, targetUnit);
        targetDmg = CombatState.calcDamageThisToThat(targetUnit, activeUnit);
        activeHitChance = getHitChance(activeUnit, targetUnit);
        targetHitChance = getHitChance(targetUnit, activeUnit);
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.COMBAT_INFO;
    }

    private int getHitChance(Unit attackerUnit, Unit defenderUnit){
        return (attackerUnit.getWeaponAccuracy()
                + attackerUnit.getSkill()
                + CombatState.getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                - targetUnit.getSpeed())
                - board.getPos(targetUnit).getEvasion();
    }

    public int getActiveDmg() {
        return activeDmg;
    }

    public int getActiveHitChance() {
        return activeHitChance;
    }

    public int getTargetDmg() {
        return targetDmg;
    }

    public int getTargetHitChance() {
        return targetHitChance;
    }

    public Unit getActiveUnit() {
        return activeUnit;
    }

    public Unit getTargetUnit() {
        return targetUnit;
    }

}
