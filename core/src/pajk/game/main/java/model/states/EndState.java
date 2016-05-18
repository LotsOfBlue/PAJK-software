package pajk.game.main.java.model.states;

import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.GameModel.StateName;
import pajk.game.main.java.model.Unit;
import pajk.game.main.java.model.states.State;

/**
 * Created by jonatan on 16/05/2016.
 */
public class EndState extends State {
    private int score = 1, turns = 1, units = 1, base=100;

    GameModel gameModel;
    Unit.Allegiance winner;

    @Override
    public StateName getName(){
        return StateName.END;
    }

    @Override
    public void activate(){
        System.out.println("end state engaged");
        gameModel = GameModel.getInstance();
        winner = gameModel.getWinner();
        units = gameModel.getNumberOfUnits(winner);
        turns = gameModel.getNumberOfTurns();
        score = getGameScore(winner);

    }

    @Override
    void enterAction(){
        gameModel.setState(StateName.MAIN_MENU);
    }


    public int getGameScore(Unit.Allegiance player){
        return base*units/turns;//TODO more advanced calcs?
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
