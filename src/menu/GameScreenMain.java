package menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;


public class GameScreenMain {
	
	public static final float SCREEN_RATIO = 0.5f;
	public static final int CONFIG_HEIGHT = (int) (800 * SCREEN_RATIO);
	public static final int CONFIG_WIDTH = (int) (1280 * SCREEN_RATIO);
	
	public static void main(String args[]){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.height = CONFIG_HEIGHT;
		config.width = CONFIG_WIDTH;
		config.useGL20 = true;
		new LwjglApplication(new StateManager(), config);
	}
}
