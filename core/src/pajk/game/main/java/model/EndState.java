package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.GameModel.StateName;

/**
 * Created by jonatan on 16/05/2016.
 */
public class EndState implements State {
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
    public void performAction (ActionName actionName){
        if (actionName.equals(ActionName.ENTER)){
            gameModel.setState(StateName.MAIN_MENU);
        }
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
