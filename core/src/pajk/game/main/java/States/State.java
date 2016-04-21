package pajk.game.main.java.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by palm on 2016-04-20.
 */
public abstract class State {
    private OrthographicCamera cam;
//    private Vector3 vec;
    private GameStateManager gameStateManager;

    public State(GameStateManager gameStateManager){
        this.cam = new OrthographicCamera();
        this.gameStateManager = gameStateManager;
    }

    public abstract void handleInput();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch spriteBatch);
}
