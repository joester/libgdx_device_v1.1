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
		play.setPosition(renderInfo[0]*renderInfo[2]*0.58f, renderInfo[1]*renderInfo[2]*0.005f);
		help.setPosition(renderInfo[0]*renderInfo[2]*0.74f, renderInfo[1]*renderInfo[2]*0.005f);
		this.bgArt.setSize(state.renderInfo[2] * (100),
				state.renderInfo[2] * (57.2f));
		this.play.setScale(.4f);
		this.help.setScale(.4f);
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
