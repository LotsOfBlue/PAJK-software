package pajk.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.main.java.controller.PajkGdxGameController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PajkGdxGameController.WIDTH;
		config.height = PajkGdxGameController.HEIGHT;
		config.resizable = false;
		config.title = PajkGdxGameController.TITLE;
		new LwjglApplication(new PajkGdxGameController(), config);
	}
}
