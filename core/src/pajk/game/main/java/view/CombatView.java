package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.StringBuilder;
import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;

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
    private boolean secondAttackFromActiveUnit = false;
    private boolean attackFromEnemyUnit = false;

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

    private BitmapFont bitmapFont;

    public CombatView (){

        this.gameModel = GameModel.getInstance();

        board = gameModel.getBoard();

        bitmapFont = new BitmapFont();

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
        secondAttackFromActiveUnit = combatState.isSecondAttackFromActiveUnit();
        attackFromEnemyUnit = combatState.isAttackFromEnemyUnit();
    }

    private void drawCombat(){


        float frame = 0f;
        if (animationClock<30) {
            frame = animationClock/100f;
            animationClock++;
        }

        switch (combatDrawState) {
            case ACTIVE_FIRST_HIT:
                if(animationClock == 30){
                    combatDrawState = CombatDrawState.ENEMY_HIT;
                    animationClock = 0;
                }
                drawAttackAnimation(activeUnit, frame);
                break;
            case ENEMY_HIT:
                if(animationClock == 30 || !attackFromEnemyUnit){
                    combatDrawState = CombatDrawState.ACTIVE_SECOND_HIT;
                    animationClock = 0;
                }
                drawAttackAnimation(enemyUnit, frame);
                break;
            case ACTIVE_SECOND_HIT:
                if(animationClock == 30 || !secondAttackFromActiveUnit){
                    combatDrawState = CombatDrawState.ACTIVE_FIRST_HIT;
                    animationClock = 0;
                    done = true;
                    flush();
                }
                drawAttackAnimation(activeUnit, frame);
                break;
        }

    }




    private void drawDamageNumber(Unit unit, float frame){
        float uPos[] = calcUnitDrawPos(unit);
        if(unit.equals(activeUnit)){
            if(combatDrawState == CombatDrawState.ACTIVE_FIRST_HIT){
                if(firstHitFromActiveUnit){
                    bitmapFont.setColor(Color.FIREBRICK);
                    if(firstCritFromActiveUnit){
                        bitmapFont.setColor(Color.ROYAL);
                    }
                    draw(uPos[0]+frame,uPos[1]+frame,""+firstDamageFromActiveUnit);
                }else{
                    bitmapFont.setColor(Color.SCARLET);
                    draw(uPos[0]+frame,uPos[1]+frame,"MISS");
                }

            }else if(secondAttackFromActiveUnit){
                if(secondHitFromActiveUnit){
                    bitmapFont.setColor(Color.FIREBRICK);
                    if(secondCritFromActiveUnit){
                        bitmapFont.setColor(Color.ROYAL);
                    }
                    draw(uPos[0]+frame,uPos[1]+frame,""+secondDamageFromActiveUnit);
                }else{
                    bitmapFont.setColor(Color.SCARLET);
                    draw(uPos[0]+frame,uPos[1]+frame,"MISS");
                }
            }
        }else if(attackFromEnemyUnit){
            if(hitFromEnemyUnit){
                bitmapFont.setColor(Color.FIREBRICK);
                if(critFromEnemyUnit){
                    bitmapFont.setColor(Color.ROYAL);
                }
                draw(uPos[0]+frame,uPos[1]+frame,""+damageFromEnemyUnit);
            }else{
                bitmapFont.setColor(Color.SCARLET);
                draw(uPos[0]+frame,uPos[1]+frame,"MISS");
            }
        }
    }

    private void drawAttackAnimation(Unit unit, float frame){
        drawDamageNumber(unit, frame);
        Unit.UnitClass unitClass = unit.getUnitClass();
        switch (unitClass){
            case SWORD:
                if(unit.getAllegiance() == Unit.Allegiance.PLAYER){
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

    private float[] calcUnitDrawPos(Unit unit){
        float uPos[] = new float[2];
        Tile myTile = board.getPos(unit);
        uPos[0] = (myTile.getX() ) * TILE_WIDTH;
        uPos[1] = (board.getBoardHeight() - myTile.getY() - 1) * TILE_WIDTH;
        return uPos;

    }

    private void drawAttackFrame(Unit unit, TextureRegion textureRegion){
        float uPos[] = calcUnitDrawPos(unit);
        draw( uPos[0], uPos[1], textureRegion);
    }


    private void draw(float xPos, float yPos, TextureRegion textureRegion){
        spriteBatch.draw(textureRegion,xPos,yPos);
    }

    private void draw(float xPos, float yPos, String str){
        bitmapFont.draw(spriteBatch, str, xPos, yPos);
    }

    private void flush(){


    }
}
