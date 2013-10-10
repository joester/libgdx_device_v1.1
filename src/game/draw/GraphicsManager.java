package game.draw;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GraphicsManager {
	private HashMap<Integer, Texture> imgs = new HashMap<Integer, Texture>();
	
	//Constructor takes no arguments and puts all FileHandle objects into a HashMap to a corresponding ID.
	//IDs should match those of the character, device, and monsters.
	//Backgrounds start at 100.
	//Menu art starts at 200.

	public GraphicsManager(){
		
	}
	public void load(int start, String... paths)
	{
		for(int i = 0; i < paths.length; i++) {
			imgs.put(start + i, new Texture(Gdx.files.internal(paths[i])));
		}
	}
	
	//Input object ID to get Texture for it.
	public Texture ID(int ID){
		return imgs.get(ID);
	}
	
	public void dispose(){
		for(Texture t : imgs.values()){
			t.dispose();
		}
	}
	
	public void dispose(int ID){
		imgs.get(ID).dispose();
	}
}
