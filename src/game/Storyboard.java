package game;

import sounds.SoundSystem;
import menu.BaseState;
import menu.StateManager;
import game.draw.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Storyboard extends BaseState{
	int currentScene;
	Sprite scene;
	GraphicsManager graphics;
	boolean isDone;
	
	public Storyboard(StateManager state, GraphicsManager g, SoundSystem sound){
		super(state, sound);
		this.currentScene = 0;
		this.graphics = g;
		this.create();
	}
	
	private void update(){
		if(isDone){
			this.create();
			isDone = false;
		}
		if(Gdx.input.justTouched()){
			advanceScene();
		}
	}
	
	private void advanceScene(){
		currentScene ++;
		if(currentScene == 5){
			state.moveToSequence();
			scene.getTexture().dispose();
		}
		if(currentScene == 8){
			state.moveToMenu();
			scene.getTexture().dispose();
			currentScene = 0;
			isDone = true;
			return;
		}
		scene = new Sprite(graphics.ID(2000 + currentScene));
		this.dispose();
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		this.scene.setSize(state.renderInfo[2] * (100),
				state.renderInfo[2] * (57.2f));
		this.scene.draw(batch);
		this.update();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		this.graphics.load(2000,
				"data/scenes/sketch_scene1-2.png",
				"data/scenes/sketch_scene2.png",
				"data/scenes/sketch_scene3-2.png",
				"data/scenes/sketch_scene4-2.png",
				"data/scenes/sketch_scene5.png",
				"data/scenes/sketch_scene6-2.png",
				"data/scenes/sketch_scene7.png",
				"data/scenes/sketch_scene8.png"				
				);
		this.scene = new Sprite(graphics.ID(2000 + currentScene));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		graphics.dispose(2000 + currentScene - 1);
	}
}
