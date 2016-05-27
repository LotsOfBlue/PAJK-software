package game.main.java.model.states;

import game.main.java.model.GameModel;
import game.main.java.model.GameModel.StateName;
import game.main.java.model.units.Unit;

/**
 * @author Jonatan
 */
public class EndState extends State {
    private int score;
    private int turns;
    private int units;

    private GameModel gameModel;
    private Unit.Allegiance winner;

    @Override
    public StateName getName(){
        return StateName.END;
    }

    @Override
    public void activate(){
        gameModel = GameModel.getInstance();
        winner = gameModel.getWinner();
        units = gameModel.getNumberOfUnits(winner);
        turns = gameModel.getNumberOfTurns();
        score = getGameScore();
    }

    @Override
    void enterAction(){
        gameModel.setState(StateName.MAIN_MENU);
    }

    private int getGameScore(){
        final int BASE = 100;
        return BASE * units / turns;
    }

    public int getScore() {
        return score;
    }

    public int getTurns() {
        return turns;
    }

    public int getUnits() {
        return units;
    }

    public Unit.Allegiance getWinner() {
        return winner;
    }
}
