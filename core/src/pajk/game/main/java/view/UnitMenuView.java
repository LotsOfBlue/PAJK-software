package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.states.CombatState;
import pajk.game.main.java.model.states.EnemyTurnState;
import pajk.game.main.java.model.states.StatusState;
import pajk.game.main.java.model.states.UnitMenuState;
import pajk.game.main.java.model.units.Unit;

import java.util.List;

/**
 * Created by palm on 2016-04-22.
 */
public class UnitMenuView extends AbstractGameView {

    private BitmapFont font;        //TODO change to "freetype" instead, use gradle
    private Texture menuOverlay;
    private Texture background;
    private Texture tooltipBackground;
    private GameModel gameModel = GameModel.getInstance();
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    /**
     * Creates a UnitMenuView.
     * Draws the menu if in UnitMenuState. Displays options depending on menuList in UnitMenuState
     * @param camera
     */
    public UnitMenuView(OrthographicCamera camera) {
        font = new BitmapFont();
        menuOverlay = new Texture("Menus/menuOverlayShort.png");
        background = new Texture("Menus/menuBackgroundEdges.png");
        tooltipBackground = new Texture("Menus/tooltipBackground.png");
        this.camera = camera;
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;

        spriteBatch.begin();
        if(gameModel.getState().getClass() == UnitMenuState.class){
            drawMenu();
        }

        //check
        if(gameModel.getBoard().getCursorTile().hasUnit() && gameModel.getState().getClass() != EnemyTurnState.class &&
                gameModel.getState().getClass() != CombatState.class && gameModel.getState().getClass() != StatusState.class){
            drawTooltip();
        }

        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

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

    private void drawTooltip(){

        float x = camera.position.x - (camera.viewportWidth/2) +40;
        if(shouldDrawTooltipRight()){
            x = camera.position.x + (camera.viewportHeight/2);
        }
        float y = camera.position.y - (camera.viewportHeight/2) +10;
        spriteBatch.draw(tooltipBackground,x,y);

        Unit unit = GameModel.getInstance().getBoard().getCursorTile().getUnit();
        String healthText = unit.getHealth() +"/"+unit.getMaxHealth() +" hp";
        String lvlText = "Lvl: " +unit.getLevel();
        String nameText = unit.getName();

        font.getData().setScale(2,2);
        font.setColor(Color.BLACK);
        font.draw(spriteBatch,healthText,x+15,y+110);
        font.draw(spriteBatch,lvlText,x+15,y+80);
        font.getData().setScale(2f - nameText.length()/16f);
        font.draw(spriteBatch, nameText, x+15, y+140);

    }

    private boolean shouldDrawTooltipRight(){
        int tileWidth = 64;
        int x = gameModel.getBoard().getCursorTile().getX();

        return x * tileWidth < camera.position.x;
    }

    private boolean shouldDrawMenuRight(){
        int tileWidth = 64;
        int x = gameModel.getBoard().getPos(gameModel.getActiveUnit()).getX();
        return x * tileWidth > (camera.viewportWidth/2);
    }
}
