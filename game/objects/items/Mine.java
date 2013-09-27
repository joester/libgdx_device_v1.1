package game.objects.items;

import java.util.ArrayList;

import sounds.SoundSystem;

import game.GameStats;
import game.controls.GameTimer;
import game.objects.AnimatedObject;
import game.objects.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Mine extends AnimatedObject{
	
	GameStats stats;
	GameTimer timeToExpire = new GameTimer(10);
	boolean isActive;
	
	public Mine(float posX, float posY, GameStats stats, SoundSystem sounds, Texture sprites){
		super(99, posX, posY, 1, 90, 5, 5, 0, 0,
				false, 5, false, 5, 5,
				sprites, 200, 200, sounds);
		this.stats = stats;
		this.movement.speedcap = 100;
		this.isActive = false;
		this.add_animation(0, 0, 4, 8, true);
		this.set_animation(0);

	}
	
	@Override
	public void behavior_collision(GameObject obj){
		if(obj.getID() == 0){
			if(stats.addMine()){
				this.terminate();
			}
		}	
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		super.update(dt, objects);
		timeToExpire.update_timer(dt);
		if(timeToExpire.isDone() && !isActive){
			this.terminate();
		}		
	}	
}
