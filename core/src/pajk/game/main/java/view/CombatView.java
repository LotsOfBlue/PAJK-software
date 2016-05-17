package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.GameModel;

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





    private Animation redSwordUnitAnimation;
    private Animation blueSwordUnitAnimation;
    private Animation redBowUnitAnimation;
    private Animation blueBowUnitAnimation;

    private BitmapFont bitmapFont;

    /**
     * Construktor of CombatView, initializes the class and get all requierd
     */
    public CombatView (){

        this.gameModel = GameModel.getInstance();

        board = gameModel.getBoard();

        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(1.5f,1.5f);




        blueSwordUnitAnimation = getAnimationFrom(new Texture("blue-sword-animation"));

        redSwordUnitAnimation = getAnimationFrom(new Texture("red-sword-animation"));

        blueBowUnitAnimation = getAnimationFrom(new Texture("blue-bow-animation"));

        redBowUnitAnimation = getAnimationFrom(new Texture("red-bow-animation"));



    }

    private Animation getAnimationFrom(Texture texture){
        Texture tempTexture;
        TextureRegion tempTextureRegion[];
        TextureRegion tempTextureRegions[][];
        int width;
        int height;

        tempTexture = texture;
        width = tempTexture.getWidth()/(tempTexture.getWidth()/TILE_WIDTH);
        height = tempTexture.getHeight()/(tempTexture.getHeight()/TILE_WIDTH);
        tempTextureRegions = TextureRegion.split(tempTexture, width, height);
        tempTextureRegion = new TextureRegion[width * height];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                tempTextureRegion[index++] = tempTextureRegions[i][j];
            }
        }
        return new Animation(0.075f, tempTextureRegion);

    }


    public void render(SpriteBatch spriteBatch){

//        System.out.println("drawing combat");//TODO remove
        this.spriteBatch = spriteBatch;
        spriteBatch.begin();
        //TODO drawFunc
        drawCombat();
        spriteBatch.end();



    }

    public void update(float deltaTime){
        //TODO nothing?

        activeUnit = gameModel.getActiveUnit();
        enemyUnit = gameModel.getTargetUnit();

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
                drawAttackAnimation(enemyUnit, frame);
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
        drawDamageNumber(unit, frame);
        Unit.UnitClass unitClass = unit.getUnitClass();
        switch (unitClass){
            case SWORD:
                if(unit.getAllegiance() == Unit.Allegiance.PLAYER){
                    drawAttackFrame(unit, blueSwordUnitAnimation.getKeyFrame(frame));
                }else{
                    drawAttackFrame(unit, redSwordUnitAnimation.getKeyFrame(frame));
                }

                break;
            case AXE:
                break;
            case PIKE:
                break;
            case BOW:
                if(unit.getAllegiance() == Unit.Allegiance.PLAYER){
                    drawAttackFrame(unit, blueBowUnitAnimation.getKeyFrame(frame));
                }else{
                    drawAttackFrame(unit, redBowUnitAnimation.getKeyFrame(frame));
                }
                break;

        }

    }

    private void drawDamageNumber(Unit unit, float frame){
        float uPos[] = {0f,0f};
        float scale = animationClock/30f;
        bitmapFont.getData().setScale(scale);
        String message = "null";
        if(unit.equals(activeUnit)){
            uPos = calcUnitDrawPos(enemyUnit);
            if(combatDrawState == CombatDrawState.ACTIVE_FIRST_HIT){

                if(firstHitFromActiveUnit){
                    bitmapFont.setColor(Color.FIREBRICK);
                    if(firstCritFromActiveUnit){
                        bitmapFont.setColor(Color.ROYAL);
                    }
                    message = ""+firstDamageFromActiveUnit;
                }else{
                    bitmapFont.setColor(Color.SCARLET);
                    message = "MISS";
                }

            }else if(secondAttackFromActiveUnit){
                if(secondHitFromActiveUnit){
                    bitmapFont.setColor(Color.FIREBRICK);
                    if(secondCritFromActiveUnit){
                        bitmapFont.setColor(Color.ROYAL);
                    }
                    message = ""+secondDamageFromActiveUnit;
                }else{
                    bitmapFont.setColor(Color.SCARLET);
                    message = "MISS";
                }
            }
        }else if(attackFromEnemyUnit){
            uPos = calcUnitDrawPos(activeUnit);
            if(hitFromEnemyUnit){
                bitmapFont.setColor(Color.FIREBRICK);
                if(critFromEnemyUnit){
                    bitmapFont.setColor(Color.ROYAL);
                }
                message = ""+damageFromEnemyUnit;
            }else{
                bitmapFont.setColor(Color.SCARLET);
                message = "MISS";
            }
        }
        draw(uPos[0]+TILE_WIDTH/3,uPos[1]+TILE_WIDTH,message);
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
