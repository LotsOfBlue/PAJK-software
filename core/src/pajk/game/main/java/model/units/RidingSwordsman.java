package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 23/05/2016.
 */
public class RidingSwordsman extends Swordsman {
    public RidingSwordsman(Allegiance allegiance, int level) {
        super(allegiance, level);
        profession = "Riding Swordsman";
        movementType = MovementType.RIDING;
        grayTextureFilePath = "gray-sword-riding-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-sword-riding-animation.png";
            textureFilePath = "blue-sword-riding-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-sword-riding-animation.png";
            textureFilePath = "red-sword-riding-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
