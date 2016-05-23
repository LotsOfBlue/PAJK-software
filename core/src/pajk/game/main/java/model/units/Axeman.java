package pajk.game.main.java.model.units;

import pajk.game.main.java.model.items.IronAxe;

/**
 * Axemen are powerful and have alot of hitpoints, but lack defenses and skill.
 *
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
        this.setWeapon(new IronAxe());

        maxHealthGrowth = 90;
        strengthGrowth = 60;
        skillGrowth = 45;
        speedGrowth = 35;
        luckGrowth = 30;
        defenceGrowth = 40;
        resistanceGrowth = 25;

        addExperience((level - 1) * 100);
        health = maxHealth;
        grayTextureFilePath = "Sprites/Units/Gray/gray-axe-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-axe-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-axe-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-axe-animation.png";
            textureFilePath = "Sprites/Units/Red/red-axe-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
