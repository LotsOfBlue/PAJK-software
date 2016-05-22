package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import pajk.game.PajkGdxGame;
import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.CombatState;
import pajk.game.main.java.model.units.*;

import java.sql.Time;

/**
 * Visual representation of the combat.
 * Gets values from CombatState
 */
public class CombatView extends AbstractGameView {
    private final int TILE_WIDTH = 64; //TODO make global tile width?

    private SpriteBatch spriteBatch;
    private GameModel gameModel;
    private Board board;

    private int animationClock = 0;
    private boolean isUpdated = false;

    private Unit activeUnit;
    private Unit targetUnit;

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


    private Animation activeUnitAnimation;
    private Animation targetUnitAnimation;
    private Texture gridTexture;


    private BitmapFont bitmapFont;

    /**
     * Construktor of CombatView, initializes the class and get all requierd
     */
    public CombatView (){

        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(2f,2f);

        gridTexture = new Texture("gridOverlay64.png");

    }

    private Animation getAnimationFrom(String filePath){
        Texture tempTexture = new Texture(filePath);
        int width = tempTexture.getWidth()/(tempTexture.getWidth()/TILE_WIDTH);
        int height = tempTexture.getHeight()/(tempTexture.getHeight()/TILE_WIDTH);
        TextureRegion[][] tempTextureRegions = TextureRegion.split(tempTexture, width, height);
        TextureRegion[] tempTextureRegion = new TextureRegion[width * height];
        int index = 0;
        for (int i = 0; i < tempTextureRegions.length; i++) {
            for (int j = 0; j < tempTextureRegions[i].length; j++) {
                tempTextureRegion[index++] = tempTextureRegions[i][j];
            }
            index = 0;
        }
        //TODO understand animation time float...
        return new Animation(0.075f, tempTextureRegion);
    }


    public void render(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;
        gameModel = GameModel.getInstance();
        if(gameModel.getState().getClass() == CombatState.class){
            CombatState combatState = (CombatState)gameModel.getState();
            if (combatState.isCalcDone()){
                if(!isUpdated){
                    updateVariables();
                }
                spriteBatch.begin();
                drawCombat();
                spriteBatch.end();
            }
        }




    }

    private void updateVariables(){
        isUpdated = true;
        board = gameModel.getBoard();
        activeUnit = gameModel.getActiveUnit();
        targetUnit = gameModel.getTargetUnit();
        activeUnitAnimation = getAnimationFrom(activeUnit.getAnimationFilePath());
        targetUnitAnimation = getAnimationFrom(targetUnit.getAnimationFilePath());


        CombatState combatState = (CombatState)gameModel.getState();
        firstDamageFromActiveUnit = combatState.getFirstDamageFromActiveUnit();
        firstHitFromActiveUnit = combatState.isFirstHitFromActiveUnit();
        firstCritFromActiveUnit = combatState.isFirstCritFromActiveUnit();
        secondDamageFromActiveUnit = combatState.getSecondDamageFromActiveUnit();
        secondHitFromActiveUnit = combatState.isSecondHitFromActiveUnit();
        secondCritFromActiveUnit = combatState.isSecondCritFromActiveUnit();
        damageFromEnemyUnit = combatState.getDamageFromEnemyUnit();
        hitFromEnemyUnit = combatState.isHitFromEnemyUnit();
        critFromEnemyUnit = combatState.isCritFromEnemyUnit();
        secondAttackFromActiveUnit = combatState.isSecondAttackFromActiveUnit();
        attackFromEnemyUnit = combatState.isAttackFromEnemyUnit();
    }

    public void update(float deltaTime){
        //TODO nothing?


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
                    gameModel.performAction(ActionName.COMBAT_ACTIVE_HIT);
                    break;
                }
                drawAttackAnimation(activeUnit, frame);
                break;
            case ENEMY_HIT:
                if(animationClock == 30 || !attackFromEnemyUnit){
                    combatDrawState = CombatDrawState.ACTIVE_SECOND_HIT;
                    animationClock = 0;
                    gameModel.performAction(ActionName.COMBAT_TARGET_HIT);
                    break;
                }
                drawAttackAnimation(targetUnit, frame);
                break;
            case ACTIVE_SECOND_HIT:
                if(animationClock == 30 || !secondAttackFromActiveUnit){
                    combatDrawState = CombatDrawState.ACTIVE_FIRST_HIT;
                    animationClock = 0;
                    gameModel.performAction(ActionName.COMBAT_DONE);
                    flush();
                    break;
                }
                drawAttackAnimation(activeUnit, frame);
                break;
        }

    }




    private void drawAttackAnimation(Unit unit, float frame){
        drawTile(board.getPos(unit));
        drawDamageNumber(unit, frame);
        TextureRegion textureRegion;
        if(unit  == activeUnit){
            textureRegion = activeUnitAnimation.getKeyFrame(frame);
            if((calcDrawPos(activeUnit)[0] < calcDrawPos(targetUnit)[0]) && (!textureRegion.isFlipX())){
                textureRegion.flip(true, false);
            } else if(textureRegion.isFlipX()){
                textureRegion.flip(false, false);
            }
            drawAttackFrame(unit, textureRegion);
        }else{
            textureRegion = targetUnitAnimation.getKeyFrame(frame);
            if((calcDrawPos(targetUnit)[0] < calcDrawPos(activeUnit)[0]) && (!textureRegion.isFlipX())){
                textureRegion.flip(true, false);
            } else if(textureRegion.isFlipX()){
                textureRegion.flip(false, false);
            }
            drawAttackFrame(unit, textureRegion);
        }

    }

    /**
     * Checks what terrain the tile has and draws it.
     * @param tile the tile to be drawn.
     */
    private void drawTile(Tile tile){
        Texture texture = ViewUtils.getTileTexture(tile);
        float[] pos = calcDrawPos(tile);
        draw(texture, pos[0], pos[1]);
        draw(gridTexture, pos[0], pos[1]);

    }

    private void drawDamageNumber(Unit unit, float frame){
        float uPos[] = {0f,0f};
        float scale = animationClock/30f;
        bitmapFont.getData().setScale(scale);
        bitmapFont.setColor(Color.SCARLET);
        String message = "null";
        if(unit.equals(activeUnit)){
            uPos = calcDrawPos(targetUnit);
            if(combatDrawState == CombatDrawState.ACTIVE_FIRST_HIT){

                if(firstHitFromActiveUnit){
                    if(firstCritFromActiveUnit){
                        draw(uPos[0]+TILE_WIDTH/3,uPos[1]+ (TILE_WIDTH*1.5f), "CRIT");
                    }
                    message = ""+firstDamageFromActiveUnit;
                }else{
                    message = "MISS";
                }

            }else if(secondAttackFromActiveUnit){
                if(secondHitFromActiveUnit){
                    if(secondCritFromActiveUnit){
                        draw(uPos[0]+TILE_WIDTH/3,uPos[1] + (TILE_WIDTH*1.5f),"CRIT");
                    }
                    message = ""+secondDamageFromActiveUnit;
                }else{
                    message = "MISS";
                }
            }
        }else if(attackFromEnemyUnit){
            uPos = calcDrawPos(activeUnit);
            if(hitFromEnemyUnit){
                if(critFromEnemyUnit){
                    draw(uPos[0]+TILE_WIDTH/3,uPos[1] + (TILE_WIDTH*1.5f),"CRIT");
                }
                message = ""+damageFromEnemyUnit;
            }else{
                message = "MISS";
            }
        }
        draw(uPos[0]+TILE_WIDTH/3,uPos[1]+TILE_WIDTH,message);
    }

    private float[] calcDrawPos(Unit unit){
        float uPos[] = new float[2];
        Tile myTile = board.getPos(unit);
        uPos[0] = (myTile.getX() ) * TILE_WIDTH;
        uPos[1] = (board.getBoardHeight() - myTile.getY() - 1) * TILE_WIDTH;
        return uPos;

    }
    private float[] calcDrawPos(Tile tile){
        float uPos[] = new float[2];
        uPos[0] = (tile.getX() ) * TILE_WIDTH;
        uPos[1] = (board.getBoardHeight() - tile.getY() - 1) * TILE_WIDTH;
        return uPos;

    }

    private void drawAttackFrame(Unit unit, TextureRegion textureRegion){
        float uPos[] = calcDrawPos(unit);
        draw(textureRegion, uPos[0], uPos[1]);
    }

    private void draw(Texture texture, float xPos, float yPos){
        spriteBatch.draw(texture,xPos,yPos);
    }

    private void draw(TextureRegion textureRegion, float xPos, float yPos){
        spriteBatch.draw(textureRegion,xPos,yPos);
    }

    private void draw(float xPos, float yPos, String str){
        bitmapFont.draw(spriteBatch, str, xPos, yPos);
    }

    private void flush(){
        isUpdated = false;

    }
}
