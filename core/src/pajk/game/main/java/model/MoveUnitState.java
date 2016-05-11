package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.List;

/**
 * Created by Gustav on 2016-05-11.
 */
public class MoveUnitState implements State {
    private GameModel gameModel;
    private Board board;
    private Unit unit;
    private Unit unitHolder;
    private Tile target;
    private List<Tile> path;
    private int cooldown = 0;

    @Override
    public void performAction(ActionName action) {
        if (action == ActionName.NO_INPUT){
            update();
        }
    }

    private void update(){
        if (cooldown == 0){
            Tile currentTarget = path.get(path.size() - 1);
            if (unitHolder != null){
                board.placeUnit(unitHolder, board.getPos(unit));
            }
            if (currentTarget.hasUnit()){
                unitHolder = currentTarget.getUnit();
            }else{
                unitHolder = null;
            }
            board.moveUnit(unit, path.get(path.size() - 1));
            path.remove(path.size() - 1);
            cooldown = 7;
        }
        cooldown--;
        System.out.println(cooldown);
        if (board.getPos(unit) == target){
            gameModel.setState(GameModel.StateName.UNIT_MENU);
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.MOVE_UNIT;
    }

    @Override
    public void activate() {
        gameModel = GameModel.getInstance();
        board = gameModel.getBoard();
        unit = gameModel.getActiveUnit();
        target = gameModel.getTargetTile();
        System.out.println("Test2");
        PathFinder pathFinder = new PathFinder(board);
        path = pathFinder.getQuickestPath(board.getPos(unit), target, unit);
        System.out.println("Test3");
    }
}
