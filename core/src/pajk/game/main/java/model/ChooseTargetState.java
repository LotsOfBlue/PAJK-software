package pajk.game.main.java.model;

import java.util.Set;

/**
 * This state is active when you have selected "Attack" from the unit menu, and here you are supposed to move the cursor
 * to the enemy you want to attack and then confirm your selection with enter.
 *
 * Created by Gustav on 2016-04-28.
 */
public class ChooseTargetState extends MoveState {

    private Unit activeUnit;
    private Board board;
    private GameModel model;
    private Set<Tile> allowedTiles;


    @Override
    public void enterAction(){
        //See if the cursor is over a tile you can attack, and if there's an enemy there.
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
     * Cancels the attack and return back to the unit menu.
     */
    @Override
    public void backAction() {
        for (Tile t : allowedTiles) {
            t.setOverlay(Tile.Overlay.NONE);
        }
        model.setState(GameModel.StateName.UNIT_MENU);
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        board = GameModel.getInstance().getBoard();
        activeUnit = model.getActiveUnit();
        Tile centerTile = board.getPos(activeUnit);
        allowedTiles = board.getTilesAround(centerTile, activeUnit.getWeaponMinRange(), activeUnit.getWeaponMaxRange());
        for (Tile t:
                allowedTiles) {
            t.setOverlay(Tile.Overlay.TARGET);
        }
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.CHOOSE_TARGET;
    }
}
