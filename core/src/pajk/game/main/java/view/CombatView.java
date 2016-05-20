package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.CombatState;
import pajk.game.main.java.model.units.Unit;

import java.util.HashMap;

/**
 * Visual representation of the combat.
 * Gets values from CombatState
 */
public class CombatView extends AbstractGameView {
    private final int TILE_WIDTH = 64; //TODO make global tile width?

    private SpriteBatch spriteBatch;
    private GameModel gameModel;
    private Board board;

    private int cooldown = 60;
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
    private Texture hpBar;
    private HashMap<String, Animation> animationHashMap = new HashMap<>();


    private BitmapFont bitmapFont;

    /**
     * Construktor of CombatView, initializes the class and get all requierd
     */
    public CombatView (){

        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(2f,2f);

        gridTexture = new Texture("Sprites/Tiles/gridOverlay64.png");
        hpBar = new Texture("Sprites/Units/hpbarBlue.png");

    }

    private Animation createAnimationFrom(String filePath){
        if(animationHashMap.isEmpty() || !animationHashMap.containsKey(filePath)){
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
            animationHashMap.put(filePath, new Animation(0.075f, tempTextureRegion));
        }
        return animationHashMap.get(filePath);

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
        activeUnitAnimation = createAnimationFrom(activeUnit.getAnimationFilePath());
        targetUnitAnimation = createAnimationFrom(targetUnit.getAnimationFilePath());


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
                //Are we done?
                if(animationClock == 30 && cooldown <= 0){
                    combatDrawState = CombatDrawState.ENEMY_HIT;
                    animationClock = 0;
                    cooldown = 60;
                    gameModel.performAction(ActionName.COMBAT_ACTIVE_HIT);
                    break;
                }
                //else do the painting
                drawAttackAnimation(activeUnit, frame);
                drawDamageNumber(activeUnit, frame);
                drawAttackAnimation(targetUnit, 0);
                break;
            case ENEMY_HIT:
                if((animationClock == 30 && cooldown <= 0) || !attackFromEnemyUnit){
                    combatDrawState = CombatDrawState.ACTIVE_SECOND_HIT;
                    animationClock = 0;
                    cooldown = 60;
                    gameModel.performAction(ActionName.COMBAT_TARGET_HIT);
                    break;
                }

                drawAttackAnimation(targetUnit, frame);
                drawDamageNumber(targetUnit, frame);
                drawAttackAnimation(activeUnit, 0);
                break;
            case ACTIVE_SECOND_HIT:
                if((animationClock == 30 && cooldown <= 0) || !secondAttackFromActiveUnit){
                    combatDrawState = CombatDrawState.ACTIVE_FIRST_HIT;
                    animationClock = 0;
                    cooldown = 60;
                    gameModel.performAction(ActionName.COMBAT_DONE);
                    reset();
                    break;
                }

                drawAttackAnimation(activeUnit, frame);
                drawDamageNumber(activeUnit, frame);
                drawAttackAnimation(targetUnit, 0);
                break;
        }
        cooldown--;
    }




    private void drawAttackAnimation(Unit unit, float frame){
        drawTile(board.getPos(unit));
        TextureRegion textureRegion;
        boolean isFlip = false;
        if(unit  == activeUnit){
            textureRegion = activeUnitAnimation.getKeyFrame(frame);
            if(calcDrawPos(activeUnit)[0] < calcDrawPos(targetUnit)[0]){
                isFlip = true;
            }

        }else{
            textureRegion = targetUnitAnimation.getKeyFrame(frame);
            if(calcDrawPos(targetUnit)[0] < calcDrawPos(activeUnit)[0]){
                isFlip = true;
            }
        }
        if(isFlip && !textureRegion.isFlipX()){
            textureRegion.flip(true, false);
        } else if(!isFlip && textureRegion.isFlipX()){
            textureRegion.flip(true, false);
        }

        drawAttackFrame(unit, textureRegion);
    }

    /**
     * Checks what terrain the tile has and draws it.
     * @param tile the tile to be drawn.
     */
    private void drawTile(Tile tile){
        Texture texture = ViewUtils.getTileTexture(tile);
        float[] pos = calcDrawPos(tile);

        TextureRegion txtReg = new TextureRegion(texture, 0, 0, TILE_WIDTH, TILE_WIDTH-hpBar.getHeight());

        draw(txtReg, pos[0], pos[1]);
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
                        draw("CRIT", uPos[0]+TILE_WIDTH/3, uPos[1]+ (TILE_WIDTH*1.5f));
                    }
                    message = ""+firstDamageFromActiveUnit;
                }else{
                    message = "MISS";
                }

            }else if(secondAttackFromActiveUnit){
                if(secondHitFromActiveUnit){
                    if(secondCritFromActiveUnit){
                        draw("CRIT", uPos[0]+TILE_WIDTH/3, uPos[1] + (TILE_WIDTH*1.5f));
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
                    draw("CRIT", uPos[0]+TILE_WIDTH/3, uPos[1] + (TILE_WIDTH*1.5f));
                }
                message = ""+damageFromEnemyUnit;
            }else{
                message = "MISS";
            }
        }
        draw(message, uPos[0]+TILE_WIDTH/3, uPos[1]+TILE_WIDTH * 1.2f);
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

    private void draw(String str, float xPos, float yPos){
        bitmapFont.draw(spriteBatch, str, xPos, yPos);
    }

    private void reset(){
        isUpdated = false;

    }
}
