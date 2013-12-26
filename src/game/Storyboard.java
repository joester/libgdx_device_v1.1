package game;

import java.util.HashMap;

import sounds.SoundSystem;
import menu.BaseState;
import menu.StateManager;
import game.draw.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;
import device.graphics.Graphics;

public class Storyboard extends BaseState{
	int currentScene, maxScene;
	Sprite scene;
	GraphicsManager graphics;
	boolean isDone;
	Manager manager;
	HashMap<String, Texture> assets;
	String cinematicType;
	
	public Storyboard(StateManager state, GraphicsManager g, SoundSystem sound, Manager manager, String cinematicType, int numScenes){
		super(state, sound, manager);
		this.currentScene = 0;
		this.graphics = g;
		this.manager = manager;
		if(cinematicType.equals("Outro")){
			manager.loadArtAssets("Outro");
			assets = manager.getArtAssets("Outro");
		}
		else if(cinematicType.equals("Intro")){
			manager.loadArtAssets("Intro");
			assets = manager.getArtAssets("Intro");
		}
		this.cinematicType = cinematicType;
		maxScene=numScenes;
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
		if(currentScene == maxScene){		
			if(cinematicType == "Intro"){
				manager.unloadArtAssets();
				state.moveToGame();
			}
			else if(cinematicType == "Outro"){
				manager.unloadArtAssets();
				state.moveToMenu();
			}
			
			return;
		}
		scene = new Sprite(assets.get("sc" + Integer.toString(currentScene)));
		this.dispose();
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		this.scene.setSize(state.renderInfo[2] * (100),
				state.renderInfo[2] * (57.2f));
		this.scene.draw(batch);
		Graphics.draw(Graphics.TYPES.BACKGROUND, scene, 0, 0, 1f, 1f);
		this.update();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		this.scene = new Sprite(assets.get("sc" + Integer.toString(currentScene)));
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
