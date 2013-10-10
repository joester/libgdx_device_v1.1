package game;

import sounds.SoundSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import game.draw.GraphicsManager;
import menu.BaseState;
import menu.StateManager;

public class Tutorial extends BaseState {
	
	Sprite nav_left, nav_right, nav_exit;
	boolean canLeft, canRight;
	int currentScreen;
	GraphicsManager g;
	Sprite scene;
	
	public Tutorial(StateManager state, GraphicsManager g, SoundSystem sound){
		super(state, sound);
		this.g = g;
		create();
	}

	private void update(){
		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();
		if(Gdx.input.justTouched()){
			if(canLeft){
				if(nav_left.getBoundingRectangle().contains(x, y) && canLeft){
					currentScreen = 0;
					this.scene = new Sprite(g.ID(50 + currentScreen));
					sound.playSound(SoundSystem.buttonh);
					canLeft = false;
					canRight = true;
					return;
				}
			}
			if(canRight){
				if(nav_right.getBoundingRectangle().contains(x, y) && canRight){
					currentScreen = 1;
					this.scene = new Sprite(g.ID(50 + currentScreen));
					sound.playSound(SoundSystem.buttonl);
					canLeft = true;
					canRight = false;
					return;
				}
			}
			if(nav_exit.getBoundingRectangle().contains(x, y)){
				state.moveToMenu();
				currentScreen = 0;
				this.scene = new Sprite(g.ID(50 + currentScreen));
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
		this.scene.setSize(state.renderInfo[2] * (100),
				state.renderInfo[2] * (57.2f));
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
		this.scene = new Sprite(g.ID(50 + currentScreen));
		nav_left = new Sprite(g.ID(52));
		nav_right = new Sprite(g.ID(53));
		nav_exit = new Sprite(g.ID(54));
		nav_left.setScale(.3f);
		nav_left.setPosition(0, -50);
		nav_right.setScale(.3f);
		nav_right.setPosition(900, -50);
		nav_exit.setScale(.3f);
		nav_exit.setPosition(370, -180);
		canLeft = false;
		canRight = true;
		currentScreen = 0;
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
