<<<<<<< HEAD
package menu;

import java.util.HashMap;

import sounds.SoundSystem;
import game.controls.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;

public abstract class BaseState {
	
	protected SoundSystem sound;
	protected StateManager state;
	Controller controller;
	protected Manager manager;
	protected HashMap<String, Texture> assets;
	
	public BaseState(StateManager state, SoundSystem sound, Manager manager){
		this.state = state;
		this.sound = sound;
		this.manager = manager;
	}
	
	public abstract void render(SpriteBatch batch);
	public abstract void create();
	public abstract void dispose();
	
}
=======
package menu;

import sounds.SoundSystem;
import game.controls.Controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseState {
	
	protected SoundSystem sound;
	protected StateManager state;
	Controller controller;
	
	public BaseState(StateManager state, SoundSystem sound){
		this.state = state;
		this.sound = sound;
		
	}
	
	public abstract void render(SpriteBatch batch);
	public abstract void create();
	public abstract void dispose();
	
}
>>>>>>> branch 'master' of https://github.com/EyeWumbo/libgdx_device_v1.1.git
