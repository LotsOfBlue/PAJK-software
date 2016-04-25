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
    private Texture unit;

    public BoardView(){
        img = new Texture("grass-tile");
        unit = new Texture("unit-sprite.png");
        this.gameModel = StateManager.getInstance();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        drawBoard(spriteBatch);
        drawUnit(spriteBatch);
        spriteBatch.end();
    }

    private void drawUnit(SpriteBatch spriteBatch){
        spriteBatch.draw(unit,0,0);
    }

    private void drawBoard(SpriteBatch spriteBatch){
        for(int i = 0; i < 10 * 65; i+=65){
            for (int j = 0; j < 10 * 65; j+=65){
                spriteBatch.draw(img,i,j);
            }
        }
    }


}
