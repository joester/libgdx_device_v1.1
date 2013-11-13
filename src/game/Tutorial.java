package game;

import java.util.HashMap;

import sounds.SoundSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;
import game.draw.GraphicsManager;
import menu.BaseState;
import menu.StateManager;

public class Tutorial extends BaseState {
	
	Sprite nav_left, nav_right, nav_exit, scr1, scr2;
	boolean canLeft, canRight;
	int currentScreen;
	GraphicsManager g;
	Sprite scene;
	HashMap<String, Texture> assets;
	
	public Tutorial(StateManager state, GraphicsManager g, SoundSystem sound, Manager manager){
		super(state, sound, manager);
		this.g = g;
		manager.loadArtAssets("Tut");
		assets = manager.getArtAssets("Tut");
		create();
	}

	private void update(){
		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();
		if(Gdx.input.justTouched()){
			if(canLeft){
				if(nav_left.getBoundingRectangle().contains(x, y) && canLeft){
					currentScreen = 0;
					this.scene = currentScreen == 0 ? scr1 : scr2;
					canLeft = false;
					canRight = true;
					return;
				}
			}
			if(canRight){
				if(nav_right.getBoundingRectangle().contains(x, y) && canRight){
					currentScreen = 1;
					this.scene = currentScreen == 0 ? scr1 : scr2;
					sound.playSound(SoundSystem.buttonl);
					canLeft = true;
					canRight = false;
					return;
				}
			}
			if(nav_exit.getBoundingRectangle().contains(x, y)){
				manager.unloadArtAssets();
				state.moveToMenu();
				currentScreen = 0;
				this.scene = currentScreen == 0 ? scr1 : scr2;
				sound.playSound(SoundSystem.buttonh);
				canLeft = false;
				canRight = true;
				
				return;
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		this.scene.setSize(state.renderInfo[2] * (100),	state.renderInfo[2] * (57.2f));
		this.scene.draw(batch);
		if(canLeft){
			nav_left.draw(batch);
		}
		if(canRight){
			nav_right.draw(batch);
		}
		nav_exit.draw(batch);
		this.update();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		scr1 = new Sprite(assets.get("tut_pg1"));
		scr2 = new Sprite(assets.get("tut_pg2"));
		
		this.scene = currentScreen == 0 ? scr1 : scr2;

		nav_left = new Sprite(assets.get("tut_nav_left"));
		nav_right = new Sprite(assets.get("tut_nav_right"));
		nav_exit = new Sprite(assets.get("tut_nav_exit"));
/*		nav_left.setScale(.3f);
		nav_left.setPosition(0, -50);
		nav_right.setScale(.3f);
		nav_right.setPosition(900, -50);
		nav_exit.setScale(.3f);
		nav_exit.setPosition(370, -180);*/
		
		nav_left.setBounds(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getWidth() * 0.11f, Gdx.graphics.getHeight() * 0.19f);
		nav_right.setBounds(Gdx.graphics.getWidth() * 0.81f, Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getWidth() * 0.11f, Gdx.graphics.getHeight() * 0.19f);
		nav_exit.setBounds(Gdx.graphics.getWidth() * 0.415f, -Gdx.graphics.getHeight() * 0.04f, Gdx.graphics.getWidth() * 0.17f, Gdx.graphics.getHeight() * 0.3f);
		
		canLeft = false;
		canRight = true;	
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
