package pajk.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.controller.Controller;
import pajk.game.main.java.model.CombatState;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.view.BoardView;
import pajk.game.main.java.view.CombatView;

public class PajkGdxGame extends ApplicationAdapter {
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	public static final String TITLE = "Pajkification";

	private GameModel gameModel;
	private Controller gameController;
	private BoardView boardView;
	private CombatView combatView;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameModel = GameModel.getInstance();
		gameModel.setState(GameModel.StateName.MAIN_STATE);
		gameController = new Controller(gameModel);
		boardView = new BoardView();
		combatView = new CombatView();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor((float)0.46, (float)0.33, (float)0.16, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		boardView.render(batch);
		if(gameModel.getState().getClass() == CombatState.class){//TODO make this better, move into boardview even?
			CombatState combatState = (CombatState)gameModel.getState();
			if (combatState.isCalcDone()){
				combatView.update(new Float(1.1));
				combatView.render(batch);
			}
		}
		listenToKeys();
	}
	
	private void listenToKeys(){
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			gameController.leftInput();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			gameController.rightInput();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			gameController.upInput();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			gameController.downInput();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
			gameController.enterInput();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
			gameController.backInput();
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){//TODO REMOVE? alt change to a "skip" button
			gameController.combatDoneInput();
		}
	}
}
