package pajk.game.main.java.model.units;

import pajk.game.main.java.model.items.ArcaneBolt;

/**
 * Mages are powerful at range, but cannot withstand physical attacks.
 *
 * Created by jonatan on 20/05/2016.
 */
public class Mage extends Unit {
    public Mage(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Mage";
        maxHealth = 14;
        strength = 10;
        skill = 10;
        speed = 8;
        luck = 5;
        defence = 2;
        resistance = 5;
        movement = 5;
        movementType = MovementType.WALKING;

        maxHealthGrowth = 50;
        strengthGrowth = 70;
        skillGrowth = 60;
        speedGrowth = 60;
        luckGrowth = 55;
        defenceGrowth = 30;
        resistanceGrowth = 40;

        this.setWeapon(new ArcaneBolt());

        addExperience((level - 1) * 100);
        health = maxHealth;
        grayTextureFilePath = "Sprites/Units/Gray/gray-tome-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-tome-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-tome-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-tome-animation.png";
            textureFilePath = "Sprites/Units/Red/red-tome-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
