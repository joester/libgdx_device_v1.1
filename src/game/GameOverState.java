package game;

import sounds.SoundSystem;
import menu.BaseState;
import menu.StateManager;
import game.draw.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dev.Manager;


public class GameOverState extends BaseState{
	
	Sprite gameOverImage, retry, quit;
	GraphicsManager g;
	Manager manager;
	
	public GameOverState(StateManager state, GraphicsManager g, SoundSystem sound, Manager manager){
		super(state, sound, manager);
		this.g = g;
		this.manager = manager;
		manager.loadArtAssets("End");
		assets = manager.getArtAssets("End");
		gameOverImage = new Sprite(assets.get("end_bg"));
		retry = new Sprite(assets.get("end_retry"));
		quit = new Sprite(assets.get("end_quit"));
		retry.setBounds(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.445f, Gdx.graphics.getWidth() * 0.27f, Gdx.graphics.getHeight() * 0.13f);
		quit.setBounds(Gdx.graphics.getWidth() * 0.55f, Gdx.graphics.getHeight() * 0.445f, Gdx.graphics.getWidth() * 0.27f, Gdx.graphics.getHeight() * 0.13f);
		this.gameOverImage.setSize(state.renderInfo[2] * (100),
				state.renderInfo[2] * (57.2f));
	}
	
	public void update(){
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX();
			int y = Gdx.graphics.getHeight() - Gdx.input.getY();
			if(retry.getBoundingRectangle().contains(x,y)){
				manager.unloadArtAssets();
				state.moveToGame();
			}
			if(quit.getBoundingRectangle().contains(x,y)){
				//manager.unloadArtAssets();
				state.moveToSequence("Outro");
			}
		}
	}
	
	public void render(SpriteBatch batch){
		gameOverImage.draw(batch);
		retry.draw(batch);
		quit.draw(batch);
		this.update();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
