package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

/**
 * Created by Johan on 2016-04-28.
 */
public class EnemyTurnState implements State {
    private GameModel gameModel;
    private Board board;
    private PathFinder pathFinder;

    @Override
    public void performAction(ActionName action) {}

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        pathFinder = new PathFinder(board);
        System.out.println("ENEMY TURN"); //TODO debug
        gameModel.newTurn();

        //Make all enemy units act
        for (Unit u : gameModel.getUnitList()) {
            if (u.getAllegiance().equals(Unit.Allegiance.AI)) {
                pathFinder.getQuickestPath(board.getTile(1,1), board.getTile(0, 4), u);
                //TODO enemy unit logic happens here
            }
        }

        //Once all units are finished, the player's turn begins
        System.out.println("PLAYER TURN"); //TODO debug
        gameModel.newTurn();
        GameModel.getInstance().setState(GameModel.StateName.MAIN_STATE);
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.ENEMY_TURN;
    }
}
