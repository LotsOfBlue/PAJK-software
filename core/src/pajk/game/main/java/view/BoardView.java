package pajk.game.main.java.view;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import pajk.game.main.java.model.StateManager;


/**
 * Created by palm on 2016-04-22.
 */
public class BoardView extends GameView{


    private StateManager gameModel;
    private Texture img;

    public BoardView(StateManager gameModel){
        img = new Texture("grass-tile");
        this.gameModel = gameModel;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(img,0,0);
        spriteBatch.end();
    }

    private void drawBoard(SpriteBatch spriteBatch){

    }


}
