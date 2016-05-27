package game.main.java.model.units;

/**
 * Created by jonatan on 23/05/2016.
 */
public class RidingSwordsman extends Swordsman {
    public RidingSwordsman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Riding Swordsman";
        movement = 6;
        movementType = MovementType.RIDING;
        grayTextureFilePath = "Sprites/Units/Gray/gray-sword-riding-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-sword-riding-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-sword-riding-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-sword-riding-animation.png";
            textureFilePath = "Sprites/Units/Red/red-sword-riding-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
