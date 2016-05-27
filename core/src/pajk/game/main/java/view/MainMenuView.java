package game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import game.PajkGdxGame;
import game.main.java.model.GameModel;
import game.main.java.model.scenarios.Scenario;
import game.main.java.model.states.MainMenuState;

import java.util.HashMap;
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

    private MainMenuState mmState;
    private int selection;
    private HashMap<String, Texture> screenshotHashMap = new HashMap<>();

    public MainMenuView(OrthographicCamera camera) {
        model = GameModel.getInstance();
        title = new Texture("Menus/titlescreen.png");
        scenarioSelectBG = new Texture("Menus/scenarioSelect.png");
        this.camera = camera;
        font = new BitmapFont();
        font.setColor(Color.LIGHT_GRAY);
        layout = new GlyphLayout();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState() instanceof MainMenuState){
            mmState = (MainMenuState) model.getState();
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
                drawScenarioSelectMenu(spriteBatch);
            }
            spriteBatch.end();
        }
    }

    /**
     * Draw the scenario select menu.
     * @param spriteBatch Spritebatch.
     */
    private void drawScenarioSelectMenu(SpriteBatch spriteBatch) {
        //The currently and previously selected scenario
        selection = mmState.getMenuItemSelected();
        Scenario scenario = mmState.getScenarioList().get(selection);
        //Background
        spriteBatch.draw(scenarioSelectBG, 0, 0);
        //List of scenarios
        drawScenarioList(spriteBatch);
        //Scenario name
        font.getData().setScale(2);
        layout.setText(font, scenario.getName());
        font.draw(spriteBatch, layout, 600 - (layout.width / 2), 500);
        //Scenario description
        drawDescription(spriteBatch, scenario);
        //Scenario screenshot
        String path = scenario.getScreenshotPath();
        if (path != null) {
            if(screenshotHashMap.isEmpty() || !screenshotHashMap.containsKey(path)){
                screenshotHashMap.put(path, new Texture(path));
            }
            Texture screenshot = screenshotHashMap.get(path);
            spriteBatch.draw(screenshot, 440, 260);
        }

        //Help text
        font.getData().setScale(1.5f);
        font.draw(spriteBatch, "Z - Select     X - Back", 500, 25);
    }

	/**
     * Draw the list of scenarios to choose from.
     * @param spriteBatch Spritebatch
     */
    private void drawScenarioList(SpriteBatch spriteBatch) {
        font.getData().setScale(1);
        List<Scenario> scenarioList = mmState.getScenarioList();

        for (Scenario s : scenarioList) {
            //Calculate the position of the scenarios last frame and their expected position this frame
            int yPos = calcListItemOffset(scenarioList.indexOf(s), selection);
            font.draw(
                    spriteBatch,
                    s.getName(),
                    10,
                    yPos,
                    0,
                    s.getName().length(),
                    220,
                    Align.left,
                    false,
                    "...");
        }
    }

	/**
     * Draw the currently selected scenario's description.
     * @param spriteBatch Spritebatch
     * @param scenario The currently selected scenario.
     */
    private void drawDescription(SpriteBatch spriteBatch, Scenario scenario) {
        font.getData().setScale(1.8f);
        font.draw(
                spriteBatch,
                scenario.getDescription(),
                320,
                210,
                580,
                Align.left,
                true);
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
}
