package game.objects;

import java.util.ArrayList;

import game.controls.GameTimer;
import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class ShockWave extends AnimatedObject{

	GameTimer displayTime = new GameTimer(1);
	float currentScale = 1;
	
	public ShockWave(float posX, float posY, Texture sprites, SoundSystem sounds) {
		super(18, posX, posY, 1, 1, 0, 0, 0, 0,
				false, 0, false, 10, 10, sprites,
				512, 512, sounds);
		
		this.add_animation(0, 0, 3, 3, false);
		this.set_animation(0);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		displayTime.update_timer(dt);
		if(!displayTime.isDone())
		{
			this.drawWidth += dt * 100;
			this.drawHeight += dt * 100;
			this.sprite.setColor(1, 1, 1, this.currentScale -= dt);
		}
		else
			this.terminate();
	}
	
	@Override
	protected float getZ()
	{
		return 10000;
	}
	
}
