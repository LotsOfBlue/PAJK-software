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
     * Constructor of CombatView, initializes the class and get all requiered
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
            int tileSize = ViewUtils.TILE_WIDTH;
            float animationDuration = animationTime/2f;
            TextureRegion[][] tempTextureRegions = TextureRegion.split(tempTexture, tileSize, tileSize);
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

        switch (combatDrawState) {
            case ACTIVE_FIRST_HIT:
                drawCombat(activeUnit,
                        targetUnit,
                        true,
                        CombatDrawState.ENEMY_HIT,
                        ActionName.COMBAT_ACTIVE_HIT);
                break;
            case ENEMY_HIT:
                drawCombat(targetUnit,
                        activeUnit,
                        attackFromEnemyUnit,
                        CombatDrawState.ACTIVE_SECOND_HIT,
                        ActionName.COMBAT_TARGET_HIT);
                break;
            case ACTIVE_SECOND_HIT:
                drawCombat(activeUnit,
                        targetUnit,
                        secondAttackFromActiveUnit,
                        CombatDrawState.ACTIVE_FIRST_HIT,
                        ActionName.COMBAT_DONE);
                break;
        }
    }

    private void drawCombat(Unit activeUnit, Unit targetUnit, Boolean doesAttack, CombatDrawState nextDrawState, ActionName nextModelAction){
        float frame = (TimeUtils.millis() - timeStamp) / 1000f;
        //Are we done?
        if(frame >= animationTime || !doesAttack){
            combatDrawState = nextDrawState;
            gameModel.performAction(nextModelAction);
            timeStamp = TimeUtils.millis();
        }else  if(frame < animationTime/2f){
            drawAttackAnimation(activeUnit, frame);
        }else {
            drawAttackAnimation(activeUnit, 0);
        }
        if(frame >= animationTime/2f){
            drawDamageNumber(activeUnit, frame);
        }
        drawAttackAnimation(targetUnit, 0);
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

        TextureRegion txtReg = new TextureRegion(texture, 0, 0, ViewUtils.TILE_WIDTH, ViewUtils.TILE_WIDTH - hpBar.getHeight());

        draw(txtReg, pos[0], pos[1]);
        draw(gridTexture, pos[0], pos[1]);
    }

    private void drawDamageNumber(Unit unit, float frame){

        if(unit.equals(activeUnit)){
            if(combatDrawState == CombatDrawState.ACTIVE_FIRST_HIT){
                drawDamageMessage(targetUnit,
                        frame,
                        true,
                        firstHitFromActiveUnit,
                        firstCritFromActiveUnit,
                        firstDamageFromActiveUnit);

            } else {
                drawDamageMessage(targetUnit,
                        frame,
                        secondAttackFromActiveUnit,
                        secondHitFromActiveUnit,
                        secondCritFromActiveUnit,
                        secondDamageFromActiveUnit);
            }
        } else {
            drawDamageMessage(activeUnit,
                    frame,
                    attackFromEnemyUnit,
                    hitFromEnemyUnit,
                    critFromEnemyUnit,
                    damageFromEnemyUnit);
        }
    }

    private void drawDamageMessage(Unit targetUnit, float frame, boolean doesAttack, boolean doesHit, boolean doesCrit, int damage){
        float uPos[] = {0f,0f};
        float scale = frame * 1.1f;
        font.getData().setScale(scale);
        font.setColor(Color.BLACK);
        String message = "null";

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
        spriteBatch.draw(texture,xPos,yPos);
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
