package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.PajkGdxGame;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.scenarios.Scenario;
import pajk.game.main.java.model.states.MainMenuState;

import java.util.List;

/**
 * Created by johan on 2016-05-20.
 */
public class MainMenuView extends AbstractGameView{

    private GameModel model;
    private Texture titleTex;
    private OrthographicCamera camera;
    private BitmapFont font;

    public MainMenuView(OrthographicCamera camera) {
        model = GameModel.getInstance();
        titleTex = new Texture("Menus/PAJK titlescreen.png");
        this.camera = camera;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().scale(1);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState() instanceof MainMenuState){
            MainMenuState state = (MainMenuState) model.getState();
            camera.position.set(PajkGdxGame.WIDTH / 2f, PajkGdxGame.HEIGHT / 2f, 0);
            camera.update();
            spriteBatch.setProjectionMatrix(camera.combined);
            spriteBatch.begin();
            if (state.getTitle()) {
                spriteBatch.draw(titleTex, 0, 0);
            } else {
                //TODO select scenario
                List<Scenario> scenarioList = state.getScenarioList();
                for (int i = 0; i < scenarioList.size(); i++) {
                    Scenario s = scenarioList.get(i);
                    font.draw(spriteBatch, s.getName(), 0, 400 - (50*i));
                }
            }
            spriteBatch.end();
        }
    }

    @Override
    public void update(float deltaTime) {

    }
}
