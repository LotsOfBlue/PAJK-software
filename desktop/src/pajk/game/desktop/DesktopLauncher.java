package pajk.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.PajkGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PajkGdxGame.WIDTH;
		config.height = PajkGdxGame.HEIGHT;
		config.resizable = true;
		config.title = PajkGdxGame.TITLE;
		new LwjglApplication(new PajkGdxGame(), config);
	}
}
