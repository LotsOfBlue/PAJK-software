package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pajk.game.main.java.model.GameModel;

/**
 * Created by jonatan on 06/05/2016.
 */
public class CombatView extends AbstractGameView {
    private boolean done = false;
    private final int TILE_WIDTH = 64;

    private SpriteBatch spriteBatch;
    private GameModel gameModel;
    private Board board;

    private int animationClock = 0;


    private Unit activeUnit;
    private Unit enemyUnit;

    private int firstDamageFromActiveUnit = 0;
    private boolean firstHitFromActiveUnit = false;
    private boolean firstCritFromActiveUnit = false;
    private int secondDamageFromActiveUnit = 0;
    private boolean secondHitFromActiveUnit = false;
    private boolean secondCritFromActiveUnit = false;
    private int damageFromEnemyUnit = 0;
    private boolean hitFromEnemyUnit = false;
    private boolean critFromEnemyUnit = false;

    private enum CombatDrawState{
        ACTIVE_FIRST_HIT,
        ENEMY_HIT,
        ACTIVE_SECOND_HIT

    }
    private CombatDrawState combatDrawState = CombatDrawState.ACTIVE_FIRST_HIT;


    Texture tileSprite;
    Texture enemySprite;
    Texture activeSprite;

    private Texture redSwordUnitSheet;
    private TextureRegion redSwordUnitFrames[];
    private Animation redSwordUnitAnimation;

    public CombatView (){

        this.gameModel = GameModel.getInstance();

        board = gameModel.getBoard();

        //Red sword unit
        redSwordUnitAnimation = new Animation(new Float(2) ,new TextureRegion(new Texture("unit-sprite")));
        redSwordUnitSheet = new Texture("unit-sprite-combat");
        TextureRegion[][] tmp = TextureRegion.split(redSwordUnitSheet, redSwordUnitSheet.getWidth()/4, redSwordUnitSheet.getHeight()/1);
        redSwordUnitFrames = new TextureRegion[4 * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                redSwordUnitFrames[index++] = tmp[i][j];
            }
        }
        redSwordUnitAnimation = new Animation(0.05f, redSwordUnitFrames);
        //end ed sword unit


    }
    public void render(SpriteBatch spriteBatch){

        this.spriteBatch = spriteBatch;
        spriteBatch.begin();
        //TODO drawFunc
        drawCombat();
        spriteBatch.end();
        if(done){ gameModel.performAction(ActionName.COMBAT_DONE); }

    }

    public void update(float deltaTime){
        //TODO nothing?
        done = false;
        activeUnit = gameModel.getActiveUnit();
        enemyUnit = gameModel.getTargetUnit();

        CombatState combatState = (CombatState)gameModel.getState();
        firstDamageFromActiveUnit = combatState.getFirstDamageFromActiveUnit();
        firstHitFromActiveUnit = combatState.isFirstHitFromActiveUnit();
        firstCritFromActiveUnit = combatState.isSecondCritFromActiveUnit();
        secondDamageFromActiveUnit = combatState.getSecondDamageFromActiveUnit();
        secondHitFromActiveUnit = combatState.isSecondHitFromActiveUnit();
        secondCritFromActiveUnit = combatState.isSecondCritFromActiveUnit();
        damageFromEnemyUnit = combatState.getDamageFromEnemyUnit();
        hitFromEnemyUnit = combatState.isHitFromEnemyUnit();
        critFromEnemyUnit = combatState.isCritFromEnemyUnit();
    }

    private void drawCombat(){


        float frame = 0f;
        if (animationClock<30) {

            /*if (animationClock < 7) {
                frame = 0.00f;
            } else if (animationClock < 14) {
                frame = 0.05f;
            } else if (animationClock < 21) {
                frame = 0.1f;
            } else {
                frame = 0.15f;
            }*/
            frame = animationClock/100f;
            animationClock++;
        }

        switch (combatDrawState) {
            case ACTIVE_FIRST_HIT:
                drawAttackAnimation(activeUnit, frame);
                if(animationClock == 30){
                    combatDrawState = CombatDrawState.ENEMY_HIT;
                    animationClock = 0;
                }
                break;
            case ENEMY_HIT:
                drawAttackAnimation(enemyUnit, frame);
                if(animationClock == 30){
                    combatDrawState = CombatDrawState.ACTIVE_SECOND_HIT;
                    animationClock = 0;
                }
                break;
            case ACTIVE_SECOND_HIT:
                drawAttackAnimation(activeUnit, frame);
                if(animationClock == 30 || !secondHitFromActiveUnit){
                    combatDrawState = CombatDrawState.ACTIVE_FIRST_HIT;
                    animationClock = 0;
                    done = true;
                    flush();
                }
                break;
        }

    }






    private void drawAttackAnimation(Unit unit, float frame){
        Unit.UnitClass unitClass = unit.getUnitClass();
        switch (unitClass){
            case SWORD:
                if(unit.getAllegiance() == Unit.Allegiance.HUMAN){
                    drawAttackFrame(unit, redSwordUnitAnimation.getKeyFrame(frame));
                }else{
                    drawAttackFrame(unit, redSwordUnitAnimation.getKeyFrame(frame));
                }

                break;
            case AXE:
                break;
            case PIKE:
                break;
        }

    }

    private void drawAttackFrame(Unit unit, TextureRegion textureRegion){
        Tile myTile = board.getPos(unit);
        int xPos = (myTile.getX() ) * TILE_WIDTH;
        int yPos = (board.getBoardHeight() - myTile.getY() - 1) * TILE_WIDTH;
        draw( xPos, yPos, textureRegion);
    }


    private void draw( int xPos, int yPos, TextureRegion textureRegion){
        spriteBatch.draw(textureRegion,xPos,yPos);
    }

    private void flush(){


    }
}
