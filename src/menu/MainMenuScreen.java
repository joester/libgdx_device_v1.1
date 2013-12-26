package menu;

import java.util.HashMap;

import sounds.SoundSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;
import device.graphics.Graphics;

public class MainMenuScreen extends BaseState {
	
	Sprite bgArt, play, help;
	HashMap<String, Texture> assets = new HashMap<String, Texture>();

	public MainMenuScreen(StateManager state, SoundSystem sound, Manager manager){
		super(state, sound, manager);
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
		
		Graphics.draw(Graphics.TYPES.BACKGROUND, bgArt, 0, 0, 1f, 1f);
		Graphics.draw(Graphics.TYPES.BUTTON, play, 0.61f, 0.06f, 0.15f, 0.25f);
		Graphics.draw(Graphics.TYPES.BUTTON, help, 0.79f, 0.06f, 0.15f, 0.25f);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		update();
		Graphics.draw(batch);
	}
	
	private void update()
	{
		if(Gdx.input.justTouched())
		{
			int x = Gdx.input.getX();
			int y = Gdx.graphics.getHeight() - Gdx.input.getY();
			if(play.getBoundingRectangle().contains(x, y))
			{
				manager.unloadArtAssets();
				state.moveToSequence("Intro");				
			}
			else if(help.getBoundingRectangle().contains(x,y))
			{
				manager.unloadArtAssets();
				state.moveToTutorial();
			}
		}
	}
}
