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
 * @author Johan
 */
public class MainMenuView extends AbstractGameView{

    private GameModel model;
    private Texture title;
    private Texture scenarioSelectBG;
    private OrthographicCamera camera;
    private BitmapFont font;
    //Used for centering scenario name properly
    private GlyphLayout layout;

    public MainMenuView(OrthographicCamera camera) {
        model = GameModel.getInstance();
        title = new Texture("Menus/titlescreen.png");
        scenarioSelectBG = new Texture("Menus/scenarioSelect.png");
        this.camera = camera;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        layout = new GlyphLayout();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState() instanceof MainMenuState){
            MainMenuState mmState = (MainMenuState) model.getState();
            //Reset the camera in case it has been moved
            camera.position.set(PajkGdxGame.WIDTH / 2f, PajkGdxGame.HEIGHT / 2f, 0);
            camera.update();
            spriteBatch.setProjectionMatrix(camera.combined);
            spriteBatch.begin();
            //Display the title screen
            if (mmState.getShowTitle()) {
                spriteBatch.draw(title, 0, 0);
            }
            //Display the scenario select screen
            else {
                drawScenarioSelectMenu(spriteBatch, mmState);
            }
            spriteBatch.end();
        }
    }

    private void drawScenarioSelectMenu(SpriteBatch spriteBatch, MainMenuState mmState) {
        //The currently selected scenario
        int selection = mmState.getMenuItemSelected();
        Scenario scenario = mmState.getScenarioList().get(selection);
        //Background
        spriteBatch.draw(scenarioSelectBG, 0, 0);
        //List of scenarios
        font.getData().setScale(1);
        List<Scenario> scenarioList = mmState.getScenarioList();
        for (Scenario s : scenarioList) {
            font.draw(
                    spriteBatch,
                    s.getName(),
                    10,
                    calcListItemOffset(scenarioList.indexOf(s), selection),
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

	/**
     * Calculate the vertical offset of each scenario in the list
     * @param index The index of the scenario being calculated
     * @param selected The scenario that is currently selected
     */
    private int calcListItemOffset(int index, int selected) {
        final int CENTER = 283;
        final int BASE_OFFSET = 35;
        int posInList = index - selected;
        return CENTER - (BASE_OFFSET * posInList);
    }

    @Override
    public void update(float deltaTime) {

    }
}
