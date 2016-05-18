package pajk.game.main.java.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.CombatState;
import pajk.game.main.java.model.EndState;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.UnitMenuState;

/**
 * Created by palm on 2016-05-10.
 */
public class MainView {

    private BoardView boardView = null;
    private CombatView combatView;
    private MenuView menuView;
    private StatusView statusView;
    private EndView endView;
    private CombatInfoView combatInfoView;
    private GameModel gameModel = GameModel.getInstance();
    private OrthographicCamera camera;
//    private OrthographicCamera camera;

    public MainView(){
        /*float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        boardView = new BoardView(camera);
        combatView = new CombatView();
        menuView = new MenuView(camera);
        statusView = new StatusView(camera);
        endView = new EndView(camera);*/
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
            combatInfoView.render(spriteBatch);
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
        menuView = new MenuView(camera);
        statusView = new StatusView(camera);
        endView = new EndView(camera);
        combatInfoView = new CombatInfoView(camera);
    }
}
