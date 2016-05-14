package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.StatusState;
import pajk.game.main.java.model.Unit;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusView extends AbstractGameView{

    private Texture statusBackground;
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont font;
    private Unit unit;

    public StatusView(OrthographicCamera camera){
        statusBackground = new Texture("statusBackground.png");
        this.camera = camera;
        font = new BitmapFont();
        model = GameModel.getInstance();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState().getClass() == StatusState.class){
            drawStatusScreen(spriteBatch);
        }
    }

    private void drawStatusScreen(SpriteBatch spriteBatch){

        model = GameModel.getInstance();
        unit = GameModel.getInstance().getActiveUnit();
        spriteBatch.begin();
        int x = 70;
        int y = (int)(camera.position.y - (camera.viewportHeight/2)) + 50;
        spriteBatch.draw(statusBackground,x,y);

        font.getData().setScale(2,2);

        font.draw(spriteBatch, unit.getName(),x,y+camera.viewportHeight/2);
        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

    }
}
