package pajk.game.main.java.model.scenarios;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.items.*;
import pajk.game.main.java.model.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-20.
 */
public class MountainScenario extends Scenario {
    public MountainScenario(){
        name = "Ascend the mountain!";
        description = "Your merry band of adventurers are pelted on all sides by pesky angels as you try to " +
                "climb up a mountain. Stave them off and get to the top!";
        mapName = "MountainMap.txt";
        screenshotPath = null;
    }

    @Override
    public List<Unit> makeUnitList(Board board) {
        List<Unit> unitList = new ArrayList<>();
        Unit currentUnit;

        //Player Units
        currentUnit = new Swordsman(Unit.Allegiance.PLAYER, 7);
        currentUnit.setWeapon(new IronSword());
        board.moveUnit(currentUnit, board.getTile(2, 19));
        unitList.add(currentUnit);

        currentUnit = new Mage(Unit.Allegiance.PLAYER, 8);
        currentUnit.setWeapon(new ArcaneBolt());
        board.moveUnit(currentUnit, board.getTile(3, 18));
        unitList.add(currentUnit);

        currentUnit = new Archer(Unit.Allegiance.PLAYER, 8);
        currentUnit.setWeapon(new HuntingBow());
        board.moveUnit(currentUnit, board.getTile(4, 19));
        unitList.add(currentUnit);

        currentUnit = new Archer(Unit.Allegiance.PLAYER, 6);
        currentUnit.setWeapon(new HuntingBow());
        board.moveUnit(currentUnit, board.getTile(5, 18));
        unitList.add(currentUnit);

        currentUnit = new RidingAxeman(Unit.Allegiance.PLAYER, 9);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(7, 19));
        unitList.add(currentUnit);

        currentUnit = new RidingPikeman(Unit.Allegiance.PLAYER, 9);
        currentUnit.setWeapon(new IronPike());
        board.moveUnit(currentUnit, board.getTile(6, 18));
        unitList.add(currentUnit);

        currentUnit = new RidingSwordsman(Unit.Allegiance.PLAYER, 9);
        currentUnit.setWeapon(new IronSword());
        board.moveUnit(currentUnit, board.getTile(7, 18));
        unitList.add(currentUnit);


        //Enemy Units
        //Super easy boss unit.
        currentUnit = new FlyingSwordsman(Unit.Allegiance.AI, 1);
        currentUnit.setWeapon(new IronSword());
        board.moveUnit(currentUnit, board.getTile(1, 0));
        currentUnit.setDefender(true);
        unitList.add(currentUnit);

        //Flier at the base of the mountain
        currentUnit = new FlyingSwordsman(Unit.Allegiance.AI, 6);
        currentUnit.setWeapon(new IronSword());
        board.moveUnit(currentUnit, board.getTile(14, 15));
        currentUnit.setDefender(true);
        unitList.add(currentUnit);

        //Defender after the first bend
        currentUnit = new FlyingAxeman(Unit.Allegiance.AI, 7);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(1, 13));
        currentUnit.setDefender(true);
        unitList.add(currentUnit);

        //Defenders at the midway point.
        currentUnit = new FlyingAxeman(Unit.Allegiance.AI, 6);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(4, 7));
        currentUnit.setDefender(true);
        unitList.add(currentUnit);

        currentUnit = new FlyingSwordsman(Unit.Allegiance.AI, 7);
        currentUnit.setWeapon(new IronSword());
        board.moveUnit(currentUnit, board.getTile(6, 7));
        currentUnit.setDefender(true);
        unitList.add(currentUnit);

        //Defender at the last bend.
        currentUnit = new FlyingPikeman(Unit.Allegiance.AI, 12);
        currentUnit.setWeapon(new IronPike());
        board.moveUnit(currentUnit, board.getTile(10, 5));
        currentUnit.setDefender(true);
        unitList.add(currentUnit);

        //Assault Angels
        currentUnit = new FlyingPikeman(Unit.Allegiance.AI, 7);
        currentUnit.setWeapon(new IronPike());
        board.moveUnit(currentUnit, board.getTile(8, 13));
        currentUnit.setDefender(false);
        unitList.add(currentUnit);

        currentUnit = new FlyingPikeman(Unit.Allegiance.AI, 7);
        currentUnit.setWeapon(new IronPike());
        board.moveUnit(currentUnit, board.getTile(9, 14));
        currentUnit.setDefender(false);
        unitList.add(currentUnit);

        currentUnit = new FlyingPikeman(Unit.Allegiance.AI, 8);
        currentUnit.setWeapon(new IronPike());
        board.moveUnit(currentUnit, board.getTile(5, 8));
        currentUnit.setDefender(false);
        unitList.add(currentUnit);

        currentUnit = new FlyingAxeman(Unit.Allegiance.AI, 8);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(13, 1));
        currentUnit.setDefender(false);
        unitList.add(currentUnit);

        currentUnit = new FlyingAxeman(Unit.Allegiance.AI, 9);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(14, 1));
        currentUnit.setDefender(false);
        unitList.add(currentUnit);


        return unitList;
    }
}
