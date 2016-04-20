package pajk.game.main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palm on 2016-04-18.
 */
class Player {
    private boolean computerControlled;
    private List<Unit> unitList = new ArrayList<Unit>();
    Player(boolean computerControlled){
        this.computerControlled = computerControlled;
    }

    void addUnit(Unit unit){
        unitList.add(unit);
    }
}
