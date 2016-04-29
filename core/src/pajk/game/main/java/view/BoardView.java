package pajk.game.main.java.view;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
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
    private int tileWidth = 64;
    private SpriteBatch spriteBatch;

    public BoardView(){
        img = new Texture("grass-tile");
        unit = new Texture("unit-sprite");
        cursor = new Texture("cursor");
        overlayMove = new Texture("overlayBlue.png");
        overlayAttack = new Texture("overlayRed.png");

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

    private void drawTile(int x, int y){
        draw(x,y,img);

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

        for(int x = 0; x < gameModel.getBoard().getBoardWidth(); x++){
            for(int y = 0; y < gameModel.getBoard().getBoardHeight(); y++){
                drawTile(x,y);
                if(gameModel.getBoard().getTile(x,y).hasUnit()){
                    drawUnit(x,y);
                }
                if(gameModel.getBoard().getTile(x,y).getOverlay() == Tile.Overlay.MOVEMENT){
                    draw(x,y, overlayMove);
                } else if(gameModel.getBoard().getTile(x,y).getOverlay() == Tile.Overlay.TARGET){
                    draw(x,y, overlayAttack);
                }
            }
        }
    }
}
