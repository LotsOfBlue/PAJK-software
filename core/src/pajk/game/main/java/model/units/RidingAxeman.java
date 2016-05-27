package game.main.java.model.units;

/**
 * Created by jonatan on 23/05/2016.
 */
public class RidingAxeman extends Axeman {
    public RidingAxeman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Riding Axeman";
        movement = 6;
        movementType = MovementType.RIDING;
        grayTextureFilePath = "Sprites/Units/Gray/gray-axe-riding-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-axe-riding-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-axe-riding-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-axe-riding-animation.png";
            textureFilePath = "Sprites/Units/Red/red-axe-riding-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
