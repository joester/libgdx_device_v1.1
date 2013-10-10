package menu;

import java.io.IOException;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionsScreen extends BaseState{
	
	Button button1 = null;
	Button button2 = null;
	Sprite spr;
	StateManager state;
	static OptionsContainer opCon = new OptionsContainer();
	
	public OptionsScreen(StateManager state, SoundSystem sound){
		super(state, sound);
		create();		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		spr = new Sprite(state.graphics.ID(203));
		batch.draw(spr, 0, 0);
		if(button1.update()){
			opCon.optionsBool.put("hai", !opCon.optionsBool.get("hai"));
			try {
				opCon.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(opCon.optionsBool.get("hai"));
		}
		if(button2.update()){
			
		}
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
