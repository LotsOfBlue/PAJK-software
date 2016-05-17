package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.*;

import java.util.ArrayList;

/**
 * Created by jonatan on 17/05/2016.
 */
public class EndView extends AbstractGameView{

    private Texture statusBackground;
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont font;

    public EndView(OrthographicCamera camera){
        statusBackground = new Texture("statusBackground.png");
        this.camera = camera;
        font = new BitmapFont();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        model = GameModel.getInstance();
        if(model.getState().getClass() == EndState.class){
            drawStatusScreen(spriteBatch);
        }
    }

    private void drawStatusScreen(SpriteBatch spriteBatch){
        State state = model.getState();
        EndState endState = (EndState) state;
        spriteBatch.begin();
        int x = (int)(camera.position.x - (camera.viewportWidth/2) + (camera.viewportWidth/8));
        int y = (int)(camera.position.y - (camera.viewportHeight/2) + (camera.viewportHeight/8));
        spriteBatch.draw(statusBackground,x,y);

        if(endState.getWinner() == Unit.Allegiance.PLAYER){
            font.getData().setScale(5,5);
            font.draw(spriteBatch,"Victory",x+20,y+ statusBackground.getHeight() - 110);
            font.getData().setScale(3,3);
            font.draw(spriteBatch,"Your score was "+endState.getScore(),x+20,y+ statusBackground.getHeight() - 180);
            font.draw(spriteBatch,"It took you "+endState.getTurns()+" turns to finish.",x+20,y+ statusBackground.getHeight() - 220);
            font.draw(spriteBatch,"You had "+endState.getUnits()+" units left.",x+20,y+ statusBackground.getHeight() - 260);
            font.draw(spriteBatch,"More useless stats coming soon!",x+20,y+ statusBackground.getHeight() - 300);

        }else {
            font.getData().setScale(5,5);
            font.draw(spriteBatch,"Defeat",x+20,y+ statusBackground.getHeight() - 110);
            font.getData().setScale(3,3);
            font.draw(spriteBatch,"Your score was "+endState.getScore(),x+20,y+ statusBackground.getHeight() - 180);
            font.draw(spriteBatch,"More useless stats coming soon!",x+20,y+ statusBackground.getHeight() - 220);
        }


        spriteBatch.end();

    }

    @Override
    public void update(float deltaTime) {

    }
}
