package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 21/05/2016.
 */
public class FlyingSwordsman extends Swordsman {
    public FlyingSwordsman(Allegiance allegiance, int level){
        super(allegiance, level);
        movementType = MovementType.FLYING;
        profession = "Flying Swordsman";
        grayTextureFilePath = "gray-sword-flying-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "blue-sword-flying-animation.png";
            textureFilePath = "blue-sword-flying-sprite.png";
            portraitFilePath = "shrek-blue.png";
        } else {
            animationFilePath = "red-sword-flying-animation.png";
            textureFilePath = "red-sword-flying-sprite.png";
            portraitFilePath = "shrek-red.png";
        }
    }
}
