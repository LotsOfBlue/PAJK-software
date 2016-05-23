package pajk.game.main.java.model.units;

import pajk.game.main.java.model.items.IronPike;

/**
 * Pikemen are defensive, but slow.
 *
 * Created by Gustav on 2016-05-19.
 */
public class Pikeman extends Unit {
    public Pikeman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Pikeman";
        maxHealth = 19;
        strength = 7;
        skill = 4;
        speed = 5;
        luck = 3;
        defence = 8;
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

        this.setWeapon(new IronPike());

        addExperience((level - 1) * 100);
        health = maxHealth;
        grayTextureFilePath = "Sprites/Units/Gray/gray-pike-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-pike-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-pike-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-pike-animation.png";
            textureFilePath = "Sprites/Units/Red/red-pike-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
