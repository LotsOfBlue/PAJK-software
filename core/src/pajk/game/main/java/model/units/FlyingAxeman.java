package game.main.java.model.units;

/**
 * Created by jonatan on 21/05/2016.
 */
public class FlyingAxeman extends Axeman {
    public FlyingAxeman(Allegiance allegiance, int level){
        super(allegiance, level);
        movementType = MovementType.FLYING;
        profession = "Flying Axeman";
        grayTextureFilePath = "Sprites/Units/Gray/gray-axe-flying-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-axe-flying-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-axe-flying-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-axe-flying-animation.png";
            textureFilePath = "Sprites/Units/Red/red-axe-flying-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
