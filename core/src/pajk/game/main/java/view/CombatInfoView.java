package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.CombatInfoState;
import pajk.game.main.java.model.Unit;

/**
 * Created by jonatan on 18/05/2016.
 */
public class CombatInfoView extends AbstractGameView{

    private Texture statusBackground;
    private Texture unitImage;
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont font;
    private Unit unit;

    public CombatInfoView(OrthographicCamera camera){
        statusBackground = new Texture("statusBackground.png");
        unitImage = new Texture("shrek.png");
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
        spriteBatch.begin();
        int x = (int)(camera.position.x - (statusBackground.getWidth()/2));
        int y = (int)(camera.position.y - (statusBackground.getHeight()/2));
        spriteBatch.draw(statusBackground,x,y);
        spriteBatch.draw(unitImage,x + (statusBackground.getWidth()/8), y + 7 * (statusBackground.getHeight()/8) - unitImage.getHeight());
        spriteBatch.draw(unitImage,x + 7 * (statusBackground.getWidth()/8) - unitImage.getWidth(), y + 7 * (statusBackground.getHeight()/8) - unitImage.getHeight());
        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

    }
}
