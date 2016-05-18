package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.StatusState;
import pajk.game.main.java.model.Unit;

import java.util.ArrayList;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusView extends AbstractGameView{

    private Texture statusBackground;
    private Texture unitImage;
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont font;
    private Unit unit;

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
        this.camera = camera;
        font = new BitmapFont();
        model = GameModel.getInstance();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(model.getState().getClass() == StatusState.class){
            drawStatusScreen(spriteBatch);
        }
    }


    private void drawStatusScreen(SpriteBatch spriteBatch){

        model = GameModel.getInstance();
        unit = GameModel.getInstance().getActiveUnit();
        spriteBatch.begin();
        int x = (int)(camera.position.x - camera.viewportWidth/2)+70;
        int y = (int)(camera.position.y - (camera.viewportHeight/2)) + 50;
        spriteBatch.draw(statusBackground,x,y);

        font.getData().setScale(2,2);

        //Creates strings with info from active unit
        String name = unit.getName();
        String health = "Health: "+unit.getHealth() + "/" + unit.getMaxHealth();
        String level = "Level: " +unit.getLevel();
        String defence = "Defence: " +unit.getDefence();
        String weapon = "Weapon: " +unit.getWeapon().getWeaponType();
        String exp = "Experience: " +unit.getExperience();
        String strength = "Strength: " +unit.getStrength();
        String might = "Might: " +unit.getMight();
        String skill = "Skill: " +unit.getSpeed();
        String luck = "Luck: " + unit.getLuck();
        String resistance = "Resistance: " +unit.getResistance();
        String speed = "Speed: "+ unit.getSpeed();
        String movement = "Movement: " +unit.getMovement();
        String constitution = "Constitution: " +unit.getConstitution();
        String aid = "Aid: " +unit.getAid();


        //Draws name, image, hp, level, exp and weapon to the left
        font.draw(spriteBatch,name,x+20,y+ camera.viewportHeight - 110);
        spriteBatch.draw(unitImage,x+20,y+ camera.viewportHeight - 100 -50 -130);
        font.draw(spriteBatch,health,x+20,y+ camera.viewportHeight - 100 -50 -150);
        font.draw(spriteBatch,level,x+20,y+ camera.viewportHeight - 100 -85 -150);
        font.draw(spriteBatch,exp,x+20,y+ camera.viewportHeight - 100 -120 -150);
        font.draw(spriteBatch,weapon,x+20,y+ camera.viewportHeight - 100 -155 -150);


        ArrayList<String> stats = new ArrayList<>();

        stats.add(strength);
        stats.add(skill);
        stats.add(speed);
        stats.add(defence);
        stats.add(resistance);
        stats.add(luck);
        stats.add(might);
        stats.add(movement);
        stats.add(constitution);
        stats.add(aid);

        //draws stats on two columns, splitting it halfways
        x+=300;
        for(int i = 0; i < stats.size(); i++){
            int ydiff = i * 35;
            if(i == stats.size()/2){
                x += 200;
            }
            if(i>=stats.size()/2){
                ydiff = 35*(i-stats.size()/2);
            }
            font.draw(spriteBatch, stats.get(i),x+20,y + camera.viewportHeight -145 - ydiff-20);
        }


        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

    }
}
