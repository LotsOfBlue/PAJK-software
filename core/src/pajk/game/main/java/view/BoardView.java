package pajk.game.main.java.view;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.Tile;
import pajk.game.main.java.model.GameModel;


/**
 * Created by palm on 2016-04-22.
 */
public class BoardView extends GameView{

    private GameModel gameModel;
    private Texture img;
    private Texture unit;
    private Texture cursor;
    private Texture overlayMove;
    private Texture overlayAttack;
    private Texture plainsTexture;
    private Texture forestTexture;
    private Texture mountainTexture;
    private Texture waterTexture;
    private int tileWidth = 64;
    private SpriteBatch spriteBatch;

    public BoardView(){
        img = new Texture("grass-tile");
        unit = new Texture("unit-sprite");
        cursor = new Texture("cursor");
        overlayMove = new Texture("overlayBlue.png");
        overlayAttack = new Texture("overlayRed.png");
        plainsTexture = new Texture("grass64.png");
        forestTexture = new Texture("forest64.png");
        mountainTexture=new Texture("mountain64.png");
        waterTexture=new Texture("water64.png");

        this.gameModel = GameModel.getInstance();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        spriteBatch.begin();
        drawBoard();
        drawCursor();
        spriteBatch.end();
    }

    private void drawUnit(int x, int y){
        draw(x,y,unit);
    }

    private void drawTile(Tile tile){
        Texture sprite = img;
        switch (tile.getTerrainType()){
            case "Forest":
                sprite = forestTexture;
                break;
            case "Plains":
                sprite = plainsTexture;
                break;
            case "Mountain":
                sprite = mountainTexture;
                break;
            case "River":
                sprite = waterTexture;
                break;
        }
        draw(tile.getX(),tile.getY(),sprite);

    }

    private void drawCursor(){
        int cursorX = gameModel.getBoard().getCursorTile().getX();
        int cursorY = gameModel.getBoard().getCursorTile().getY();
        draw(cursorX,cursorY,cursor);
    }

    /**
     * Draws texture on board.
     * Converts from "board coordinates" to "pixel coordinates"
     * @param x
     * @param y
     * @param texture
     */
    private void draw(int x, int y, Texture texture){
        int pixelX = x*(tileWidth+1);
        int pixelY = (gameModel.getBoard().getBoardHeight() - 1 - y)*(tileWidth+1);
        spriteBatch.draw(texture,pixelX,pixelY);
    }

    /**
     * Draws board with units and overlay
     *
     */
    private void drawBoard(){
        Board board = gameModel.getBoard();

        for(int x = 0; x < gameModel.getBoard().getBoardWidth(); x++){
            for(int y = 0; y < gameModel.getBoard().getBoardHeight(); y++){
                Tile tile = board.getTile(x, y);
                drawTile(tile);
                if(board.getTile(x,y).hasUnit()){
                    drawUnit(x,y);
                }
                if(board.getTile(x,y).getOverlay() == Tile.Overlay.MOVEMENT){
                    draw(x,y, overlayMove);
                } else if(board.getTile(x,y).getOverlay() == Tile.Overlay.TARGET){
                    draw(x,y, overlayAttack);
                }
            }
        }
    }
}
