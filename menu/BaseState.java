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
