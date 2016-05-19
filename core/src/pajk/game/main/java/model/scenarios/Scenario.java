package pajk.game.main.java.model.scenarios;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.items.HuntingBow;
import pajk.game.main.java.model.items.IronPike;
import pajk.game.main.java.model.items.Weapon;
import pajk.game.main.java.model.units.Archer;
import pajk.game.main.java.model.units.Pikeman;
import pajk.game.main.java.model.units.Swordsman;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.items.IronSword;
import pajk.game.main.java.model.utils.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-16.
 */
public class Scenario {

    private String mapName;
    private String unitFileName;
    private String name;
    private String description;
    private final int ROWS_PER_UNIT = 6;

    public Scenario(String mapName, String unitFileName, String scenarioName, String scenarioDescription){
        this.mapName = mapName;
        this.unitFileName = unitFileName;
        this.name = scenarioName;
        this.description = scenarioDescription;
    }

    public Board makeBoard(){
        return new Board(mapName);
    }

    public List<Unit> makeUnitList(Board board){
        ArrayList<Unit> result = new ArrayList<>();
        List<String> rows = FileReader.readFile(unitFileName);
        for (int i = 0; i < rows.size() / ROWS_PER_UNIT; i++) {
            int offset = i * ROWS_PER_UNIT;
            Unit unit = createUnit(parseAllegiance(rows.get(offset)), rows.get(offset + 3), Integer.parseInt(rows.get(offset + 4)));
            board.moveUnit(unit, board.getTile(Integer.parseInt(rows.get(offset + 1)), Integer.parseInt(rows.get(offset + 2))));
            unit.setWeapon(parseWeapon(rows.get(offset + 5)));
            result.add(unit);
        }
        return result;
    }

    private Unit.Allegiance parseAllegiance(String str){
        switch (str){
            case "PLAYER":
                return Unit.Allegiance.PLAYER;
            case "AI":
                return Unit.Allegiance.AI;
            default:
                return null;
        }
    }

    private Unit createUnit(Unit.Allegiance allegiance, String profession, int level){
        switch (profession){
            case "Archer":
                return new Archer(allegiance, level);
            case "Swordsman":
                return new Swordsman(allegiance, level);
            case "Pikeman":
                return new Pikeman(allegiance, level);
            default:
                return null;
        }
    }

    private Weapon parseWeapon(String str){
        switch (str){
            case "IronSword":
                return new IronSword();
            case "HuntingBow":
                return new HuntingBow();
            case "IronPike":
                return new IronPike();
            default:
                return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
}
