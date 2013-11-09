package game.objects.items;

import java.util.ArrayList;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

import game.GameStats;
import game.controls.GameTimer;
import game.objects.AnimatedObject;
import game.objects.GameObject;

public class VortexIcon extends AnimatedObject{

	GameStats stats;
	GameTimer existTimer = new GameTimer(5);
	
	public VortexIcon(float posX, float posY, GameStats stats, SoundSystem sounds, Texture sprites) {
		super("vortexicon",14, posX, posY, 0, 0, 5, 5, 0, 0,
				false, 2.5f, false, 5, 5, sprites,
				200, 200, sounds);
		
		this.stats = stats;
		this.add_animation("vortex_item",0, 0, 4, 8, true);
		this.set_animation("vortex_item", true);
	}
	
	@Override
	public void behavior_collision(GameObject obj){
		if(obj.getID() == 0){
			if(stats.addVortex()){
				{
					this.terminate();
				}
			}
		}
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		if(existTimer.isDone()){
			this.terminate();
		}
		existTimer.update_timer(dt);
		super.update(dt, objects);
	}
}
