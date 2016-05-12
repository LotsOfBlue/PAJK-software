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

    private int animationClock = 0;


    private Texture combatSheet;
    private TextureRegion combatFrames[];
    private Animation combatAnimation;

    public CombatView (){

        this.gameModel = GameModel.getInstance();

        board = gameModel.getBoard();

        test = new Animation(new Float(3) ,new TextureRegion(new Texture("unit-sprite")));

        combatSheet = new Texture("unit-sprite-combat");
        TextureRegion[][] tmp = TextureRegion.split(combatSheet, combatSheet.getWidth()/4, combatSheet.getHeight()/1);
        combatFrames = new TextureRegion[4 * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                combatFrames[index++] = tmp[i][j];
            }
        }
        combatAnimation = new Animation(0.05f, combatFrames);


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
        Tile acT = gameModel.getBoard().getPos(activeUnit);
        Tile eT = gameModel.getBoard().getPos(enemyUnit);
        if(!firstActiveHitDone){
            //TODO first combat section
            if (animationClock<30){
                int frame;
                if(animationClock<7){
                    frame = 0;
                }else if(animationClock<14){
                    frame = 1;
                }else if(animationClock<21){
                    frame = 2;
                }else{
                    frame = 3;
                }

                Texture sprite = new Texture("forest64.png");
                switch (acT.getTerrainType()){
                    case "Forest":
                        sprite = new Texture("forest64.png");
                        break;
                    case "Plains":
                        sprite = new Texture("grass64.png");
                        break;
                    case "Mountain":
                        sprite = new Texture("mountain64.png");
                        break;
                    case "River":
                        sprite = new Texture("water64.png");
                        break;



                }
                spriteBatch.draw(sprite,acT.getX()*(TILE_WIDTH+1),acT.getY()*(TILE_WIDTH+1));
                spriteBatch.draw(combatAnimation.getKeyFrame(frame),acT.getX()*(TILE_WIDTH+1),acT.getY()*(TILE_WIDTH+1));
                Texture sprite2 = new Texture("forest64.png");
                switch (eT.getTerrainType()){
                    case "Forest":
                        sprite = new Texture("forest64.png");
                        break;
                    case "Plains":
                        sprite = new Texture("grass64.png");
                        break;
                    case "Mountain":
                        sprite = new Texture("mountain64.png");
                        break;
                    case "River":
                        sprite = new Texture("water64.png");
                        break;



                }
                spriteBatch.draw(sprite,eT.getX()*(TILE_WIDTH+1),eT.getY()*(TILE_WIDTH+1));
                spriteBatch.draw(combatAnimation.getKeyFrame(frame),eT.getX()*(TILE_WIDTH+1),eT.getY()*(TILE_WIDTH+1));

                animationClock++;
            }
            if(animationClock == 30){
                firstActiveHitDone=true;
                animationClock = 0;
            }

        /*}else if(!enemyHitDone){
            //TODO second comnbat section
            if (animationClock<30){
                spriteBatch.draw(combatAnimation.getKeyFrame(animationClock),gameModel.getBoard().getPos(activeUnit).getX(),gameModel.getBoard().getPos(activeUnit).getY());

                spriteBatch.draw(combatAnimation.getKeyFrame(animationClock),gameModel.getBoard().getPos(enemyUnit).getX(),gameModel.getBoard().getPos(enemyUnit).getY());

                animationClock++;
            }
            if(animationClock == 30){
                firstActiveHitDone=true;
                animationClock = 0;
            }


        }else if (!secondActiveHitDone){
            //TODO third comnbat section
            if (animationClock<30){
                spriteBatch.draw(combatAnimation.getKeyFrame(animationClock),gameModel.getBoard().getPos(activeUnit).getX(),gameModel.getBoard().getPos(activeUnit).getY());

                spriteBatch.draw(combatAnimation.getKeyFrame(animationClock),gameModel.getBoard().getPos(enemyUnit).getX(),gameModel.getBoard().getPos(enemyUnit).getY());

                animationClock++;
            }
            if(animationClock == 30){
                firstActiveHitDone=true;
                animationClock = 0;
            }*/


        }else{
            done = true;
            flush();
        }

    }

    private void flush(){
        firstActiveHitDone = false;
        secondActiveHitDone = false;
        enemyHitDone = false;

    }
}
