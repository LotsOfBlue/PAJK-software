package pajk.game.main.java.model.units;

/**
 * Created by jonatan on 21/05/2016.
 */
public class FlyingSwordsman extends Swordsman {
    public FlyingSwordsman(Allegiance allegiance, int level){
        super(allegiance, level);
        movementType = MovementType.FLYING;
        profession = "Flying Swordsman";
        grayTextureFilePath = "Sprites/Units/Gray/gray-sword-flying-sprite.png";
        if(allegiance == Allegiance.PLAYER){
            animationFilePath = "Sprites/Units/Blue/blue-sword-flying-animation.png";
            textureFilePath = "Sprites/Units/Blue/blue-sword-flying-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-blue.png";
        } else {
            animationFilePath = "Sprites/Units/Red/red-sword-flying-animation.png";
            textureFilePath = "Sprites/Units/Red/red-sword-flying-sprite.png";
            portraitFilePath = "Sprites/Units/shrek-red.png";
        }
    }
}
