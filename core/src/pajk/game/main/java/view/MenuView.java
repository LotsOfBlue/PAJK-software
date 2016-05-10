package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.PajkGdxGame;
import pajk.game.main.java.model.GameModel;

import java.util.Map;

/**
 * Created by palm on 2016-04-22.
 */
public class MenuView extends GameView{

    private BitmapFont font;        //TODO change to "freetype" instead, use gradle
    private Texture menuOverlay;
    private Texture background;
    private GameModel gameModel = GameModel.getInstance();
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    public MenuView(OrthographicCamera camera) {
        font = new BitmapFont();
        menuOverlay = new Texture("menuOverlay.png");
        background = new Texture("background.png");
        this.camera = camera;
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;

//        camera.update();
//        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
//        drawBoard();
//        drawCursor();
        drawMenu();

        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

    }

    private void drawMenu(){
//        if(gameModel.getCurrentStateName() == GameModel.StateName.UNIT_MENU) {

            spriteBatch.draw(background, camera.position.x - (camera.viewportWidth/2),
                    camera.position.y);
            font.getData().setScale(2, 2);


            Map<Integer,String> menuMap = gameModel.getMenuState().getMenuMap();
            int selectedItem = gameModel.getMenuState().getMenuItemSelected();

            if(selectedItem == 0){
                spriteBatch.draw(menuOverlay, camera.position.x - (camera.viewportWidth/2),
                        camera.position.y +(camera.viewportHeight/2) -40 );
            } else if (selectedItem == 1){
                spriteBatch.draw(menuOverlay, camera.position.x - (camera.viewportWidth/2),
                        camera.position.y +(camera.viewportHeight/2) - 80);
            } else if (selectedItem == 2){
                spriteBatch.draw(menuOverlay, camera.position.x - (camera.viewportWidth/2),
                        camera.position.y +(camera.viewportHeight/2) - 120);
            }

            font.draw(spriteBatch, "Move", camera.position.x - (camera.viewportWidth/2),
                    camera.position.y +(camera.viewportHeight/2) );
            font.draw(spriteBatch, "Attack", camera.position.x - (camera.viewportWidth/2),
                    camera.position.y +(camera.viewportHeight/2) - 40);
            font.draw(spriteBatch, "Wait", camera.position.x - (camera.viewportWidth/2),
                    camera.position.y +(camera.viewportHeight/2) - 80);




//        }
    }
}
