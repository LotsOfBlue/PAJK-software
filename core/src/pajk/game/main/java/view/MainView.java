package pajk.game.main.java.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;

/**
 * Created by palm on 2016-05-10.
 */
public class MainView {

    private BoardView boardView;
    private CombatView combatView;
    private UnitMenuView menuView;
    private StatusView statusView;
    private EndView endView;
    private GameModel gameModel = GameModel.getInstance();
    private OrthographicCamera camera;

    /**
     * Creates the MainView
     * MainView creates all other views and draws them. The seperate View classes decides if it should be drawn or not.
     */
    public MainView(){
        makeNewViews();


    }

    public void render(SpriteBatch spriteBatch){
        if (gameModel.getState().getName() != GameModel.StateName.MAIN_MENU) {
            if (boardView.getBoard() != gameModel.getBoard()){
                makeNewViews();
            }
            boardView.render(spriteBatch);
            combatView.render(spriteBatch);
            menuView.render(spriteBatch);
            statusView.render(spriteBatch);
            endView.render(spriteBatch);
        } else {
            //TODO: Draw the main menu.
        }
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
    }
}
