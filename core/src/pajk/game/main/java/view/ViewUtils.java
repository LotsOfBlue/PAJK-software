package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pajk.game.main.java.model.Tile;

/**
 * Created by jonatan on 21/05/2016.
 */
public class ViewUtils {

    public static final int TILE_WIDTH = 64;

    private static Texture plainsTexture = new Texture("Sprites/Tiles/grass64.png");
    private static Texture forestTexture = new Texture("Sprites/Tiles/forest64.png");
    private static Texture mountainTexture = new Texture("Sprites/Tiles/mountain64.png");
    private static Texture waterTexture = new Texture("Sprites/Tiles/water64.png");
    private static Texture wallTexture = new Texture("Sprites/Tiles/wall64.png");
    private static Texture floorTexture = new Texture("Sprites/Tiles/floor64.png");

    public static Texture getTileTexture(Tile tile){
        switch (tile.getTerrainType()){
            case "Forest":
                return forestTexture;
            case "Plains":
                return plainsTexture;
            case "Mountain":
                return mountainTexture;
            case "River":
                return waterTexture;
            case "Wall":
                return wallTexture;
            case "Floor":
                return floorTexture;
        }
        return plainsTexture;
    }

}
