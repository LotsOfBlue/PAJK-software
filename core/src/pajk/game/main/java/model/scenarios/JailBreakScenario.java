package game.main.java.model.scenarios;

import game.main.java.model.Board;
import game.main.java.model.items.*;
import game.main.java.model.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustav on 2016-05-20.
 */
public class JailBreakScenario extends Scenario {
    public JailBreakScenario(){
        name = "Save the Captive!";
        description = "Two of your allies have been captured, rush to their aid before they're executed!";
        mapName = "Scenarios/JailbreakMap.txt";
        screenshotPath = "Scenarios/SaveCaptives.png";
    }

    @Override
    public List<Unit> makeUnitList(Board board) {
        List<Unit> unitList = new ArrayList<>();
        Unit currentUnit;

        //Player Units

        //The units that need to be saved.
        currentUnit = new Swordsman(Unit.Allegiance.PLAYER, 7);
        currentUnit.setWeapon(new BrokenKnife());
        board.moveUnit(currentUnit, board.getTile(13, 1));
        unitList.add(currentUnit);

        currentUnit = new Swordsman(Unit.Allegiance.PLAYER, 6);
        currentUnit.setWeapon(new BrokenKnife());
        board.moveUnit(currentUnit, board.getTile(12, 1));
        unitList.add(currentUnit);

        //The rescue force!
        currentUnit = new RidingPikeman(Unit.Allegiance.PLAYER, 8);
        currentUnit.setWeapon(new IronPike());
        board.moveUnit(currentUnit, board.getTile(3, 13));
        unitList.add(currentUnit);

        currentUnit = new RidingAxeman(Unit.Allegiance.PLAYER, 8);
        currentUnit.setWeapon(new IronPike());
        board.moveUnit(currentUnit, board.getTile(4, 13));
        unitList.add(currentUnit);

        currentUnit = new RidingAxeman(Unit.Allegiance.PLAYER, 9);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(0, 14));
        unitList.add(currentUnit);

        currentUnit = new Mage(Unit.Allegiance.PLAYER, 7);
        currentUnit.setWeapon(new ArcaneBolt());
        board.moveUnit(currentUnit, board.getTile(5, 14));
        unitList.add(currentUnit);

        currentUnit = new Axeman(Unit.Allegiance.PLAYER, 7);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(3, 14));
        unitList.add(currentUnit);

        //Enemy Units

        //The executioners.
        currentUnit = new Archer(Unit.Allegiance.AI, 5);
        currentUnit.setWeapon(new HuntingBow());
        board.moveUnit(currentUnit, board.getTile(3, 3));
        unitList.add(currentUnit);

        currentUnit = new Archer(Unit.Allegiance.AI, 5);
        currentUnit.setWeapon(new HuntingBow());
        board.moveUnit(currentUnit, board.getTile(7, 1));
        unitList.add(currentUnit);

        currentUnit = new Archer(Unit.Allegiance.AI, 6);
        currentUnit.setWeapon(new HuntingBow());
        board.moveUnit(currentUnit, board.getTile(10, 9));
        unitList.add(currentUnit);

        //The aggressive guards.
        currentUnit = new RidingAxeman(Unit.Allegiance.AI, 7);
        currentUnit.setWeapon(new IronAxe());
        board.moveUnit(currentUnit, board.getTile(13, 3));
        unitList.add(currentUnit);

        currentUnit = new Mage(Unit.Allegiance.AI, 2);
        currentUnit.setWeapon(new ArcaneBolt());
        board.moveUnit(currentUnit, board.getTile(2, 5));
        unitList.add(currentUnit);

        //The defensive guards.
        currentUnit = new Axeman(Unit.Allegiance.AI, 7);
        currentUnit.setWeapon(new IronAxe());
        currentUnit.setDefender(true);
        board.moveUnit(currentUnit, board.getTile(10, 7));
        unitList.add(currentUnit);

        currentUnit = new Swordsman(Unit.Allegiance.AI, 7);
        currentUnit.setWeapon(new IronSword());
        currentUnit.setDefender(true);
        board.moveUnit(currentUnit, board.getTile(11, 7));
        unitList.add(currentUnit);

        currentUnit = new Swordsman(Unit.Allegiance.AI, 5);
        currentUnit.setWeapon(new IronSword());
        currentUnit.setDefender(true);
        board.moveUnit(currentUnit, board.getTile(3, 8));
        unitList.add(currentUnit);

        return unitList;
    }
}
