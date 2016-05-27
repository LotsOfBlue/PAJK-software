package game.main.java.model.scenarios;

import game.main.java.model.Board;
import game.main.java.model.items.*;
import game.main.java.model.units.*;
import game.main.java.model.utils.FileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-16.
 */
public abstract class Scenario {

    protected String mapName;
    protected String name;
    protected String description;
    protected String screenshotPath;

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

    public String getScreenshotPath() {
        return screenshotPath;
    }
}
