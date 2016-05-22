package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 23/05/2016.
 */
public class RidingAxeman extends Axeman {
    public RidingAxeman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Riding Axeman";
        movementType = MovementType.RIDING;
        grayTextureFilePath = "gray-axe-riding-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-axe-riding-animation.png";
            textureFilePath = "blue-axe-riding-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-axe-riding-animation.png";
            textureFilePath = "red-axe-riding-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
