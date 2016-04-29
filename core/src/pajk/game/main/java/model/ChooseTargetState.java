package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.Set;

/**
 * Created by Gustav on 2016-04-28.
 */
public class ChooseTargetState implements State {

    private Unit activeUnit;
    private Board board;
    private GameModel model;
    private Set<Tile> allowedTiles;

    public ChooseTargetState(Board board){
        this.board = board;
    }

    @Override
    public void performAction(ActionName action) {
        switch (action){
            case UP:
                board.moveCursor(Board.Direction.NORTH);
                break;
            case LEFT:
                board.moveCursor(Board.Direction.WEST);
                break;
            case RIGHT:
                board.moveCursor(Board.Direction.EAST);
                break;
            case DOWN:
                board.moveCursor(Board.Direction.SOUTH);
                break;
            case ENTER:
                enterAction();
                break;
            case BACK:
                backToMenu();
                break;
        }
    }

    private void enterAction(){
        Tile cursorTile = board.getCursorTile();
        for (Tile t:
                allowedTiles) {
            if (t == cursorTile && cursorTile.hasUnit() && cursorTile.getUnit().getAllegiance() == Unit.Allegiance.AI){
                System.out.println("Found a target");
                //Clear the overlay coloring.
                for (Tile ti:
                        allowedTiles) {
                    ti.setOverlay(Tile.Overlay.NONE);
                }
                model.setTargetUnit(cursorTile.getUnit());
                model.setState(GameModel.StateName.COMBAT_INFO);
                break;
            }
        }
    }

    /**
     * Cancel the attack and return back to the unit menu
     */
    private void backToMenu() {
        for (Tile t : allowedTiles) {
            t.setOverlay(Tile.Overlay.NONE);
        }
        GameModel.getInstance().setState(GameModel.StateName.UNIT_MENU);
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        activeUnit = model.getActiveUnit();
        Tile centerTile = board.getPos(activeUnit);
        allowedTiles = board.getTilesAround(centerTile, activeUnit.getWeaponMinRange(), activeUnit.getWeaponMaxRange());
        for (Tile t:
                allowedTiles) {
            System.out.println(t.toString());
            t.setOverlay(Tile.Overlay.TARGET);
        }
    }
}
