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
    private OrthographicCamera camera;
    private GameModel model;
    private BitmapFont font;
    private Unit unit;

    public StatusView(OrthographicCamera camera){
        statusBackground = new Texture("statusBackground.png");
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
        int x = 70;
        int y = (int)(camera.position.y - (camera.viewportHeight/2)) + 50;
        spriteBatch.draw(statusBackground,x,y);

        font.getData().setScale(2,2);


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

        ArrayList<String> stats = new ArrayList<>();
        stats.add(name);
        stats.add(health);
        stats.add(level);
        stats.add(defence);
        stats.add(weapon);
        stats.add(exp);
        stats.add(strength);
        stats.add(might);
        stats.add(skill);
        stats.add(luck);
        stats.add(resistance);


        /*
        private String name;
    private int level = 1;
    private int experience = 1;
    private int health = 20;
    private int maxHealth = 20;
    private int strength = 5;
    private int might = 5;
    private int skill = 5;
    private int speed = 5;
    private int luck = 5;
    private int defence = 5;
    private int resistance = 5;
    private int movement = 3;
    private int constitution = 5;
    private int aid = 5;
         */

        font.draw(spriteBatch,name,x,y+ camera.viewportHeight - 100);
        for(int i = 1; i < stats.size(); i++){
            font.draw(spriteBatch, stats.get(i),x,y + camera.viewportHeight -110 - 30*i);
        }

//        font.draw(spriteBatch, unit.getName(),x,y+camera.viewportHeight/2);

        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {

    }
}
