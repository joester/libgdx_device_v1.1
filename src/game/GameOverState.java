package game;

import sounds.SoundSystem;
import menu.BaseState;
import menu.StateManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;
import device.graphics.Graphics;


public class GameOverState extends BaseState{
	
	Sprite gameOverImage, retry, quit;
	Manager manager;
	
	public GameOverState(StateManager state, SoundSystem sound, Manager manager){
		super(state, sound, manager);
		this.manager = manager;
		manager.loadArtAssets("End");
		assets = manager.getArtAssets("End");
		
		Graphics.clearAll();
		
		gameOverImage = new Sprite(assets.get("end_bg"));
		retry = new Sprite(assets.get("end_retry"));
		quit = new Sprite(assets.get("end_quit"));
		
		Graphics.draw(Graphics.TYPES.BACKGROUND, gameOverImage, 0, 0, 1f, 1f);
		Graphics.draw(Graphics.TYPES.BUTTON, retry, 0.2f, 0.445f, 0.27f, 0.13f);
		Graphics.draw(Graphics.TYPES.BUTTON, quit, 0.55f, 0.445f, 0.27f, 0.13f);
		
		//Write Score
		String score = "Final Score: " + GameStats.getScore();
		float scoreX = ((Gdx.graphics.getWidth() - Graphics.font.getBounds(score).width) / 2) / Gdx.graphics.getWidth();
		Graphics.write(score, scoreX, 1/3f);
		
		//Write Monster
		String monster = "Monsters Slain: " + GameStats.getMonsterCount();
		float monsterX = ((Gdx.graphics.getWidth() - Graphics.font.getBounds(monster).width) / 2) / Gdx.graphics.getWidth();
		Graphics.write(monster, monsterX, 1/3f - ((Graphics.font.getBounds(score).height * 1.5f)/Gdx.graphics.getHeight()));
		
		//Write Time
		String time = "Time Survived: " + GameStats.timeElapsed();
		float timeX = ((Gdx.graphics.getWidth() - Graphics.font.getBounds(time).width) / 2) / Gdx.graphics.getWidth();
		Graphics.write(time, timeX, 1/3f - (((Graphics.font.getBounds(score).height * 1.5f) + (Graphics.font.getBounds(monster).height * 1.5f))/Gdx.graphics.getHeight()));
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
		Graphics.draw(batch);
		this.update();
	}

	public void create() {
		//Graphics.clear();
	}
	
	public void dispose() {
	}
}
