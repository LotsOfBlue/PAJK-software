package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.MainMenuState;

/**
 * Created by johan on 2016-05-20.
 */
public class MainMenuView extends AbstractGameView{

    private GameModel model;

    public MainMenuView() {
        model = GameModel.getInstance();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState() instanceof MainMenuState){
            //TODO do stuff
            System.out.println("MENU");
        }
    }

    @Override
    public void update(float deltaTime) {

    }
}
