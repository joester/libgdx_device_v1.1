package game.objects.items;

import java.util.ArrayList;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

import game.controls.GameTimer;
import game.objects.AnimatedObject;
import game.objects.GameObject;

public class Vortex extends AnimatedObject{
	
	GameTimer actTimer = new GameTimer(10);
	float scaleBase = 3;

	public Vortex(float posX, float posY, SoundSystem sounds, Texture sprites) {
		super(14, posX, posY, 0, 0, 5, 5, 0, 0,
				false, 2.5f, false, 4, 4, sprites,
				400, 400, sounds);
		
		this.add_animation(0, 0, 5, 30, true);
		this.set_animation(0);

		sounds.playSound(SoundSystem.vortex);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void behavior_collision(GameObject obj){
		if(obj.getID() == 2){
			obj.terminate();
		}
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		super.update(dt, objects);
		if(actTimer.isDone()){
			this.terminate();
		}
		else{
			specMove(objects);
			this.sprite.setScale(scaleBase + (float) Math.sin(actTimer.get_time() * 5) / 3);
			scaleBase -= dt/4;
			actTimer.update_timer(dt);
		}
	}
	
	private void specMove(ArrayList<GameObject> objects){
		for(GameObject obj : objects){
			float x = this.get_positionX() - obj.get_positionX();
			float y = this.get_positionY() - obj.get_positionY();
			float dist = (float)Math.sqrt((x * x) + (y * y));
			if(obj.getID() == 2){
				obj.impact(25 / (dist), Math.atan2(y, x));
			}
		}
	}
	
	@Override
	protected float getZ()
	{
		return 10000;
	}
}
