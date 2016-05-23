package pajk.game.main.java.model.units;

import pajk.game.main.java.model.items.HuntingBow;

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
        this.setWeapon(new HuntingBow());

        maxHealthGrowth = 70;
        strengthGrowth = 40;
        skillGrowth = 60;
        speedGrowth = 60;
        luckGrowth = 55;
        defenceGrowth = 20;
        resistanceGrowth = 30;

        addExperience((level - 1) * 100);
        health = maxHealth;
        grayTextureFilePath = "Sprites/Units/Gray/gray-bow-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-bow-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-bow-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-bow-animation.png";
            textureFilePath = "Sprites/Units/Red/red-bow-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
