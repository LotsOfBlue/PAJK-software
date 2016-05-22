package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.states.StatusState;
import pajk.game.main.java.model.units.Unit;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusView extends AbstractGameView{

    private Texture statusBackground;
    private Texture unitImage;
    private Texture selector;
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont whiteFont;
    private BitmapFont blackFont;
    private Unit unit;
    private StatusState state;

    /**
     * Creates a StatusView.
     * Draws the status screen of the unit selected. Prints following info about unit:
     * Name, health, level, defence, Weapon, Experience, Strength, Might, Skill, Luck, Resistance,
     * Speed, Movement, Constitution and Aid.
     * @param camera where the status will be drawn.
     */
    public StatusView(OrthographicCamera camera){
        statusBackground = new Texture("statusBackground.png");
        unitImage = new Texture("shrek.png");
        selector = new Texture("statusInfoSelector.png");
        this.camera = camera;
        whiteFont = new BitmapFont();
        blackFont = new BitmapFont();
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
        unit = GameModel.getInstance().getActiveUnit();

        int x = (int)(camera.position.x - camera.viewportWidth/2)+85;
        int y = (int)(camera.position.y - (camera.viewportHeight/2)) + 30;
        spriteBatch.draw(statusBackground,x-15,y+30);

        whiteFont.getData().setScale(2,2);


        //draws name
        whiteFont.draw(spriteBatch,state.getInfoItem(0),x+20,y+ camera.viewportHeight - 110);
        if(0 == state.getSelectedInfoItemNr()){
            drawOverlay(spriteBatch, x+20,(int)(y+ camera.viewportHeight - 110));
        }
        //draws image
        spriteBatch.draw(unitImage,x+20,y+ camera.viewportHeight - 100 -50 -130);

        //draws first column
        for(int i = 1; i < 5; i++){
            whiteFont.draw(spriteBatch,state.getInfoItem(i),x+20,y+ camera.viewportHeight - (300 +(i-1)*35));
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
            whiteFont.draw(spriteBatch, state.getInfoItem(i),x+20,y + camera.viewportHeight -145 - ydiff-20);
            if(i == state.getSelectedInfoItemNr()) {
                drawOverlay(spriteBatch, x + 20, (int) (y + camera.viewportHeight - 145 - ydiff - 20));
            }
        }


    }

    private void drawInfoText(SpriteBatch spritebatch){
        int x = (int)(camera.position.x - camera.viewportWidth/2)+85;
        int y = (int)(camera.position.y - (camera.viewportHeight/2)) + 30;
        whiteFont.draw(spritebatch,state.getActiveInfoItemText(),x+320,y+150);
    }

    private void drawButtonText(SpriteBatch spriteBatch) {
        blackFont.dispose();
        blackFont = new BitmapFont();
        blackFont.setColor(Color.BLACK);
        blackFont.getData().setScale((float) 1.5, (float) 1.5);
        int x = (int) (camera.position.x - 400);
        int y = (int) (camera.position.y - camera.viewportHeight / 2 + 50);

        if(state.isInInfoState()){
            blackFont.draw(spriteBatch,"(X) Back",x,y);
            blackFont.draw(spriteBatch,"(UP/DOWN) Switch Stat",x+150,y);
        } else {
            blackFont.draw(spriteBatch,"(Z) Show Information",x,y);
            blackFont.draw(spriteBatch,"(X) Back",x+300,y);
            blackFont.draw(spriteBatch,"(UP/DOWN) Switch Unit",x+500,y);
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
