package pajk.game.main.java.model.states;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.Tile;
import pajk.game.main.java.model.units.Unit;

/**
 * This state is active when you start the game. It allows you to move the cursor around the board and select units.
 *
 * Created by Gustav on 2016-04-22.
 */
public class MainState extends MoveState {

    private GameModel model;
    private Board board;

    private boolean bannerActive = false;
    private int bannerCooldown = 60;

    @Override
    public void enterAction() {
        if (!bannerActive) {
            //Open the menu for the unit under the cursor of it's an allied unit that has not yet acted this turn.
            Tile cursorTile = board.getCursorTile();
            if (cursorTile.hasUnit()){
                Unit currentUnit = cursorTile.getUnit();
                model.setActiveUnit(currentUnit);
                if(currentUnit.getAllegiance() == Unit.Allegiance.AI){
                    model.setState(GameModel.StateName.STATUS);
                } else {
                    model.setState(GameModel.StateName.UNIT_MENU);
                }
            }
        }
    }

    @Override
    public void anyAction(){
        if(model.allUnitsDone()) {
            if (bannerCooldown == 0){
                bannerActive = false;
                bannerCooldown = 60;
                model.setState(GameModel.StateName.ENEMY_TURN);
            } else {
                bannerCooldown--;
                bannerActive = true;
            }
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MAIN;
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        board = GameModel.getInstance().getBoard();
        //If all enemies or allies dead, win/lose game
        if(model.isGameOver()){
            model.setState(GameModel.StateName.END);
        }

    }

    public boolean isEnemyTurnBannerActive(){
        return bannerActive;
    }
}
