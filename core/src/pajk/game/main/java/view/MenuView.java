package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.PajkGdxGame;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.Unit;

import java.awt.*;
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

    /**
     * Gets menumap from model and draws background, all menu items, all menu text and a overlay on selected item
     */
    private void drawMenu(){

        font.getData().setScale(2, 2);

        Map<Integer,String> menuMap = gameModel.getMenuState().getMenuMap();
        int selectedItem = gameModel.getMenuState().getMenuItemSelected();

        float x = camera.position.x - (camera.viewportWidth/2);

        if(!shouldDrawMenuRight()){
            x = camera.position.x + (camera.viewportHeight/2);
        }

        spriteBatch.draw(background, x,
                camera.position.y);

        int gap = 40;
        for(int i = 0; i < menuMap.size(); i++){
            if(selectedItem == i){
                spriteBatch.draw(menuOverlay, x,
                        camera.position.y +(camera.viewportHeight/2) -(i*gap) -gap);
            }
            if(gameModel.getActiveUnit().getUnitState() == Unit.UnitState.MOVED
                    && menuMap.get(i).equals("Move")){
                font.setColor(Color.GRAY);
                font.draw(spriteBatch, menuMap.get(i), x,
                        camera.position.y +(camera.viewportHeight/2) - (i*gap) );
            } else {
                font.setColor(Color.BLACK);
                font.draw(spriteBatch, menuMap.get(i), x,
                        camera.position.y + (camera.viewportHeight / 2) - (i * gap));
            }
        }
    }
    private boolean shouldDrawMenuRight(){
        int tileWidth = 64;
        int x = gameModel.getBoard().getPos(gameModel.getActiveUnit()).getX();
        return x * tileWidth > (camera.viewportWidth/2);
    }
}
