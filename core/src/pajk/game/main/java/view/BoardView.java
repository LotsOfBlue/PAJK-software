package pajk.game.main.java.view;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import pajk.game.PajkGdxGame;
import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.Tile;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.UnitMenuState;

import java.util.Map;


/**
 * Created by palm on 2016-04-22.
 * Should maybe be divided into smaller classes?
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
    private Texture background;
    private SpriteBatch spriteBatch;
    private BitmapFont font;        //TODO change to "freetype" instead, use gradle
    private Texture menuOverlay;
    private final int TILE_WIDTH = 64;

    public BoardView(){
        img = new Texture("grass-tile");
        unit = new Texture("unit-sprite");
        cursor = new Texture("cursor.png");
        overlayMove = new Texture("overlayBlue.png");
        overlayAttack = new Texture("overlayRed.png");
        plainsTexture = new Texture("grass64.png");
        forestTexture = new Texture("forest64.png");
        mountainTexture=new Texture("mountain64.png");
        waterTexture=new Texture("water64.png");
        background = new Texture("background.png");
        font = new BitmapFont();
        menuOverlay = new Texture("menuOverlay.png");
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
        drawMenu();
        spriteBatch.end();
    }

    private void drawMenu(){
        if(gameModel.getCurrentStateName() == GameModel.StateName.UNIT_MENU) {
            int x = 2;
            int y = 5 * 65 + 2;
            spriteBatch.draw(background, x, y);
            font.getData().setScale(2, 2);
            font.draw(spriteBatch, "Move", x+5, PajkGdxGame.HEIGHT - 30);
            font.draw(spriteBatch, "Attack", x+5, PajkGdxGame.HEIGHT - 70);
            font.draw(spriteBatch, "Wait", x+5, PajkGdxGame.HEIGHT - 110);

            Map<Integer,String> menuMap = gameModel.getMenuState().getMenuMap();
            int selectedItem = gameModel.getMenuState().getMenuItemSelected();

            if(selectedItem == 0){
                spriteBatch.draw(menuOverlay, x+5, PajkGdxGame.HEIGHT - 70);
            } else if (selectedItem == 1){
                spriteBatch.draw(menuOverlay, x+5, PajkGdxGame.HEIGHT - 110);
            } else if (selectedItem == 2){
                spriteBatch.draw(menuOverlay, x+5, PajkGdxGame.HEIGHT - 150);
            }

            font.draw(spriteBatch, "Move", x+5, PajkGdxGame.HEIGHT - 30);
            font.draw(spriteBatch, "Attack", x+5, PajkGdxGame.HEIGHT - 70);
            font.draw(spriteBatch, "Wait", x+5, PajkGdxGame.HEIGHT - 110);

        }
    }

    private void drawUnit(int x, int y){
        draw(x,y,unit);
    }

    private void drawTile(Tile tile){
        Texture texture = img;
        switch (tile.getTerrainType()){
            case "Forest":
                texture = forestTexture;
                break;
            case "Plains":
                texture = plainsTexture;
                break;
            case "Mountain":
                texture = mountainTexture;
                break;
            case "River":
                texture = waterTexture;
                break;
        }
        draw(tile.getX(),tile.getY(),texture);

    }

    private void drawCursor(){
        int cursorX = gameModel.getBoard().getCursorTile().getX();
        int cursorY = gameModel.getBoard().getCursorTile().getY();
        draw(cursorX,cursorY,cursor);
    }

    /**
     * Draws texture on board.
     * Converts from "board coordinates" to "pixel coordinates"
     * @param x X "board" coordinate
     * @param y Y "board" coordinate
     * @param texture The tile's texture
     */
    private void draw(int x, int y, Texture texture){
        int pixelX = x*(TILE_WIDTH +1);
        int pixelY = (gameModel.getBoard().getBoardHeight() - 1 - y)*(TILE_WIDTH +1);
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
