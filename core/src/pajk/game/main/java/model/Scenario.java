package pajk.game.main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-16.
 */
public class Scenario {
    private String mapName;
    private String unitFileName;
    public Scenario(String mapName, String unitFileName){
        this.mapName = mapName;
        this.unitFileName = unitFileName;
    }
    public Board makeBoard(){
        return new Board(mapName);
    }
    public List<Unit> makeUnitList(Board board){
        ArrayList<Unit> result = new ArrayList<>();
        List<String> rows = FileReader.readFile(unitFileName);
        for (int i = 0; i < rows.size() / 17; i++) {
            Unit unit = new Unit(
                    parseAllegiance(rows.get(i + 2)),
                    Integer.parseInt(rows.get(i + 3)),
                    Integer.parseInt(rows.get(i + 4)),
                    Integer.parseInt(rows.get(i + 5)),
                    Integer.parseInt(rows.get(i + 6)),
                    Integer.parseInt(rows.get(i + 7)),
                    Integer.parseInt(rows.get(i + 8)),
                    Integer.parseInt(rows.get(i + 9)),
                    Integer.parseInt(rows.get(i + 10)),
                    Integer.parseInt(rows.get(i + 11)),
                    Integer.parseInt(rows.get(i + 12)),
                    Integer.parseInt(rows.get(i + 13)),
                    Integer.parseInt(rows.get(i + 14)),
                    parseMovement(rows.get(i + 16)),
                    parseClass(rows.get(i + 17)));
            board.moveUnit(unit, board.getTile(Integer.parseInt(rows.get(i)), Integer.parseInt(rows.get(i + 1))));
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
}
