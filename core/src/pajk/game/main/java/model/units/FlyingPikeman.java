package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 21/05/2016.
 */
public class FlyingPikeman extends Pikeman {
    public FlyingPikeman(Allegiance allegiance, int level){
        super(allegiance, level);
        movementType = MovementType.FLYING;
        profession = "Flying Pikeman";
        grayTextureFilePath = "Sprites/Units/Gray/gray-pike-flying-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-pike-flying-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-pike-flying-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-pike-flying-animation.png";
            textureFilePath = "Sprites/Units/Red/red-pike-flying-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
