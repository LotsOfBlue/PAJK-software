package pajk.game.main.java.model.states;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.Tile;
import pajk.game.main.java.model.units.Unit;

import java.util.*;

/**
 * Created by Gustav on 2016-04-22.
 */
public class UnitMenuState extends State {

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
    void upAction(){
        menuItemSelected = (menuItemSelected + menuList.size()-1) % menuList.size();
    }

    @Override
    void downAction(){
        menuItemSelected = (menuItemSelected + 1) % menuList.size();
    }

    @Override
    void leftAction(){

    }

    @Override
    void rightAction(){

    }

    @Override
    void enterAction(){
        switch (menuList.get(menuItemSelected)) {
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
                model.setState(GameModel.StateName.MAIN);

                break;
            case "Status":
                model.setState(GameModel.StateName.STATUS);
                break;
        }
    }

    @Override
    void backAction(){
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

    public List<String> getMenuList(){
        return new ArrayList<String>(menuList);
    }

    public int getMenuItemSelected(){
        return menuItemSelected;
    }
}
