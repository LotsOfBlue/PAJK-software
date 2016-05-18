package pajk.game.main.java.model.scenarios;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.Unit;
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
        for (int i = 0; i < rows.size() / 17; i++) {
            Unit unit = new Unit(
                    parseAllegiance(rows.get(i * 17 + 2)),
                    Integer.parseInt(rows.get(i * 17 + 3)),
                    Integer.parseInt(rows.get(i * 17 + 4)),
                    Integer.parseInt(rows.get(i * 17 + 5)),
                    Integer.parseInt(rows.get(i * 17 + 6)),
                    Integer.parseInt(rows.get(i * 17 + 7)),
                    Integer.parseInt(rows.get(i * 17 + 8)),
                    Integer.parseInt(rows.get(i * 17 + 9)),
                    Integer.parseInt(rows.get(i * 17 + 10)),
                    Integer.parseInt(rows.get(i * 17 + 11)),
                    Integer.parseInt(rows.get(i * 17 + 12)),
                    Integer.parseInt(rows.get(i * 17 + 13)),
                    Integer.parseInt(rows.get(i * 17 + 14)),
                    parseMovement(rows.get(i * 17 + 15)),
                    parseClass(rows.get(i * 17 + 16)));
            board.moveUnit(unit, board.getTile(Integer.parseInt(rows.get(i * 17)), Integer.parseInt(rows.get(i * 17 + 1))));
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

    private Unit.MovementType parseMovement(String str){
        switch (str){
            case "WALKING":
                return Unit.MovementType.WALKING;
            case "RIDING":
                return Unit.MovementType.RIDING;
            case "FLYING":
                return Unit.MovementType.FLYING;
            default:
                return null;
        }
    }

    private Unit.UnitClass parseClass(String str){
        switch (str){
            case "SWORD":
                return Unit.UnitClass.SWORD;
            case "AXE":
                return Unit.UnitClass.AXE;
            case "PIKE":
                return Unit.UnitClass.PIKE;
            case "BOW":
                return Unit.UnitClass.BOW;
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

    public void setDescription(String description) {
        this.description = description;
    }
}
