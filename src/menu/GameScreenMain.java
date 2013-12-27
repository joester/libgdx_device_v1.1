package menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;


public class GameScreenMain {
	
	public static final int SCREEN_RATIO = 2;
	public static final int CONFIG_HEIGHT = 330 * SCREEN_RATIO;
	public static final int CONFIG_WIDTH = 577 * SCREEN_RATIO;
	
	public static void main(String args[]){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.height = CONFIG_HEIGHT;
		config.width = CONFIG_WIDTH;
		config.useGL20 = true;
		new LwjglApplication(new StateManager(), config);
	}
}
