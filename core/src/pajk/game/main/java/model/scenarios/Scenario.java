package pajk.game.main.java.model.scenarios;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.items.*;
import pajk.game.main.java.model.units.*;
import pajk.game.main.java.model.utils.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-16.
 */
public abstract class Scenario {

    protected String mapName;
    protected String name;
    protected String description;

    public Board makeBoard(){
        return new Board(mapName);
    }

    public abstract List<Unit> makeUnitList(Board board);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
