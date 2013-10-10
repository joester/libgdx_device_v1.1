package game;

import sounds.SoundSystem;
import menu.BaseState;
import menu.StateManager;
import game.draw.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class GameOverState extends BaseState{
	
	Sprite gameOverImage, retry, quit;
	GraphicsManager g;
	
	
	public GameOverState(StateManager state, GraphicsManager g, SoundSystem sound){
		super(state, sound);
		this.g = g;
		gameOverImage = new Sprite(g.ID(1050));
		retry = new Sprite(g.ID(1051));
		quit = new Sprite(g.ID(1052));
		retry.setPosition((Gdx.graphics.getWidth() - retry.getWidth())/3, (Gdx.graphics.getHeight() - retry.getHeight())/2);
		quit.setPosition((Gdx.graphics.getWidth() - retry.getWidth())*2/3, (Gdx.graphics.getHeight() - retry.getHeight())/2);
		this.gameOverImage.setSize(state.renderInfo[2] * (100),
				state.renderInfo[2] * (57.2f));
	}
	
	public void update(){
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX();
			int y = Gdx.graphics.getHeight() - Gdx.input.getY();
			if(retry.getBoundingRectangle().contains(x,y)){
				state.moveToGame();
			}
			if(quit.getBoundingRectangle().contains(x,y)){
				state.moveToSequence();
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
