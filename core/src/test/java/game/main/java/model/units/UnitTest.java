package game.main.java.model.units;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import game.main.java.model.items.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palm on 2016-05-23.
 */
public class UnitTest {
    private Unit archer;
    private Unit axeman;
    private Unit mage;
    private Unit pikeman;
    private Unit swordsman;
    private List<Unit> unitList;
    @Before
    public void setUp() throws Exception {
        archer = new Archer(Unit.Allegiance.PLAYER,5);
        axeman = new Axeman(Unit.Allegiance.AI,6);
        mage = new Mage(Unit.Allegiance.PLAYER,4);
        pikeman = new Pikeman(Unit.Allegiance.AI,8);
        swordsman = new Swordsman(Unit.Allegiance.PLAYER,7);
        unitList = new ArrayList<>();
        unitList.add(archer);
        unitList.add(axeman);
        unitList.add(mage);
        unitList.add(pikeman);
        unitList.add(swordsman);
    }

    private void levelUp(){     //TODO edit when experience works as intended.

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getMaxHealth() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getMaxHealth() >= 1 && unit.getMaxHealth() <= 30);
        }
    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void getLevel() throws Exception {
        assertEquals(archer.getLevel(),5);
        assertEquals(axeman.getLevel(),6);
        assertEquals(mage.getLevel(),4);
        assertEquals(pikeman.getLevel(),8);
        assertEquals(swordsman.getLevel(),7);
    }

    @Test
    public void getExperience() throws Exception {      //TODO edit when experience works as intended

    }

    @Test
    public void getStrength() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getStrength() >= 1 && unit.getStrength() <= 30);
        }
    }

    @Test
    public void getSkill() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getSkill() >= 1 && unit.getSkill() <= 30);
        }
    }

    @Test
    public void getSpeed() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getSpeed() >= 1 && unit.getSpeed() <= 30);
        }
    }

    @Test
    public void getLuck() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getLuck() >= 1 && unit.getLuck() <= 30);
        }
    }

    @Test
    public void getDefence() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getDefence() >= 1 && unit.getDefence() <= 30);
        }
    }

    @Test
    public void getResistance() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getResistance() >= 0 && unit.getResistance() <= 30);
        }
    }

    @Test
    public void getMovement() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getMovement() >= 1 && unit.getMovement() <= 30);
        }
    }

    @Test
    public void getProfession() throws Exception {
        assertEquals(archer.getProfession(),"Archer");
        assertEquals(axeman.getProfession(),"Axeman");
        assertEquals(mage.getProfession(),"Mage");
        assertEquals(pikeman.getProfession(),"Pikeman");
        assertEquals(swordsman.getProfession(),"Swordsman");
    }

    @Test
    public void getMovementType() throws Exception {
        for (Unit unit: unitList) {
            assertEquals(unit.getMovementType(), Unit.MovementType.WALKING);
        }
    }



    @Test
    public void getAllegiance() throws Exception {
        assertEquals(archer.getAllegiance(), Unit.Allegiance.PLAYER);
        assertEquals(axeman.getAllegiance(), Unit.Allegiance.AI);
        assertEquals(mage.getAllegiance(), Unit.Allegiance.PLAYER);
        assertEquals(pikeman.getAllegiance(), Unit.Allegiance.AI);
        assertEquals(swordsman.getAllegiance(), Unit.Allegiance.PLAYER);
    }

    @Test
    public void getHealth() throws Exception {
        for (Unit unit: unitList) {
            assertTrue(unit.getHealth() >= 1 && unit.getHealth() <= 30 &&
            unit.getHealth() <= unit.getMaxHealth());
        }
    }

    @Test
    public void getWeapon() throws Exception {
        assertTrue(archer.getWeapon() instanceof Bow);
        assertTrue(axeman.getWeapon() instanceof Axe);
        assertTrue(mage.getWeapon() instanceof Tome);
        assertTrue(pikeman.getWeapon() instanceof Pike);
        assertTrue(swordsman.getWeapon() instanceof Sword);
    }




    @Test
    public void takeDamage() throws Exception {
        archer.takeDamage(archer.getMaxHealth());
        assertEquals(archer.getHealth(),0);
    }

}