package game.main.java.view;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.main.java.model.*;
import game.main.java.model.states.UnitMenuState;
import game.main.java.model.units.Unit;

import java.util.List;

/**
 * Created by palm on 2016-04-22.
 */
public class UnitMenuView extends AbstractGameView {

    private BitmapFont font;
    private Texture menuOverlay;
    private Texture background;
    private GameModel gameModel = GameModel.getInstance();
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    /**
     * Creates a UnitMenuView.
     * Draws the menu if in UnitMenuState. Displays options depending on menuList in UnitMenuState
     * @param camera Camera.
     */
    public UnitMenuView(OrthographicCamera camera) {
        font = new BitmapFont();
        menuOverlay = new Texture("Menus/menuOverlayShort.png");
        background = new Texture("Menus/menuBackgroundEdges.png");
        this.camera = camera;
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;

        if(gameModel.getState() instanceof UnitMenuState){

            spriteBatch.begin();
            drawMenu();
            spriteBatch.end();
        }
    }

    /**
     * Draws background, all menu items, all menu text and a overlay on selected item.
     * Draws text gray if item cannot be selected. 
     */
    private void drawMenu(){
        int gap = 40;
        //Scale for text
        font.getData().setScale(2, 2);

        //Gets what items should be drawn in menu
        List<String> menuMap = gameModel.getMenuState().getMenuList();
        int selectedItem = gameModel.getMenuState().getMenuItemSelected();

        //calculates x position
        float x = camera.position.x - (camera.viewportWidth/2);
        if(!shouldDrawMenuRight()){
            x = camera.position.x + (camera.viewportHeight/2);
        }

        spriteBatch.draw(background, x, camera.position.y-40);

        //draws menuOverlay and text
        for(int i = 0; i < menuMap.size(); i++){
            if(selectedItem == i){
                spriteBatch.draw(menuOverlay, x+10,
                        camera.position.y +(camera.viewportHeight/2) -(i*gap) -60);
            }
            if(gameModel.getActiveUnit().getUnitState() == Unit.UnitState.MOVED
                    && menuMap.get(i).equals("Move")
                    || (gameModel.getActiveUnit().getUnitState() == Unit.UnitState.DONE
                    && !menuMap.get(i).equals("Status"))){
                font.setColor(Color.GRAY);
                font.draw(spriteBatch, menuMap.get(i), x +15,
                        camera.position.y +(camera.viewportHeight/2) - (i*gap) -30);
            } else {
                font.setColor(Color.BLACK);
                font.draw(spriteBatch, menuMap.get(i), x +15,
                        camera.position.y + (camera.viewportHeight / 2) - (i * gap) -30);
            }
        }
    }

    private boolean shouldDrawMenuRight(){
        int tileWidth = 64;
        int x = gameModel.getBoard().getPos(gameModel.getActiveUnit()).getX();
        return x * tileWidth > (camera.viewportWidth/2);
    }


}
