package game.main.java.model.states;

import game.main.java.model.Board;
import game.main.java.model.GameModel;
import game.main.java.model.units.Unit;
import game.main.java.model.utils.CombatCalculator;

/**
 * A state showing how much damage the units will deal to each other if you commence the attack, and also their chance
 * to hit and crit and other relevant info.
 * You can confirm the attack or back out.
 *
 * Created by Johan on 2016-04-25.
 */
public class CombatInfoState extends State {
    private GameModel gameModel;
    private Board board;

    private Unit activeUnit, targetUnit;

    private int activeDmg, targetDmg, activeHitChance, targetHitChance, activeCritChance, targetCritChance;


    void enterAction(){
        GameModel.getInstance().setState(GameModel.StateName.COMBAT);
    }
    void backAction(){
        GameModel.getInstance().setState(GameModel.StateName.CHOOSE_TARGET);
    }

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();

        activeUnit = gameModel.getActiveUnit();
        targetUnit = gameModel.getTargetUnit();

        activeDmg = CombatCalculator.calcDamageThisToThat(activeUnit, targetUnit);
        targetDmg = CombatCalculator.calcDamageThisToThat(targetUnit, activeUnit);
        activeHitChance = CombatCalculator.getHitChance(activeUnit, targetUnit, board);
        targetHitChance = CombatCalculator.getHitChance(targetUnit, activeUnit, board);
        activeCritChance = CombatCalculator.getCritChance(activeUnit, targetUnit);
        targetCritChance = CombatCalculator.getCritChance(targetUnit, activeUnit);
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.COMBAT_INFO;
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

    public int getActiveCritChance() { return activeCritChance; }

    public int getTargetCritChance() { return targetCritChance; }

    public Unit getActiveUnit() {
        return activeUnit;
    }

    public Unit getTargetUnit() {
        return targetUnit;
    }

}
