package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gustav on 2016-04-22.
 */
public class UnitMenuState implements State {

    private Unit activeUnit;
    private Map<Integer, String> menuMap = new HashMap<>();
    private int menuItemSelected = 0;
    private GameModel model;

    public UnitMenuState(){
        menuMap.put(0, "Move");
        menuMap.put(1, "Attack");
        menuMap.put(2, "Wait");
    }

    @Override
    public void performAction(ActionName action) {
        switch (action){
            case UP:
                menuItemSelected = (menuItemSelected + 2) % menuMap.size();
                System.out.println(this);
                break;
            case DOWN:
                menuItemSelected = (menuItemSelected + 1) % menuMap.size();
                System.out.println(this);
                break;
            //Selecting a menu item
            case ENTER:
                switch (menuMap.get(menuItemSelected)){
                    //Prepare to move the unit
                    case "Move":
                        if (activeUnit.getUnitState() == Unit.UnitState.READY) {
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
                        activeUnit.setUnitState(Unit.UnitState.ATTACKED);
                        model.setActiveUnit(null);
                        model.setState(GameModel.StateName.MAIN_STATE);
                        break;
                }
                break;
            //Close the menu and return to the field
            case BACK:
                model.setActiveUnit(null);
                model.setState(GameModel.StateName.MAIN_STATE);
                System.out.println("Closed menu."); //TODO debug
                break;
        }
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        activeUnit = model.getActiveUnit();
        System.out.println(this.toString());
    }

    public String toString(){
        Set<Integer> menuThings = menuMap.keySet();
        String result = "";
        for (Integer i :
                menuThings) {
            if (menuItemSelected == i){
                result += ">";
            }
            result += "[" + menuMap.get(i) + "]\n";
        }
        return result;
    }
}
