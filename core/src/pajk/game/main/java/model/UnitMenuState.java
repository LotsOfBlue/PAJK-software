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
    private GameModel manager;

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
            case ENTER:
                //If the user selected 'Move'...
                switch (menuMap.get(menuItemSelected)){
                    case "Move":
                        System.out.println("Move selected");
                        manager.setState(GameModel.StateName.MOVE_SELECT);
                        break;
                    case "Attack":
                        System.out.println("Attack selected");
                        manager.setState(GameModel.StateName.CHOOSE_TARGET);
                        break;
                }
                break;
        }
    }

    @Override
    public void activate() {
        manager = GameModel.getInstance();
        activeUnit = GameModel.getInstance().getActiveUnit();
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
