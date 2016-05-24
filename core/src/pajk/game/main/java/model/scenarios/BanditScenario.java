package pajk.game.main.java.model.scenarios;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.items.*;
import pajk.game.main.java.model.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-20.
 */
public class BanditScenario extends Scenario {
    public BanditScenario(){
        name = "Deal with the bandits!";
        description = "Your group of mercenaries have been tasked to hunt down a small pack of bandits that has been " +
                "troubling the nearby town.";
        mapName = "Scenarios/BanditMap.txt";
        screenshotPath = "Scenarios/DealWithBandits.png";
    }

    @Override
    public List<Unit> makeUnitList(Board board) {
        List<Unit> unitList = new ArrayList<>();
        Unit currentUnit;
        //Player Units
        currentUnit = new Swordsman(Unit.Allegiance.PLAYER, 7);
        board.moveUnit(currentUnit, board.getTile(3, 1));
        unitList.add(currentUnit);

        currentUnit = new Swordsman(Unit.Allegiance.PLAYER, 5);
        board.moveUnit(currentUnit, board.getTile(1, 1));
        unitList.add(currentUnit);

        currentUnit = new Archer(Unit.Allegiance.PLAYER, 6);
        board.moveUnit(currentUnit, board.getTile(0, 0));
        unitList.add(currentUnit);

        currentUnit = new Pikeman(Unit.Allegiance.PLAYER, 7);
        board.moveUnit(currentUnit, board.getTile(2, 0));
        unitList.add(currentUnit);

        //Enemy Units
        currentUnit = new Swordsman(Unit.Allegiance.AI, 3);
        board.moveUnit(currentUnit, board.getTile(4, 8));
        currentUnit.setDefender(true);
        unitList.add(currentUnit);

        currentUnit = new Axeman(Unit.Allegiance.AI, 4);
        board.moveUnit(currentUnit, board.getTile(20, 5));
        unitList.add(currentUnit);

        currentUnit = new Axeman(Unit.Allegiance.AI, 3);
        board.moveUnit(currentUnit, board.getTile(19, 6));
        unitList.add(currentUnit);

        currentUnit = new Axeman(Unit.Allegiance.AI, 7);
        board.moveUnit(currentUnit, board.getTile(3, 12));
        unitList.add(currentUnit);

        currentUnit = new Archer(Unit.Allegiance.AI, 4);
        board.moveUnit(currentUnit, board.getTile(2, 13));
        unitList.add(currentUnit);

        return unitList;
    }
}
