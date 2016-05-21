package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.CombatInfoState;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.units.Unit.*;

/**
 * Created by jonatan on 18/05/2016.
 */
public class CombatInfoView extends AbstractGameView{

    private Texture statusBackground;
    private Texture activeUnitImage;
    private Texture targetUnitImage;
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont font;
    private Unit activeUnit, targetUnit;

    public CombatInfoView(OrthographicCamera camera){
        statusBackground = new Texture("statusBackground.png");
        this.camera = camera;
        font = new BitmapFont();
        model = GameModel.getInstance();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState().getClass() == CombatInfoState.class){
            drawInfoScreen(spriteBatch);
        }
    }

    private void drawInfoScreen(SpriteBatch spriteBatch){
        CombatInfoState combatInfoState = (CombatInfoState)model.getState();

        if(activeUnit != combatInfoState.getActiveUnit()){
            activeUnit = combatInfoState.getActiveUnit();
            activeUnitImage = new Texture(activeUnit.getPortraitFilePath());
        }
        if(targetUnit != combatInfoState.getTargetUnit()){
            targetUnit = combatInfoState.getTargetUnit();
            targetUnitImage = new Texture(targetUnit.getPortraitFilePath());
        }
        int x = (int)(camera.position.x - (statusBackground.getWidth()/2));
        int y = (int)(camera.position.y - (statusBackground.getHeight()/2));
        spriteBatch.begin();
        spriteBatch.draw(statusBackground,x,y);
        spriteBatch.draw(activeUnitImage,x + (statusBackground.getWidth()/8), y + 7 * (statusBackground.getHeight()/8) - activeUnitImage.getHeight());
        font.draw(spriteBatch,
                "\n" + activeUnit.getHealth() + " --> " + (activeUnit.getHealth() - combatInfoState.getTargetDmg())
                        + "/" + activeUnit.getMaxHealth() + " HP"
                        + "\n\n" + combatInfoState.getActiveDmg() + " DMG"
                        + "\n\n" + combatInfoState.getActiveHitChance() + "% HIT CHANCE"
                        + "\n\n" + combatInfoState.getActiveCritChance() + "% CRIT CHANCE"
                        + "\n\n" + activeUnit.getSpeed() + " SPEED"
                        + "\n\n" + activeUnit.getWeapon().getName()
                , x + (statusBackground.getWidth()/8)
                ,  y + 7 * (statusBackground.getHeight()/8) - activeUnitImage.getHeight());
        font.draw(spriteBatch,
                targetUnit.getHealth() + " --> " +
                        (targetUnit.getHealth() - combatInfoState.getActiveDmg())
                        + "/" + targetUnit.getMaxHealth() + " HP"
                        + "\n\n" + combatInfoState.getTargetDmg() + " DMG"
                        + "\n\n" + combatInfoState.getTargetHitChance() + "% HIT CHANCE"
                        + "\n\n" + combatInfoState.getTargetCritChance() + "% CRIT CHANCE"
                        + "\n\n" + targetUnit.getSpeed() + " SPEED"
                        + "\n\n" + targetUnit.getWeapon().getName()
                , x + 7 * (statusBackground.getWidth()/8) - targetUnitImage.getWidth()
                , y + 7 * (statusBackground.getHeight()/8));
        spriteBatch.draw(targetUnitImage,x + 7 * (statusBackground.getWidth()/8) - targetUnitImage.getWidth(), y + (statusBackground.getHeight()/8));
        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

    }

}
