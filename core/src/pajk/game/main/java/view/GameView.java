package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by palm on 2016-04-22.
 */
public abstract class GameView {

    public abstract void render(SpriteBatch spriteBatch);
    public abstract void update(float deltaTime);

}
