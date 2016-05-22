package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 21/05/2016.
 */
public class FlyingAxeman extends Axeman {
    public FlyingAxeman(Allegiance allegiance, int level){
        super(allegiance, level);
        movementType = MovementType.FLYING;
        profession = "Flying Axeman";
        grayTextureFilePath = "gray-axe-flying-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-axe-flying-animation.png";
            textureFilePath = "blue-axe-flying-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-axe-flying-animation.png";
            textureFilePath = "red-axe-flying-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
