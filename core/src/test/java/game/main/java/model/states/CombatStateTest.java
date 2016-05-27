package game.main.java.model.states;

import org.junit.Before;
import org.junit.Test;
import game.main.java.model.Board;
import game.main.java.model.GameModel;
import game.main.java.model.items.IronSword;
import game.main.java.model.items.Sword;
import game.main.java.model.units.Unit;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by jonatan on 26/05/2016.
 */
public class CombatStateTest {

    private class TestUnit extends Unit {

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
        public TestSword(int accuracy, int critChance){
            name = "Test Sword";
            this.minRange = 1;
            this.maxRange = 1;
            this.damage = random.nextInt(10);
            this.critChance = critChance;
            this.accuracy = accuracy;
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
    public void performAction() throws Exception {
        TestUnit unitA = new TestUnit(1);
        unitA.setWeapon(new TestSword(1000, 1000));
        board.getTile(0, 0).setUnit(unitA);
        TestUnit unitB = new TestUnit(1);
        unitB.setWeapon(new TestSword(0, 0));
        board.getTile(0, 1).setUnit(unitB);
        gameModel.setActiveUnit(unitA);
        gameModel.setTargetUnit(unitB);

        int aHits = 0;
        int aCrits = 0;
        int rounds = 100000;

        for(int i = 0; i < rounds; i++){
            gameModel.setState(GameModel.StateName.COMBAT);
            CombatState cS = (CombatState)gameModel.getState();
            if(cS.isFirstHitFromActiveUnit()){
                aHits++;
            }
            if(cS.isFirstCritFromActiveUnit()){
                aCrits++;
            }

        }
        assertTrue("CombatState does something wrong when hit chance is to big!", aHits == rounds);
        assertTrue("CombatState does something wrong when crit chance is to big!", aCrits == rounds);

        unitA.setWeapon(new TestSword(0, 0));
        aHits = 0;
        aCrits = 0;
        for(int i = 0; i < rounds; i++){
            gameModel.setState(GameModel.StateName.COMBAT);
            CombatState cS = (CombatState)gameModel.getState();
            if(cS.isFirstHitFromActiveUnit()){
                aHits++;
            }
            if(cS.isFirstCritFromActiveUnit()){
                aCrits++;
            }

        }


    }

    @Test
    public void activate() throws Exception {

    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void isAttackFromEnemyUnit() throws Exception {

    }

    @Test
    public void isSecondAttackFromActiveUnit() throws Exception {

    }

    @Test
    public void getDamageFromEnemyUnit() throws Exception {

    }

    @Test
    public void getSecondDamageFromActiveUnit() throws Exception {

    }

    @Test
    public void getFirstDamageFromActiveUnit() throws Exception {

    }

    @Test
    public void isCalcDone() throws Exception {

    }

    @Test
    public void isCritFromEnemyUnit() throws Exception {

    }

    @Test
    public void isFirstCritFromActiveUnit() throws Exception {

    }

    @Test
    public void isFirstHitFromActiveUnit() throws Exception {

    }

    @Test
    public void isHitFromEnemyUnit() throws Exception {

    }

    @Test
    public void isSecondCritFromActiveUnit() throws Exception {

    }

    @Test
    public void isSecondHitFromActiveUnit() throws Exception {

    }

}