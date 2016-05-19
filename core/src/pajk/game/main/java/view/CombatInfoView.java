package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.CombatInfoState;
import pajk.game.main.java.model.Unit;
import pajk.game.main.java.model.Unit.*;

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
    private Unit unit;

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
        Unit activeUnit = combatInfoState.getActiveUnit();
        activeUnitImage = getTextureFor(activeUnit);
        Unit targetUnit = combatInfoState.getTargetUnit();
        targetUnitImage = getTextureFor(targetUnit);
        int x = (int)(camera.position.x - (statusBackground.getWidth()/2));
        int y = (int)(camera.position.y - (statusBackground.getHeight()/2));
        spriteBatch.begin();
        spriteBatch.draw(statusBackground,x,y);
        spriteBatch.draw(activeUnitImage,x + (statusBackground.getWidth()/8), y + 7 * (statusBackground.getHeight()/8) - activeUnitImage.getHeight());
        spriteBatch.draw(targetUnitImage,x + 7 * (statusBackground.getWidth()/8) - targetUnitImage.getWidth(), y + (statusBackground.getHeight()/8));
        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

    }

    private Texture getTextureFor(Unit unit){
        switch (unit.getUnitClass()){
            case AXE:
                if(unit.getAllegiance() == Allegiance.PLAYER){
                    return new Texture("shrek-blue.png");//TODO get real images
                }else {
                    return new Texture("shrek-red.png");
                }
            case SWORD:
                if(unit.getAllegiance() == Allegiance.PLAYER){
                    return new Texture("shrek-blue.png");//TODO get real images
                }else {
                    return new Texture("shrek-red.png");
                }
            case PIKE:
                if(unit.getAllegiance() == Allegiance.PLAYER){
                    return new Texture("shrek-blue.png");//TODO get real images
                }else {
                    return new Texture("shrek-red.png");
                }
            case BOW:
                if(unit.getAllegiance() == Allegiance.PLAYER){
                    return new Texture("shrek-blue.png");//TODO get real images
                }else {
                    return new Texture("shrek-red.png");
                }
            case BOOK:
                if(unit.getAllegiance() == Allegiance.PLAYER){
                    return new Texture("shrek-blue.png");//TODO get real images
                }else {
                    return new Texture("shrek-red.png");
                }

        }
        return new Texture("shrek.png");//TODO get real images
    }
}
