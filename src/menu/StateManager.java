package menu;

import game.GameOverState;
import game.GameStats;
import game.Storyboard;
import game.TheDevice;
import game.Tutorial;
import game.draw.GraphicsManager;

import java.util.ArrayList;

import sounds.SoundSystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StateManager implements ApplicationListener{

	public GraphicsManager graphics = new GraphicsManager();
	public SoundSystem sounds = new SoundSystem();
	int currentState;
	ArrayList<BaseState> posStates;
	BitmapFont font;
	GameStats stats;
	
	public float[] renderInfo = 
		{
		100,				//Game Width
		320 * 100 / 557, 	//Game Height
		0,					//Scalar
		0,					//Camera X
		0,					//Camera Y
		};
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		posStates = new ArrayList<BaseState>();
		
		sounds.init();
		font = new BitmapFont(Gdx.files.internal("data/bearz/bearz.fnt"),Gdx.files.internal("data/bearz/bearz.png"), false);
		
		float width = Gdx.graphics.getWidth();
		this.renderInfo[2] = width/renderInfo[0];
		
		
		
		this.graphics = new GraphicsManager();
		this.graphics.load
			(
					0,
					"data/objects/hero.png",
					"data/objects/device.png",
					"data/exp.png",
					"data/monsters/Fuzzies1.png",
					"data/objects/device_spawn.png",
					"data/monsters/Fuzzies2.png",
					"data/monsters/Fuzzies3.png",
					"data/objects/device_hit.png",
					"data/UI/indication_sheet.png",
					"data/objects/mineDrop.png",
					"data/mine.png",
					"data/vortIcon.png",
					"data/vortex.png",
					"data/monsters/dust.png",
					"data/monsters/shock_wave.png"
			);
		this.graphics.load(100,"data/backgrounds/grass.png", "data/DeathRing.png");
		
		this.graphics.load(200,"data/UI/play.png", "data/backgrounds/TitleScreen.png", "data/UI/help.png");
		
		this.graphics.load(
				
				50,
				"data/UI/tut1.png",
				"data/UI/tut2.png",
				"data/UI/nav_left.png",
				"data/UI/nav_right.png",
				"data/UI/nav_exit.png"
				);
		
		this.graphics.load(
				
				1000,
				"data/UI/UIBase.png",
				"data/UI/XPFill.png",
				"data/UI/XPEmpty.png",
				"data/UI/XPGlow.png",
				"data/UI/Pause.png",
				"data/UI/warn.png",
				"data/UI/bomb.png",
				"data/UI/BombCount.png",
				"data/UI/mine.png",
				"data/UI/count.png",
				"data/UI/vortex.png"
		);
		
		this.graphics.load(
				
				1050,
				"data/backgrounds/gameover.png",
				"data/UI/retry.png",
				"data/UI/quitter.png"
				);
		
		posStates.add(new MainMenuScreen(this, sounds, renderInfo));
		posStates.add(new OptionsScreen(this, sounds));
		posStates.add(new Storyboard(this, this.graphics, sounds));
		posStates.add(new TheDevice(this, this.graphics, sounds));
		posStates.add(new Tutorial(this, this.graphics, sounds));
		posStates.add(new GameOverState(this, this.graphics, sounds));
		
		currentState = 0;
		sounds.playMusicLooping(1);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		posStates.get(currentState).dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		posStates.get(currentState).render(batch);
		if (currentState == 5)
		{
			//Draw Score
			String scoreString = "Final Score: " + stats.getScore();
			font.draw(batch, scoreString, (Gdx.graphics.getWidth() - font.getBounds(scoreString).width)/2, Gdx.graphics.getHeight()/3);
			//Draw Level
			String levelString = "Final Level: " + stats.getLevel();
			font.draw(batch, levelString, (Gdx.graphics.getWidth() - font.getBounds(levelString).width)/2, (float) (Gdx.graphics.getHeight()/3 - (font.getBounds(scoreString).height * 1.5)));
			//Draw Time
			String timeString = "Time Survived: " + stats.timeElapsed();
			font.draw(batch, timeString, (Gdx.graphics.getWidth() - font.getBounds(timeString).width)/2, (float) (Gdx.graphics.getHeight()/3 - (font.getBounds(scoreString).height * 1.5) - (font.getBounds(levelString).height * 1.5)));
		}
		batch.end();
		batch.dispose();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	public void moveToSequence(){
		if(currentState != 2){
			sounds.playMusicLooping(1);
			currentState = 2;
		}
		else{
			moveToGame();
		}
	}
	
	public void moveToGame(){
		sounds.stopMusic(1);
		sounds.playMusicLooping(2);
		posStates.get(3).create();
		currentState = 3;
	}
	
	public void moveToTutorial(){
		//posStates.get(currentState).dispose();
		posStates.get(4).create();
		currentState = 4;
	}
	
	public void moveToMenu(){
		//posStates.get(currentState).dispose();
		sounds.playMusicLooping(1);
		posStates.get(0).create();
		currentState = 0;
	}
	
	public void endGame(GameStats g){
		posStates.get(5).create();
		sounds.stopMusic(2);
		sounds.playSound(SoundSystem.laugh);
		currentState = 5;
		stats = g;
	}
	
}
