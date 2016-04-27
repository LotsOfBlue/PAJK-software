package pajk.game.main.java.view;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import pajk.game.main.java.model.StateManager;


/**
 * Created by palm on 2016-04-22.
 */
public class BoardView extends GameView{


    private StateManager gameModel;
    private Texture img;
    private Texture unit;
    private Texture cursor;
    private int tileWidth = 64;

    public BoardView(){
        img = new Texture("grass-tile");
        unit = new Texture("unit-sprite");
        cursor = new Texture("cursor");
        this.gameModel = StateManager.getInstance();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        drawBoard(spriteBatch);
        drawCursor(spriteBatch);
        spriteBatch.end();
    }

    private void drawUnit(SpriteBatch spriteBatch,int x, int y){
        draw(spriteBatch,x,y,unit);
    }

    private void drawTile(SpriteBatch spriteBatch, int x, int y){
        draw(spriteBatch,x,y,img);
    }

    private void drawCursor(SpriteBatch spriteBatch){
        int cursorX = gameModel.getBoard().getCursorTile().getX();
        int cursorY = gameModel.getBoard().getCursorTile().getY();
        draw(spriteBatch,cursorX,cursorY,cursor);
    }

    private void draw(SpriteBatch spriteBatch, int x, int y, Texture texture){
        int pixelX = x*(tileWidth+1);
        int pixelY = (gameModel.getBoard().getBoardHeight() - 1 - y)*(tileWidth+1);
        spriteBatch.draw(texture,pixelX,pixelY);
    }

    private void drawBoard(SpriteBatch spriteBatch){

        for(int x = 0; x < gameModel.getBoard().getBoardWidth(); x++){
            for(int y = 0; y < gameModel.getBoard().getBoardHeight(); y++){
                drawTile(spriteBatch,x,y);
                if(gameModel.getBoard().getTile(x,y).hasUnit()){   //
                    //gameModel.getBoard().getTile(y,x).hasUnit();
                    drawUnit(spriteBatch,x,y);
                }

            }
        }

    }


}
