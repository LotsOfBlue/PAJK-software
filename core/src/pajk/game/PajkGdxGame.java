package pajk.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.controller.Controller;
import pajk.game.main.java.model.StateManager;
import pajk.game.main.java.view.BoardView;

public class PajkGdxGame extends ApplicationAdapter {
	public static final int WIDTH = 650;
	public static final int HEIGHT = 650;
	public static final String TITLE = "Pajkification";

	private StateManager gameModel;
	private Controller gameController;
	private BoardView gameView;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameModel = StateManager.getInstance();
		gameController = new Controller(gameModel);
		gameView = new BoardView();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameView.render(batch);
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
		}
	}
}
