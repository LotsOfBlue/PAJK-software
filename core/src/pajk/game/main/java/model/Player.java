package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palm on 2016-04-18.
 */
public class Player {
    private boolean computerControlled;
    private List<Unit> unitList = new ArrayList<Unit>();
    public Player(boolean computerControlled){
        this.computerControlled = computerControlled;
    }

    public void addUnit(Unit unit){
        unitList.add(unit);
    }
}
