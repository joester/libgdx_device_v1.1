package menu;

import java.util.HashMap;

import sounds.SoundSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;

public class MainMenuScreen extends BaseState {
	
	Sprite bgArt, play, help;
	float[] renderInfo;
	HashMap<String, Texture> assets = new HashMap<String, Texture>();

	public MainMenuScreen(StateManager state, SoundSystem sound, float[] renderInfo, Manager manager){
		super(state, sound, manager);
		this.renderInfo = renderInfo;
		manager.loadArtAssets("Main");
		assets = manager.getArtAssets("Main");
		create();
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		play = new Sprite(assets.get("main_play"));
		bgArt = new Sprite(assets.get("main_bg"));		
		help = new Sprite(assets.get("main_help"));
		this.bgArt.setSize(state.renderInfo[2] * (100),
				state.renderInfo[2] * (57.2f));
		this.play.setBounds(Gdx.graphics.getWidth() * 0.61f, Gdx.graphics.getHeight() * 0.06f, Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.25f);
		this.help.setBounds(Gdx.graphics.getWidth() * 0.79f, Gdx.graphics.getHeight() * 0.06f, Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.25f);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		this.bgArt.draw(batch);
		this.play.draw(batch);
		this.help.draw(batch);
		update();
	}
	
	private void update(){
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX();
			int y = Gdx.graphics.getHeight() - Gdx.input.getY();
			if(play.getBoundingRectangle().contains(x, y)){
				manager.unloadArtAssets();
				state.moveToSequence("Intro");				
			}
			else if(help.getBoundingRectangle().contains(x,y)){
				manager.unloadArtAssets();
				state.moveToTutorial();
				
			}
		}
	}
}
