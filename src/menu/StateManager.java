
package menu;

import game.GameOverState;
import game.GameStats;
import game.Storyboard;
import game.TheDevice;
import game.Tutorial;
import game.draw.GraphicsManager;

import sounds.SoundSystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;

public class StateManager implements ApplicationListener{

	public GraphicsManager graphics = new GraphicsManager();
	public SoundSystem sounds = new SoundSystem();
	int currentState;
	BaseState[] posStates;
	BitmapFont font;
	GameStats stats;
	Manager manager;
	
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
		
		Texture.setEnforcePotImages(false);
		// TODO Auto-generated method stub
		posStates = new BaseState[7];
		
		sounds.init();
		font = new BitmapFont(Gdx.files.internal("data/bearz/bearz.fnt"),Gdx.files.internal("data/bearz/bearz.png"), false);
		
		float width = Gdx.graphics.getWidth();
		this.renderInfo[2] = width/renderInfo[0];
		
		this.manager = new Manager();
		
		this.graphics = new GraphicsManager();

		
		posStates[0] = new MainMenuScreen(this, sounds, renderInfo, manager);
		//posStates[1]=new OptionsScreen(this, sounds, manager);
		
		
		currentState = 0;
		sounds.playMusicLooping(1);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		posStates[currentState].dispose();
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
		posStates[currentState].render(batch);
		if (currentState == 5)
		{
			//Draw Score
			String scoreString = "Final Score: " + stats.getScore();
			font.setScale(Gdx.graphics.getHeight()/650.0f);
			font.draw(batch, scoreString, (Gdx.graphics.getWidth() - font.getBounds(scoreString).width)/2, Gdx.graphics.getHeight()/3f);
			//Draw Score
			String monsterString = "Monsters Slain: " + stats.getMonsterCount();
			font.draw(batch, monsterString, (Gdx.graphics.getWidth() - font.getBounds(monsterString).width)/2, Gdx.graphics.getHeight()/3f - (font.getBounds(scoreString).height * 1.5f));
//			//Draw Level
//			String levelString = "Final Level: " + stats.getLevel();
//			font.draw(batch, levelString, (Gdx.graphics.getWidth() - font.getBounds(levelString).width)/2, (float) (Gdx.graphics.getHeight()/3 - (font.getBounds(scoreString).height * 1.5)));
//			//Draw Exp
//			String XPString = "Final XP: " + stats.getXP();
//			font.draw(batch, XPString, (Gdx.graphics.getWidth() - font.getBounds(levelString).width)/2, (float) (Gdx.graphics.getHeight()/3));
			//Draw Time
			String timeString = "Time Survived: " + stats.timeElapsed();
			font.draw(batch, timeString, (Gdx.graphics.getWidth() - font.getBounds(timeString).width)/2, (float) (Gdx.graphics.getHeight()/3 - (font.getBounds(scoreString).height * 1.5f) - (font.getBounds(monsterString).height * 1.5)));
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

	public void moveToSequence(String seq){
		if(seq.equals("Intro")){
			sounds.playMusicLooping(1);
			posStates[2]=new Storyboard(this, this.graphics, sounds, manager, "Intro", 5);
			currentState = 2;
		}
		else if(seq.equals("Outro")){
			posStates[6]=new Storyboard(this, this.graphics, sounds, manager, "Outro", 3);
			currentState = 6;
		}
	}
	
	public void moveToGame(){
		sounds.stopMusic(1);
		sounds.playMusicLooping(2);
		posStates[3] = new TheDevice(this, this.graphics, sounds, manager);
		posStates[3].create();
		currentState = 3;
	}
	
	public void moveToTutorial(){
		//posStates[currentState).dispose();
		posStates[4] = new Tutorial(this, this.graphics, sounds, manager);
		posStates[4].create();
		currentState = 4;
	}
	
	public void moveToMenu(){
		//posStates[currentState).dispose();
		sounds.playMusicLooping(1);
		posStates[0] = new MainMenuScreen(this, sounds, renderInfo, manager);
		posStates[0].create();
		currentState = 0;
	}
	
	public void endGame(GameStats g){
		posStates[5] = new GameOverState(this, this.graphics, sounds, manager);
		posStates[5].create();
		sounds.stopMusic(2);
		sounds.playSound(SoundSystem.laugh);
		currentState = 5;
		stats = g;
	}
	
}

