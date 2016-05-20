package pajk.game.main.java.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;

/**
 * Created by palm on 2016-05-10.
 */
public class ViewHandler {

    private BoardView boardView;
    private CombatView combatView;
    private UnitMenuView menuView;
    private StatusView statusView;
    private EndView endView;
    private CombatInfoView combatInfoView;
    private MainMenuView mainMenuView;
    private GameModel gameModel = GameModel.getInstance();
    private OrthographicCamera camera;

    /**
     * Creates the ViewHandler
     * ViewHandler creates all other views and draws them. The separate View classes decides if it should be drawn or not.
     */
    public ViewHandler(){
        makeNewViews();
    }

    public void render(SpriteBatch spriteBatch){
        if (boardView.getBoard() != gameModel.getBoard()){
            makeNewViews();
        }
        mainMenuView.render(spriteBatch);
        boardView.render(spriteBatch);
        combatView.render(spriteBatch);
        menuView.render(spriteBatch);
        statusView.render(spriteBatch);
        endView.render(spriteBatch);
        combatInfoView.render(spriteBatch);
    }

    private void makeNewViews(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        boardView = new BoardView(camera);
        combatView = new CombatView();
        menuView = new UnitMenuView(camera);
        statusView = new StatusView(camera);
        endView = new EndView(camera);
        combatInfoView = new CombatInfoView(camera);
        mainMenuView = new MainMenuView();
    }
}
