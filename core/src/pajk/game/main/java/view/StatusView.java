package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.StatusState;
import pajk.game.main.java.model.units.Unit;
import java.util.HashMap;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusView extends AbstractGameView{

    private Texture statusBackground;
    private Texture unitImage;
    private Texture selector;
    private Texture statusBackgroundRed;
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont font;
    private Unit unit;
    private StatusState state;
    private HashMap<String, Texture> unitTextureMap = new HashMap<>();

    /**
     * Creates a StatusView.
     * Draws the status screen of the unit selected. Prints following info about unit:
     * Name, health, level, defence, Weapon, Experience, Strength, Might, Skill, Luck, Resistance,
     * Speed, Movement, Constitution and Aid.
     * @param camera where the status will be drawn.
     */
    public StatusView(OrthographicCamera camera){

        statusBackground = new Texture("Menus/statusBackground.png");
        unitImage = new Texture("Menus/shrek.png");
        selector = new Texture("Menus/statusInfoSelector.png");
        statusBackgroundRed = new Texture("statusBackgroundRed.png");

        this.camera = camera;
        font = new BitmapFont();
        model = GameModel.getInstance();

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState().getClass() == StatusState.class){
            spriteBatch.begin();
            drawStatusScreen(spriteBatch);
            if(state.isInInfoState()) {
                drawInfoText(spriteBatch);
            }
            drawButtonText(spriteBatch);
            spriteBatch.end();

        }
    }


    private void drawStatusScreen(SpriteBatch spriteBatch){
        state = (StatusState)model.getState();
        model = GameModel.getInstance();
        unit = state.getStatusUnit();

        int x = (int)(camera.position.x - camera.viewportWidth/2)+85;
        int y = (int)(camera.position.y - (camera.viewportHeight/2)) + 30;


        font.getData().setScale(2,2);
        font.setColor(Color.WHITE);

        String allegianceText;
        //draws name and alligance
        if(unit.getAllegiance() == Unit.Allegiance.PLAYER){
            allegianceText = "ALLY";
            spriteBatch.draw(statusBackground,x-15,y+30);
        } else {
            allegianceText = "ENEMY";
            spriteBatch.draw(statusBackgroundRed,x-15,y+30);
        }
        String nameText = allegianceText +": "+state.getInfoItem(0);
        font.draw(spriteBatch,nameText,x+20,y+ camera.viewportHeight - 110);
        if(0 == state.getSelectedInfoItemNr()){
            drawOverlay(spriteBatch, x+20,(int)(y+ camera.viewportHeight - 110));
        }
        //draws image
        String str = unit.getPortraitFilePath();
        if(unitTextureMap.isEmpty() || !unitTextureMap.containsKey(str)){
            unitTextureMap.put(str, new Texture(str));
        }
        spriteBatch.draw(unitTextureMap.get(str),x+20,y+ camera.viewportHeight - 100 -50 -130);

        //draws first column
        for(int i = 1; i < 5; i++){
            font.draw(spriteBatch,state.getInfoItem(i),x+20,y+ camera.viewportHeight - (300 +(i-1)*35));
            if(i == state.getSelectedInfoItemNr()) {
                drawOverlay(spriteBatch, x + 20, (int) (y + camera.viewportHeight - (300 + (i - 1) * 35)));

            }
        }

        //draws second and third column
        x += 300;
        for(int i = 5; i < state.getStateSize(); i++){
            int ydiff = (i-5) * 35;
            if(i == (state.getStateSize()+5)/2){
                x += 250;
            }
            if(i>=(state.getStateSize()+5)/2){
                ydiff = 35*(i-(state.getStateSize()+5)/2);
            }
            font.draw(spriteBatch, state.getInfoItem(i),x+20,y + camera.viewportHeight -145 - ydiff-20);
            if(i == state.getSelectedInfoItemNr()) {
                drawOverlay(spriteBatch, x + 20, (int) (y + camera.viewportHeight - 145 - ydiff - 20));
            }
        }


    }

    private void drawInfoText(SpriteBatch spritebatch){
        int x = (int)(camera.position.x - camera.viewportWidth/2)+85;
        int y = (int)(camera.position.y - (camera.viewportHeight/2)) + 30;
        font.setColor(Color.WHITE);
        font.draw(spritebatch,state.getActiveInfoItemText(),x+320,y+150);
    }

    private void drawButtonText(SpriteBatch spriteBatch) {
        font.setColor(Color.BLACK);
        font.getData().setScale((float) 1.5, (float) 1.5);
        int x = (int) (camera.position.x - 400);
        int y = (int) (camera.position.y - camera.viewportHeight / 2 + 50);

        if(state.isInInfoState()){
            font.draw(spriteBatch,"(X) Back",x,y);
            font.draw(spriteBatch,"(UP/DOWN) Switch Stat",x+150,y);
        } else {
            font.draw(spriteBatch,"(Z) Show Information",x,y);
            font.draw(spriteBatch,"(X) Back",x+300,y);
            font.draw(spriteBatch,"(UP/DOWN) Switch Unit",x+500,y);
            //TODO add upp/down action
        }


    }

    private void drawOverlay(SpriteBatch spriteBatch, int x, int y){
        state = (StatusState)model.getState();
        if(state.isInInfoState()){
            spriteBatch.draw(selector,x-30,y-35);
        }

    }

    @Override
    public void update(float deltaTime) {

    }
}
