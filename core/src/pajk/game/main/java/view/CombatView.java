package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.ActionName;
import pajk.game.main.java.model.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pajk.game.main.java.model.GameModel;

import javax.swing.*;

/**
 * Created by jonatan on 06/05/2016.
 */
public class CombatView extends GameView {
    private boolean done = false;
    private final int TILE_WIDTH = 64;

    private SpriteBatch spriteBatch;
    private GameModel gameModel;

    private Animation test;

    private Unit activeUnit;
    private Unit enemyUnit;
    private Board board;
    private boolean calcDone = false;
    private int firstDamageFromActiveUnit = 0;
    private boolean firstHitFromActiveUnit = false;
    private boolean firstCritFromActiveUnit = false;
    private int secondDamageFromActiveUnit = 0;
    private boolean secondHitFromActiveUnit = false;
    private boolean secondCritFromActiveUnit = false;
    private int damageFromEnemyUnit = 0;
    private boolean hitFromEnemyUnit = false;
    private boolean critFromEnemyUnit = false;

    private boolean firstActiveHitDone = false;
    private boolean secondActiveHitDone = false;
    private boolean enemyHitDone = false;

    public CombatView (){

        this.gameModel = GameModel.getInstance();

        board = gameModel.getBoard();

        test = new Animation(new Float(1) ,new TextureRegion(new Texture("unit-sprite")));


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
        secondActiveHitDone = !secondHitFromActiveUnit;
        secondCritFromActiveUnit = combatState.isSecondCritFromActiveUnit();
        damageFromEnemyUnit = combatState.getDamageFromEnemyUnit();
        hitFromEnemyUnit = combatState.isHitFromEnemyUnit();
        critFromEnemyUnit = combatState.isCritFromEnemyUnit();
    }

    private void drawCombat(){
        if(!firstActiveHitDone){
            //TODO first comnbat section
            spriteBatch.draw(test.getKeyFrame(1),(gameModel.getBoard().getBoardWidth()*TILE_WIDTH/2)-TILE_WIDTH,gameModel.getBoard().getBoardHeight()*TILE_WIDTH/2);

            spriteBatch.draw(test.getKeyFrame(1),(gameModel.getBoard().getBoardWidth()*TILE_WIDTH/2)+TILE_WIDTH*2,gameModel.getBoard().getBoardHeight()*TILE_WIDTH/2);
            if(test.isAnimationFinished((float)0.1)){
                firstActiveHitDone=true;
            }

        }else if(!enemyHitDone){
            //TODO second comnbat section
            spriteBatch.draw(test.getKeyFrame(1),(gameModel.getBoard().getBoardWidth()*TILE_WIDTH/2)-TILE_WIDTH,gameModel.getBoard().getBoardHeight()*TILE_WIDTH/2);

            spriteBatch.draw(test.getKeyFrame(1),(gameModel.getBoard().getBoardWidth()*TILE_WIDTH/2)+TILE_WIDTH*2,gameModel.getBoard().getBoardHeight()*TILE_WIDTH/2);
            if(test.isAnimationFinished((float)0.1)){
                enemyHitDone=true;
            }


        }else if (!secondActiveHitDone){
            //TODO third comnbat section
            spriteBatch.draw(test.getKeyFrame(1),(gameModel.getBoard().getBoardWidth()*TILE_WIDTH/2)-TILE_WIDTH,gameModel.getBoard().getBoardHeight()*TILE_WIDTH/2);

            spriteBatch.draw(test.getKeyFrame(1),(gameModel.getBoard().getBoardWidth()*TILE_WIDTH/2)+TILE_WIDTH*2,gameModel.getBoard().getBoardHeight()*TILE_WIDTH/2);
            if(test.isAnimationFinished((float)0.1)){
                secondActiveHitDone=true;
            }


        }else{
            done = true;
        }

    }
}
