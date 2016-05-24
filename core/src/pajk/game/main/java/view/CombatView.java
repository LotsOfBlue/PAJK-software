package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.TimeUtils;
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
    private SpriteBatch spriteBatch;
    private GameModel gameModel;
    private Board board;

    private float animationTime = 1.6f;
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

    private long timeStamp;
    private Animation activeUnitAnimation;
    private Animation targetUnitAnimation;
    private Texture gridTexture;
    private Texture hpBar;
    private HashMap<String, Animation> unitAnimationHashMap = new HashMap<>();

    private BitmapFont font;

    /**
     * Constructor of CombatView, initializes the class and get all required
     */
    public CombatView (){
        gameModel = GameModel.getInstance();

        font = new BitmapFont();
        font.getData().setScale(2f,2f);
        gridTexture = new Texture("Sprites/Tiles/gridOverlay64.png");
        hpBar = new Texture("Sprites/Units/hpbarBlue.png");
    }

    private Animation createAnimationFrom(String filePath){
        if(unitAnimationHashMap.isEmpty() || !unitAnimationHashMap.containsKey(filePath)){
            Texture tempTexture = new Texture(filePath);
            int tileSide = ViewUtils.TILE_WIDTH;
            float animationDuration = animationTime/2f;
            TextureRegion[][] tempTextureRegions = TextureRegion.split(tempTexture, tileSide, tileSide);
            TextureRegion[] tempTextureRegion = new TextureRegion[tempTextureRegions.length * tempTextureRegions[0].length];
            int index = 0;
            for (int i = 0; i < tempTextureRegions.length; i++) {
                for (int j = 0; j < tempTextureRegions[i].length; j++) {
                    tempTextureRegion[index++] = tempTextureRegions[i][j];
                }
                index = 0;
            }
            float frameDuration = animationDuration / tempTextureRegion.length;
            unitAnimationHashMap.put(filePath, new Animation(frameDuration, tempTextureRegion));
        }
        return unitAnimationHashMap.get(filePath);
    }

    public void render(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;
        if(gameModel.getState() instanceof CombatState){
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

        timeStamp = TimeUtils.millis();

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

        float frame = (TimeUtils.millis() - timeStamp) / 1000f;

        switch (combatDrawState) {
            case ACTIVE_FIRST_HIT:
                //Are we done?
                if(frame >= animationTime){
                    combatDrawState = CombatDrawState.ENEMY_HIT;
                    gameModel.performAction(ActionName.COMBAT_ACTIVE_HIT);
                    timeStamp = TimeUtils.millis();
                    break;
                }
                //else do the painting
                if(frame < animationTime/2f){
                    drawAttackAnimation(activeUnit, frame);
                }else {
                    drawAttackAnimation(activeUnit, 0);
                }
                if(frame >= animationTime/2f){
                    drawDamageNumber(activeUnit, frame);
                }
                drawAttackAnimation(targetUnit, 0);
                break;
            case ENEMY_HIT:
                if(frame >= animationTime || !attackFromEnemyUnit){
                    combatDrawState = CombatDrawState.ACTIVE_SECOND_HIT;
                    gameModel.performAction(ActionName.COMBAT_TARGET_HIT);
                    timeStamp = TimeUtils.millis();
                    break;
                }
                if(frame < animationTime/2f){
                    drawAttackAnimation(targetUnit, frame);
                }else {
                    drawAttackAnimation(targetUnit, 0);
                }
                if(frame >= animationTime/2f){
                    drawDamageNumber(targetUnit, frame);
                }
                drawAttackAnimation(activeUnit, 0);
                break;
            case ACTIVE_SECOND_HIT:
                if(frame >= animationTime || !secondAttackFromActiveUnit){
                    combatDrawState = CombatDrawState.ACTIVE_FIRST_HIT;
                    gameModel.performAction(ActionName.COMBAT_DONE);
                    reset();
                    break;
                }
                if(frame < animationTime/2f){
                    drawAttackAnimation(activeUnit, frame);
                }else {
                    drawAttackAnimation(activeUnit, 0);
                }
                if(frame >= animationTime/2f){
                    drawDamageNumber(activeUnit, frame);
                }
                drawAttackAnimation(targetUnit, 0);
                break;
        }
    }

    private void drawAttackAnimation(Unit unit, float frame){
        drawTile(board.getPos(unit));
        TextureRegion textureRegion;
        boolean isFlip = false;
        if(unit == activeUnit){
            textureRegion = activeUnitAnimation.getKeyFrame(frame);
            if(calcDrawPos(activeUnit)[0] < calcDrawPos(targetUnit)[0]){
                isFlip = true;
            }

        } else {
            textureRegion = targetUnitAnimation.getKeyFrame(frame);
            if(calcDrawPos(targetUnit)[0] < calcDrawPos(activeUnit)[0]){
                isFlip = true;
            }
        }
        if(isFlip && !textureRegion.isFlipX()){
            textureRegion.flip(true, false);
        } else if(!isFlip && textureRegion.isFlipX()) {
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

        TextureRegion txtReg = new TextureRegion(texture, 0, 0, ViewUtils.TILE_WIDTH, ViewUtils.TILE_WIDTH-hpBar.getHeight());

        draw(txtReg, pos[0], pos[1]);
        draw(gridTexture, pos[0], pos[1]);
    }

    private void drawDamageNumber(Unit unit, float frame){
        float uPos[] = {0f,0f};

        float scale = frame * 1.1f;
        font.getData().setScale(scale);
        font.setColor(Color.BLACK);
        String message = "null";
        if(unit.equals(activeUnit)){
            uPos = calcDrawPos(targetUnit);
            if(combatDrawState == CombatDrawState.ACTIVE_FIRST_HIT){

                if(firstHitFromActiveUnit){
                    if(firstCritFromActiveUnit){
                        draw("CRIT", uPos[0]+ViewUtils.TILE_WIDTH/3, uPos[1]+ (ViewUtils.TILE_WIDTH*1.5f));
                    }
                    message = ""+firstDamageFromActiveUnit;
                }else{
                    message = "MISS";
                }

            }else if(secondAttackFromActiveUnit){
                if(secondHitFromActiveUnit){
                    if(secondCritFromActiveUnit){
                        draw("CRIT", uPos[0]+ViewUtils.TILE_WIDTH/3, uPos[1] + (ViewUtils.TILE_WIDTH*1.5f));
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
                    draw("CRIT", uPos[0]+ViewUtils.TILE_WIDTH/3, uPos[1] + (ViewUtils.TILE_WIDTH*1.5f));
                }
                message = ""+damageFromEnemyUnit;
            }else{
                message = "MISS";
            }
        }
        if(!message.equals("null")){
            draw(message, uPos[0]+ViewUtils.TILE_WIDTH/3, uPos[1]+ViewUtils.TILE_WIDTH * 1.2f);
        }
    }

    private float[] calcDrawPos(Unit unit){
        float uPos[] = new float[2];
        Tile myTile = board.getPos(unit);
        uPos[0] = (myTile.getX() ) * ViewUtils.TILE_WIDTH;
        uPos[1] = (board.getBoardHeight() - myTile.getY() - 1) * ViewUtils.TILE_WIDTH;
        return uPos;
    }

    private float[] calcDrawPos(Tile tile){
        float uPos[] = new float[2];
        uPos[0] = (tile.getX() ) * ViewUtils.TILE_WIDTH;
        uPos[1] = (board.getBoardHeight() - tile.getY() - 1) * ViewUtils.TILE_WIDTH;
        return uPos;
    }

    private void drawAttackFrame(Unit unit, TextureRegion textureRegion){
        float uPos[] = calcDrawPos(unit);
        draw(textureRegion, uPos[0], uPos[1]);
    }

    private void draw(Texture texture, float xPos, float yPos){
        spriteBatch.draw(texture, xPos, yPos);
    }

    private void draw(TextureRegion textureRegion, float xPos, float yPos){
        spriteBatch.draw(textureRegion,xPos,yPos);
    }

    private void draw(String str, float xPos, float yPos){
        font.draw(spriteBatch, str, xPos, yPos);
    }

    private void reset(){
        isUpdated = false;

    }
}
