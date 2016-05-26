package pajk.game.main.java.model.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.items.IronSword;
import pajk.game.main.java.model.items.Sword;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.units.UnitTest;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by jonatan on 23/05/2016.
 */
public class CombatCalculatorTest {
    private class TestUnit extends Unit{
        public TestUnit(int level) {
            super(Allegiance.PLAYER, level);
            profession = "testUnit";
            this.maxHealth = 20;
            this.strength = 5;
            this.skill = 5;
            this.speed = 5;
            this.luck = 5;
            this.defence = 5;
            this.resistance = 5;
            this.movement = 5;
            movementType = MovementType.WALKING;

            maxHealthGrowth = 60;
            strengthGrowth = 60;
            skillGrowth = 60;
            speedGrowth = 60;
            luckGrowth = 60;
            defenceGrowth = 60;
            resistanceGrowth = 60;

            this.setWeapon(new IronSword());

            addExperience((level - 1) * 100);
            health = maxHealth;

        }
    }
    private class TestSword extends Sword{
        public TestSword(){
            name = "Test Sword";
            this.minRange = 1;
            this.maxRange = 1;
            this.damage = random.nextInt(10);
            this.critChance = random.nextInt(10);
            this.accuracy = random.nextInt(30) + 70;
        }

        @Override
        public String getDescription() {
            return "This is a testsword.";
        }
    }

    private GameModel gameModel;
    private Board board;
    private Random random;

    @Before
    public void setUp() throws Exception {
        gameModel = GameModel.getInstance();
        gameModel.setBoard(new Board("Scenarios/testboard.txt"));
        board = gameModel.getBoard();
        random = new Random();
    }


    @Test
    /**
     * Combat damage = weapon dmg + bonus + streangth - defence, but not less than 0! (+ attacker, - defender)
     */
    public void calcDamageThisToThat() throws Exception {
        ArrayList<TestUnit> unitList = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            TestUnit temp = new TestUnit(random.nextInt(100));
            temp.setWeapon(new TestSword());
            unitList.add(temp);
        }
        for (TestUnit u: unitList) {
            for (TestUnit t: unitList) {
                int truth = u.getWeapon().getDamage()
                        + u.getStrength()
                        + u.getWeapon().getAdvantageModifier(t.getWeapon())
                        - t.getDefence();
                if (truth < 0){
                    truth = 0;
                }
                assertTrue("Damage is not correctly calculated!", CombatCalculator.calcDamageThisToThat(u, t) == truth);
            }
        }
    }

    @Test
    /**
     * Hit chance = weapon accuracy + bonus + skill - speed - bonus evasion from tile, but not less than 0! (+ attacker, - defender)
     */
    public void getHitChance() throws Exception {
        ArrayList<TestUnit> unitList = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            TestUnit temp = new TestUnit(random.nextInt(100));
            temp.setWeapon(new TestSword());
            unitList.add(temp);
        }
        for (TestUnit u: unitList) {
            for (TestUnit t: unitList) {
                board.getTile(0,0).setUnit(u);
                board.getTile(0,1).setUnit(t);
                int truth = u.getWeapon().getAccuracy()
                        + u.getSkill()
                        + u.getWeapon().getAdvantageModifier(t.getWeapon())
                        - t.getSpeed()
                        - board.getPos(t).getEvasion();
                if(truth < 0){
                    truth = 0;
                }
                assertTrue("Hit chance is not correctly calculated!", CombatCalculator.getHitChance(u, t, board) == truth);

            }
        }
    }

    @Test
    /**
     *  Crit chance = weapon crit chance + skill + luck - luck, but not less than 0! (+ attacker, - defender)
     */
    public void getCritChance() throws Exception {
        ArrayList<TestUnit> unitList = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            TestUnit temp = new TestUnit(random.nextInt(100));
            temp.setWeapon(new TestSword());
            unitList.add(temp);
        }
        for (TestUnit u: unitList) {
            for (TestUnit t: unitList) {
                int truth = u.getWeapon().getCritChance()
                        + u.getSkill()
                        + u.getLuck()
                        - t.getLuck();
                if(truth < 0){
                    truth = 0;
                }
                assertTrue("Crit chance is not correctly calculated!", CombatCalculator.getCritChance(u, t) == truth);
            }
        }
    }

    @Test
    /**
     * Parachute function weapon advantage uses inate function of weapontype
     */
    public void getWeaponAdvantageThisToThat() throws Exception {
        ArrayList<TestUnit> unitList = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            TestUnit temp = new TestUnit(random.nextInt(100));
            temp.setWeapon(new TestSword());
            unitList.add(temp);
        }
        for (TestUnit u: unitList) {
            for (TestUnit t: unitList) {
                assertTrue("Weapon advantage is not correctly calculated!", CombatCalculator.getWeaponAdvantageThisToThat(u, t) ==
                        u.getWeapon().getAdvantageModifier(t.getWeapon()));
            }
        }
    }

}