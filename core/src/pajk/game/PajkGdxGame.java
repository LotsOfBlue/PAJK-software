package pajk.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pajk.game.main.java.States.GameStateManager;
import pajk.game.main.java.States.MainMenuState;
import pajk.game.main.java.controller.Controller;
import pajk.game.main.java.model.Model;

public class PajkGdxGame extends ApplicationAdapter {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	public static final String TITLE = "Pajkification";

	private GameStateManager gameStateManager;
	Controller gameController = new Controller(new Model());

	private SpriteBatch batch;
//	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
		gameStateManager = new GameStateManager();
		gameStateManager.push(new MainMenuState(gameStateManager));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());	//update time
		gameStateManager.render(batch);
		listenToKeys();
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}


	private void listenToKeys(){
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			gameController.leftInput();
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			gameController.rightInput();
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			gameController.upInput();
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			gameController.rightInput();
		} else if (Gdx.input.isKeyPressed(Input.Keys.Z)){
			gameController.enterInput();
		} else if (Gdx.input.isKeyPressed(Input.Keys.X)){
			gameController.backInput();
		}
	}
}
