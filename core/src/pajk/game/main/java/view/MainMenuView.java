package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
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
    private Texture scenSelect;
    private OrthographicCamera camera;
    private BitmapFont font;
    //Used for centering scenario name properly
    private GlyphLayout layout;

    public MainMenuView(OrthographicCamera camera) {
        model = GameModel.getInstance();
        titleTex = new Texture("Menus/titlescreen.png");
        scenSelect = new Texture("Menus/scenarioSelect.png");
        this.camera = camera;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        layout = new GlyphLayout();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState() instanceof MainMenuState){
            MainMenuState state = (MainMenuState) model.getState();
            camera.position.set(PajkGdxGame.WIDTH / 2f, PajkGdxGame.HEIGHT / 2f, 0);
            camera.update();
            spriteBatch.setProjectionMatrix(camera.combined);
            spriteBatch.begin();
            //Display the title screen
            if (state.getTitle()) {
                spriteBatch.draw(titleTex, 0, 0);
            }
            //Display the scenario select screen
            else {
                //The currently selected scenario
                int selection = state.getMenuItemSelected();
                Scenario scenario = state.getScenarioList().get(selection);
                //Background
                spriteBatch.draw(scenSelect, 0, 0);
                //List of scenarios
                font.getData().setScale(1);
                List<Scenario> scenarioList = state.getScenarioList();
                for (int i = 0; i < scenarioList.size(); i++) {
                    Scenario s = scenarioList.get(i);
                    font.draw(
                            spriteBatch,
                            s.getName(),
                            10,
                            285 - (40*i),
                            0,
                            s.getName().length(),
                            220,
                            Align.left,
                            false,
                            "...");
                }
                //Scenario name
                font.getData().setScale(2);
                layout.setText(font, scenario.getName());
                font.draw(spriteBatch, layout, 600 - (layout.width / 2), 500);
                //Scenario description
                font.getData().setScale(1.8f);
                font.draw(
                        spriteBatch,
                        scenario.getDescription(),
                        320,
                        200,
                        580,
                        Align.left,
                        true);

                //Scenario screenshot
                String path = scenario.getScreenshotPath();
                if (path != null) {
                    Texture screenshot = new Texture(path);
                    spriteBatch.draw(screenshot, 400, 400);
                }
            }
            spriteBatch.end();
        }
    }

    @Override
    public void update(float deltaTime) {

    }
}
