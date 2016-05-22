package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 23/05/2016.
 */
public class RidingPikeman extends Pikeman{
    public RidingPikeman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Riding Pikeman";
        movementType = MovementType.RIDING;
        grayTextureFilePath = "gray-pike-riding-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-pike-riding-animation.png";
            textureFilePath = "blue-pike-riding-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-pike-riding-animation.png";
            textureFilePath = "red-pike-riding-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
