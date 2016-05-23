package pajk.game.main.java.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pajk.game.main.java.model.*;
import pajk.game.main.java.model.states.*;
import pajk.game.main.java.model.units.Unit;

import java.util.*;

/**
 * Created by palm on 2016-04-22.
 */
public class BoardView extends AbstractGameView {

    private GameModel gameModel;
    private Board board;

    private HashMap<String, Texture> unitTextureMap = new HashMap<>();

    private Texture hpbarRed;
    private Texture hpbarBlue;
    private Texture hpbarContainer;

    private BitmapFont font;

    private Texture cursor;
    private Texture overlayMove;
    private Texture overlayAttack;
    private Texture gridTexture;

    private Texture tooltipBackground;

    private SpriteBatch spriteBatch;
    private final int TILE_WIDTH = 64;

    private OrthographicCamera camera;

    /**
     * Creates a BoardView
     * Draw the board with all tiles units and overlay.
     * Also updates the camera.
     * @param camera the camera where the board should be drawn.
     */
    public BoardView(OrthographicCamera camera){
        font = new BitmapFont();

        cursor = new Texture("Sprites/Tiles/cursor.png");
        overlayMove = new Texture("Sprites/Tiles/overlayBlue.png");
        overlayAttack = new Texture("Sprites/Tiles/overlayRed.png");
        gridTexture = new Texture("Sprites/Tiles/gridOverlay64.png");

        tooltipBackground = new Texture("Menus/tooltipBackground.png");

        hpbarBlue = new Texture("Sprites/Units/hpbarBlue.png");
        hpbarRed  = new Texture("Sprites/Units/hpbarRed.png");
        this.gameModel = GameModel.getInstance();
        this.board = gameModel.getBoard();

        this.camera = camera;

        this.camera.position.set(camera.viewportWidth / 2f, board.getBoardHeight() * TILE_WIDTH - camera.viewportHeight / 2f, 0);
        this.camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        //Always draw the board, except when the main menu is open
        if (!(gameModel.getState() instanceof MainMenuState)) {
            this.spriteBatch = spriteBatch;

            camera.update();
            spriteBatch.setProjectionMatrix(camera.combined);

            spriteBatch.begin();
            drawBoard();
            if(gameModel.getState().getClass() != CombatState.class) {
                drawCursor();
            }
            if(gameModel.getBoard().getCursorTile().hasUnit() && gameModel.getState().getClass() == MainState.class) {
                drawButtonText();
            }

            //Don't always draw unit tooltips
            if(     gameModel.getBoard().getCursorTile().hasUnit() &&
                    !(gameModel.getState() instanceof EnemyTurnState) &&
                    !(gameModel.getState() instanceof CombatState) &&
                    !(gameModel.getState() instanceof StatusState) &&
                    !(gameModel.getState() instanceof EndState)){
                drawTooltip();
            }
            spriteBatch.end();
        }
    }

    private void drawUnit(int x, int y){
        Unit myUnit = board.getTile(x,y).getUnit();
        String str;

        if(myUnit.getUnitState() == Unit.UnitState.DONE){
            str = myUnit.getGrayTextureFilePath();
        } else {
            str = myUnit.getTextureFilePath();
        }

        if(unitTextureMap.isEmpty() || !unitTextureMap.containsKey(str)){
            unitTextureMap.put(str, new Texture(str));
        }

        if (!(gameModel.getCurrentStateName().equals(GameModel.StateName.COMBAT)) || (gameModel.getActiveUnit() != myUnit && gameModel.getTargetUnit() != myUnit)){
            draw(x,y,unitTextureMap.get(str));
        }

        drawHealthbar(myUnit, x, y);
    }

    /**
     * Draws healthbar over unit and scales it with the unit's health.
     * @param unit the unit which healthbar will be drawn
     * @param x the x boardcoordinate where the unit's healthbar should be drawn
     * @param y the y boardcoordinate where the unit's healthbar shoudl be drawn
     */
    private void drawHealthbar(Unit unit, int x, int y){
        int pixelX = getPixelCoordX(x);
        int pixelY = getPixelCoordY(y);

        //draw(x,y,hpbarContainer);
//        draw(x, y, hpbarRed);
        double currentHp = unit.getHealth();
        double maxhp = unit.getMaxHealth();
        int hpPixels = (int) (64 * (currentHp / maxhp));
        TextureRegion txtReg;
        if(unit.getAllegiance() == Unit.Allegiance.PLAYER) {
            txtReg = new TextureRegion(hpbarBlue, 0, 0, hpPixels, 64);
        } else {
            txtReg = new TextureRegion(hpbarRed, 0, 0, hpPixels, 64);
        }

        spriteBatch.draw(txtReg, pixelX, pixelY);
    }

    /**
     * Checks what terrain the tile has and draws it.
     * @param tile the tile to be drawn.
     */
    private void drawTile(Tile tile){
        Texture texture = ViewUtils.getTileTexture(tile);
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
        int pixelX = getPixelCoordX(x);
        int pixelY = getPixelCoordY(y);
        spriteBatch.draw(texture,pixelX,pixelY);
    }

    /**
     * Returns the where in pixels something is if given the board coordinates
     * @param boardCoordX the coordinates in board matrix
     * @return the pixel coordinates of where on the screen the board should be represented.
     */
    private int getPixelCoordX(int boardCoordX){
        return boardCoordX * TILE_WIDTH;
    }
    private int getPixelCoordY(int boardCoordY){
        return (gameModel.getBoard().getBoardHeight() - 1 - boardCoordY)*(TILE_WIDTH);
    }

    private void drawTooltip(){

        float x = camera.position.x - (camera.viewportWidth/2) +40;
        if(shouldDrawTooltipRight()){
            x = camera.position.x + (camera.viewportHeight/2);
        }
        float y = camera.position.y - (camera.viewportHeight/2) +10;
        spriteBatch.draw(tooltipBackground,x,y);

        Unit unit = GameModel.getInstance().getBoard().getCursorTile().getUnit();
        String healthText = unit.getHealth() +"/"+unit.getMaxHealth() +" hp";
        String lvlText = "Lvl: " +unit.getLevel();
        String nameText = unit.getName();

        font.getData().setScale(2,2);
        font.setColor(Color.BLACK);
        font.draw(spriteBatch,healthText,x+15,y+110);
        font.draw(spriteBatch,lvlText,x+15,y+80);
        font.getData().setScale(2f - nameText.length()/16f);
        font.draw(spriteBatch, nameText, x+15, y+140);
    }

    private boolean shouldDrawTooltipRight(){
        int tileWidth = 64;
        int x = gameModel.getBoard().getCursorTile().getX();

        return x * tileWidth < camera.position.x;
    }

    /**
     * Draws board with units and overlay
     */
    private void drawBoard(){

        updateCamera(board);
        for(int x = 0; x < board.getBoardWidth(); x++){
            for(int y = 0; y < board.getBoardHeight(); y++){
                Tile tile = board.getTile(x, y);
                if (isWithinCamera(tile)) { // Only draw the tiles that can be seen.
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

    /**
     * Requires that cursor tile is on unit. Draws info text on button of screen that describes what will happen
     * if you press a button.
     */
    private void drawButtonText() {
        font.setColor(Color.BLACK);
        font.getData().setScale((float) 1.5, (float) 1.5);
        int x = (int) (camera.position.x - 400);

        if(shouldDrawButtonTextRight()){
            x = (int) (camera.position.x-200);
        }

        int y = (int) (camera.position.y - camera.viewportHeight / 2 + 50);

        if (gameModel.getBoard().getCursorTile().getUnit().getAllegiance() == Unit.Allegiance.AI) {
            font.draw(spriteBatch, "(Z) Status", x, y);
        } else {
            font.draw(spriteBatch, "(Z) Menu", x, y);
        }
    }

    private boolean isWithinCamera(Tile tile){
        boolean verdict = true;
        int x = tile.getX();
        int y = tile.getY();
        if (camera.position.y + camera.viewportHeight / 2 < (board.getBoardHeight() - y - 1) * TILE_WIDTH ||
                camera.position.y - camera.viewportHeight / 2 > (board.getBoardHeight() - y + 1) * TILE_WIDTH ||
                camera.position.x + camera.viewportWidth / 2 < (x + 0) * TILE_WIDTH ||
                camera.position.x - camera.viewportWidth / 2 > (x + 1) * TILE_WIDTH){
            verdict = false;
        }
        return verdict;
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

    private boolean shouldDrawButtonTextRight(){
        int tileWidth = 64;
        int x = gameModel.getBoard().getCursorTile().getX();
        return x * tileWidth > (camera.viewportWidth/2);
    }

    public Board getBoard(){return board;}

    public void setBoard(Board board){
        this.board = board;
    }
}
