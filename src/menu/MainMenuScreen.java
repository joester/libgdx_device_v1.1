package menu;

import sounds.SoundSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends BaseState {
	
	Sprite bgArt, play, help;
	float[] renderInfo;

	public MainMenuScreen(StateManager state, SoundSystem sound, float[] renderInfo){
		super(state, sound);
		this.renderInfo = renderInfo;
		create();
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		play = new Sprite(state.graphics.ID(200));
		bgArt = new Sprite(state.graphics.ID(201));		
		help = new Sprite(state.graphics.ID(202));
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
		bgArt.getTexture().dispose();
		play.getTexture().dispose();
		help.getTexture().dispose();
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
				state.moveToSequence();
			}
			else if(help.getBoundingRectangle().contains(x,y)){
				state.moveToTutorial();
			}
		}
	}
}
