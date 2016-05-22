package pajk.game.main.java.model.units;

/**
 * Archers are quick and skilled, can attack at range, but have no defenses to speak of.
 *
 * Created by Gustav on 2016-05-19.
 */
public class Archer extends Unit {
    public Archer(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Archer";
        maxHealth = 17;
        strength = 6;
        skill = 7;
        speed = 9;
        luck = 5;
        defence = 3;
        resistance = 0;
        movement = 5;
        movementType = MovementType.WALKING;

        maxHealthGrowth = 70;
        strengthGrowth = 40;
        skillGrowth = 60;
        speedGrowth = 60;
        luckGrowth = 55;
        defenceGrowth = 20;
        resistanceGrowth = 30;

        addExperience((level - 1) * 100);
        health = maxHealth;
        grayTextureFilePath = "gray-bow-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-bow-animation.png";
            textureFilePath = "blue-bow-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-bow-animation.png";
            textureFilePath = "red-bow-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
