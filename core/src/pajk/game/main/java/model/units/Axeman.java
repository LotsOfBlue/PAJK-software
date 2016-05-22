package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 19/05/2016.
 */
public class Axeman extends Unit {
    public Axeman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Axeman";
        maxHealth = 21;
        strength = 8;
        skill = 3;
        speed = 5;
        luck = 4;
        defence = 6;
        resistance = 0;
        movement = 5;
        movementType = MovementType.WALKING;

        maxHealthGrowth = 90;
        strengthGrowth = 60;
        skillGrowth = 45;
        speedGrowth = 35;
        luckGrowth = 30;
        defenceGrowth = 50;
        resistanceGrowth = 25;

        addExperience((level - 1) * 100);
        health = maxHealth;
        grayTextureFilePath = "gray-axe-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-axe-animation.png";
            textureFilePath = "blue-axe-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-axe-animation.png";
            textureFilePath = "red-axe-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
