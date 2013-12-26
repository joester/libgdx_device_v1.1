package menu;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class GameScreenMain {
	
	public static final float SCREEN_RATIO = 3f;
	public static final int CONFIG_WIDTH = (int) (300 * SCREEN_RATIO);
	public static final int CONFIG_HEIGHT = (int) (200 * SCREEN_RATIO);
	
	
	public static void main(String args[]){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.height = CONFIG_HEIGHT;
		config.width = CONFIG_WIDTH;
		config.useGL20 = true;
		new LwjglApplication(new StateManager(), config);
	}
}
