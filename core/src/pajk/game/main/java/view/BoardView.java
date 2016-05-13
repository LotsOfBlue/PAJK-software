package pajk.game.main.java.view;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import pajk.game.PajkGdxGame;
import pajk.game.main.java.model.*;


/**
 * Created by palm on 2016-04-22.
 * Should maybe be divided into smaller classes?
 */
public class BoardView extends AbstractGameView {

    private GameModel gameModel;
    private Board board;

    private Texture unit;
    private Texture grayUnit;

    private Texture blueSwordUnitSprite;
    private Texture redSwordUnitSprite;
    private Texture blueBowUnitSprite;
    private Texture redBowUnitSprite;

    private Texture hpbarRed;
    private Texture hpbarBlue;


    private Texture cursor;
    private Texture overlayMove;
    private Texture overlayAttack;
    private Texture plainsTexture;
    private Texture forestTexture;
    private Texture mountainTexture;
    private Texture waterTexture;
    private Texture gridTexture;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private final int TILE_WIDTH = 64;

    private OrthographicCamera camera;

    public BoardView(OrthographicCamera camera){
       /* unit = new Texture("unit-sprite.png");
        grayUnit = new Texture("unitGray.png");*/
        shapeRenderer = new ShapeRenderer();

        blueSwordUnitSprite = new Texture("blue-sword-sprite");
        redSwordUnitSprite = new Texture("red-sword-sprite");
        blueBowUnitSprite = new Texture("blue-bow-sprite");
        redBowUnitSprite = new Texture("red-bow-sprite");

        cursor = new Texture("cursor.png");
        overlayMove = new Texture("overlayBlue.png");
        overlayAttack = new Texture("overlayRed.png");
        plainsTexture = new Texture("grass64.png");
        forestTexture = new Texture("forest64.png");
        mountainTexture=new Texture("mountain64.png");
        waterTexture=new Texture("water64.png");
        gridTexture = new Texture("gridOverlay64.png");

        hpbarBlue = new Texture("hpbarBlue.png");
        hpbarRed  = new Texture("hpbarRed.png");
        this.gameModel = GameModel.getInstance();
        this.board = gameModel.getBoard();



        this.camera = camera;

        this.camera.position.set(camera.viewportWidth / 2f, board.getBoardHeight() * TILE_WIDTH - camera.viewportHeight / 2f, 0);
        this.camera.update();
    }

    private void resize(int width){

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

//        spriteBatch.begin();
        drawBoard();
        drawCursor();

//        spriteBatch.end();
    }



    private void drawUnit(int x, int y){
        Unit myUnit = board.getTile(x,y).getUnit();
        Unit.UnitClass myUnitClass = myUnit.getUnitClass();
        Texture myTexture = new Texture("gray-sword-sprite");
        if(myUnit.getUnitState() == Unit.UnitState.ATTACKED){
            switch (myUnitClass) {
                case BOW:
                    myTexture = new Texture("gray-bow-sprite");
                    break;
                case SWORD:
                    myTexture = new Texture("gray-sword-sprite");
                    break;
            }
        }
        else if(myUnit.getAllegiance() == Unit.Allegiance.PLAYER){
            switch (myUnitClass) {
                case BOW:
                    myTexture = new Texture("blue-bow-sprite");
                    break;
                case SWORD:
                    myTexture = new Texture("blue-sword-sprite");
                    break;
            }
        }else{
            switch (myUnitClass) {
                case BOW:
                    myTexture = new Texture("red-bow-sprite");
                    break;
                case SWORD:
                    myTexture = new Texture("red-sword-sprite");
                    break;
            }
        }
        draw(x,y,myTexture);

        drawHealthbar(x,y);
    }

    private void drawHealthbar(int x, int y){
        int pixelX = getPixelCoordX(x);
        int pixelY = getPixelCoordY(y);

        draw(x,y,hpbarRed);
        double currentHp = 15;
        double maxhp = 20;
        int hpPixels = (int)(64 * (currentHp / maxhp));
        TextureRegion txtReg = new TextureRegion(hpbarBlue, 0, 0, hpPixels, 64);
        spriteBatch.begin();
        spriteBatch.draw(txtReg,pixelX,pixelY);
        spriteBatch.end();
    }

    private void drawTile(Tile tile){
        Texture texture = plainsTexture;
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
        draw(tile.getX(),tile.getY(),gridTexture);

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
        spriteBatch.begin();
        int pixelX = getPixelCoordX(x);
        int pixelY = getPixelCoordY(y);
        spriteBatch.draw(texture,pixelX,pixelY);
        spriteBatch.end();
    }

    private int getPixelCoordX(int boardCoordX){
        return boardCoordX * TILE_WIDTH;
    }
    private int getPixelCoordY(int boardCoordY){
        return (gameModel.getBoard().getBoardHeight() - 1 - boardCoordY)*(TILE_WIDTH);
    }

    /**
     * Draws board with units and overlay
     *
     */
    private void drawBoard(){

        updateCamera(board);
        for(int x = 0; x < board.getBoardWidth(); x++){
            for(int y = 0; y < board.getBoardHeight(); y++){
                Tile tile = board.getTile(x, y);
                drawTile(tile);
                if(board.getTile(x,y).hasUnit()){
                    drawUnit(x,y);
                    /*if(board.getTile(x,y).getUnit().getUnitState() == Unit.UnitState.ATTACKED){
                        draw(x,y,grayUnit);
                    } else {
                        drawUnit(x,y);
                    }*/
                }
                if(board.getTile(x,y).getOverlay() == Tile.Overlay.MOVEMENT){
                    draw(x,y, overlayMove);
                } else if(board.getTile(x,y).getOverlay() == Tile.Overlay.TARGET){
                    draw(x,y, overlayAttack);
                }
            }
        }
    }

    private void updateCamera(Board board){
        Tile cursor = board.getCursorTile();
        //UP
        if (camera.position.y + camera.viewportHeight / 2 < (board.getBoardHeight() - cursor.getY() + 1) * TILE_WIDTH){
            if (camera.position.y + camera.viewportHeight / 2 + 16 <= board.getBoardHeight() * TILE_WIDTH){
                camera.translate(0, 16);
            }else if (camera.position.y + camera.viewportHeight / 2 + 4 <= board.getBoardHeight() * TILE_WIDTH){
                camera.translate(0, 4);
            }
        }
        //DOWN
        if (camera.position.y - camera.viewportHeight / 2 > (board.getBoardHeight() - cursor.getY() - 2) * TILE_WIDTH){
            if (camera.position.y - camera.viewportHeight / 2 - 16 >= 0){
                camera.translate(0, -16);
            }else if (camera.position.y - camera.viewportHeight / 2 - 4 >= 0){
                camera.translate(0, -4);
            }
        }
        //RIGHT
        if (camera.position.x + camera.viewportWidth / 2 < (cursor.getX() + 2) * TILE_WIDTH){
            if (camera.position.x + camera.viewportWidth / 2 + 16 <= board.getBoardWidth() * TILE_WIDTH){
                camera.translate(16, 0);
            }else if (camera.position.x + camera.viewportWidth / 2 + 4 <= board.getBoardWidth() * TILE_WIDTH){
                camera.translate(4, 0);
            }
        }
        //LEFT
        if (camera.position.x - camera.viewportWidth / 2 > (cursor.getX() - 1) * TILE_WIDTH){
            if (camera.position.x - camera.viewportWidth / 2 - 16 > 0){
                camera.translate(-16, 0);
            }else if (camera.position.x - camera.viewportWidth / 2 - 4 >= 0){
                camera.translate(-4, 0);
            }
        }
    }


}
