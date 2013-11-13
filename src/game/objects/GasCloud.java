package game.objects;

import java.util.ArrayList;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class GasCloud extends AnimatedObject{
	
	float playerSpeedPlaceholder;
	
	public GasCloud(float posX, float posY, Texture sprites, SoundSystem sounds) {
		super("gasCloud", 18, posX, posY, 1, 1, 0, 0, 0, 0,
				false, 0, false, 10, 10, sprites,
				512, 512, sounds);
		
		this.animator.add_animation("gas", 0, 0, 10000, false,
				0, 1, 2, 3, 4, 3, 4, 3, 4);
		this.animator.set_animation("gas", false);
	}
	
	@Override
	public void behavior_collision(GameObject obj){
		if(obj.getID() == 0){
			((Player) obj).slow();
		}
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		if(this.animator.isDone()){
			this.terminate();
		}
		super.update(dt, objects);
	}
}
