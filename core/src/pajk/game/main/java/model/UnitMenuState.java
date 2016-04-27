package pajk.game.main.java.model;

import pajk.game.main.java.ActionName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gustav on 2016-04-22.
 */
public class UnitMenuState implements State {

    private Unit activeUnit;
    private Map<Integer, String> menuMap = new HashMap<>();
    private int menuItemSelected = 0;

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
                System.out.println(menuItemSelected + menuMap.get(menuItemSelected));
                break;
            case DOWN:
                menuItemSelected = (menuItemSelected + 1) % menuMap.size();
                System.out.println(menuItemSelected + menuMap.get(menuItemSelected));
                break;
            case ENTER:
                //If the user selected 'Move'...
                if (menuMap.get(menuItemSelected).equals("Move")) {
                    System.out.println("Move selected");
                    StateManager.getInstance().setState(StateManager.StateName.CHOOSE_TILE);
                }
                break;
        }
    }

    @Override
    public void activate() {
        activeUnit = StateManager.getInstance().getActiveUnit();
    }
}
