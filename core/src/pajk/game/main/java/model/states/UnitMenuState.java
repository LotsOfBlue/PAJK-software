package pajk.game.main.java.model.states;

import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.Tile;
import pajk.game.main.java.model.units.Unit;

import java.util.*;

/**
 * Created by Gustav on 2016-04-22.
 */
public class UnitMenuState implements State {

    private Unit activeUnit;
    private List<String> menuList = new ArrayList<>();
    private int menuItemSelected = 0;
    private GameModel model;
    private Tile oldPos;
    private Board board;

    public UnitMenuState(){
        menuList.add("Move");
        menuList.add("Attack");
        menuList.add("Wait");
        menuList.add("Status");
    }

    @Override
    public void performAction(ActionName action) {
        switch (action){
            case UP:
                menuItemSelected = (menuItemSelected + menuList.size()-1) % menuList.size();
                System.out.println(this);
                break;
            case DOWN:
                menuItemSelected = (menuItemSelected + 1) % menuList.size();
                System.out.println(this);
                break;
            //Selecting a menu item
            case ENTER:
                switch (menuList.get(menuItemSelected)){
                    //Prepare to move the unit
                    case "Move":
                        if (activeUnit.getUnitState() == Unit.UnitState.READY) {
                            oldPos = board.getPos(activeUnit);
                            model.setState(GameModel.StateName.MOVE_SELECT);
                        }
                        break;
                    //Prepare to attack with the unit
                    case "Attack":
                        if (activeUnit.getUnitState() == Unit.UnitState.READY ||
                                activeUnit.getUnitState() == Unit.UnitState.MOVED) {
                            model.setState(GameModel.StateName.CHOOSE_TARGET);
                        }
                        break;
                    //Don't do anything else this turn
                    case "Wait":
                        activeUnit.setUnitState(Unit.UnitState.DONE);
                        if (model.allUnitsDone()) {
                            model.setState(GameModel.StateName.ENEMY_TURN);
                        }
                        else {
                            model.setState(GameModel.StateName.MAIN);
                        }
                        break;
                    case "Status":
                        model.setState(GameModel.StateName.STATUS);
                }
                break;
            //Close the menu and return to the field
            case BACK:
                goBack();
                System.out.println("Closed menu."); //TODO debug
                break;
        }
    }

    private void goBack(){
        if (activeUnit.getUnitState() == Unit.UnitState.MOVED){
            activeUnit.setUnitState(Unit.UnitState.READY);
            board.moveUnit(activeUnit, oldPos);
            model.setState(GameModel.StateName.MOVE_SELECT);
        } else {
            model.setState(GameModel.StateName.MAIN);
        }
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        board = model.getBoard();
        activeUnit = model.getActiveUnit();
        if (activeUnit.getUnitState() == Unit.UnitState.MOVED) {
            menuItemSelected = 1;
        }
        else {
            menuItemSelected = 0;
        }
        System.out.println(this);
    }

    public String toString(){
        String result = "";
        for (String str :
                menuList) {
            if (str.equals(menuList.get(menuItemSelected))){
                result += ">";
            }
            result += "[" + str + "]\n";
        }
        return result;
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.UNIT_MENU;
    }

    public List<String> getMenuList(){        //TODO make deepcopy
        return menuList;
    }

    public int getMenuItemSelected(){
        return menuItemSelected;
    }
}
