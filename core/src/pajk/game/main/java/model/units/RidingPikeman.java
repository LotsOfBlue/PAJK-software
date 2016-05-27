package game.main.java.model.units;

/**
 * Created by jonatan on 23/05/2016.
 */
public class RidingPikeman extends Pikeman{
    public RidingPikeman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Riding Pikeman";
        movement = 6;
        movementType = MovementType.RIDING;
        grayTextureFilePath = "Sprites/Units/Gray/gray-pike-riding-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-pike-riding-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-pike-riding-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-pike-riding-animation.png";
            textureFilePath = "Sprites/Units/Red/red-pike-riding-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
