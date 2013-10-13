package game.objects.enemy;

import game.objects.GameObject;
import game.room.Room;
import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class PlantOne extends Enemy{

	public PlantOne(GameObject device, int objectID, float posX, float posY,
			float mass, float friction, float hitWidth, float hitHeight,
			float hitX, float hitY, boolean isSolid, float touchRadius,
			boolean isTouchable, float drawWidth, float drawHeight,
			Texture sprites, int srcWidth, int srcHeight, SoundSystem sounds,
			Room room) {
		super(device, objectID, posX, posY, mass, friction, hitWidth, hitHeight, hitX,
				hitY, isSolid, touchRadius, isTouchable, drawWidth, drawHeight,
				sprites, srcWidth, srcHeight, sounds, room);
		// TODO Auto-generated constructor stub
		
		this.evolution = 1;
		this.attack.damage = 1;
		this.attack.attackframe = 8;
		this.attack.power = 20;
		this.attack.range = 7;
		this.attack.attackrange_limit = 10;
		this.movement.speed = 10;
		this.movement.speedcap = 15;
		this.movement.acceleration = 1;
		this.health.current = 1;
		this.health.max = 1;
		this.worth = 3;
		
		this.add_animation(0, 0, 7, 12, false);
		this.animator.add_animation(0, 1, 16, false, 0,1,2,1,0,3,4,0,2);
		this.animator.add_animation(0, 2, 5, true, 0, 1, 2, 3, 4, 3, 2, 1, 0);
		this.set_animation(2);
	}
	
	@Override
	public void playDeath() {
		sounds.playSound(SoundSystem.monster1_1_death);
	}
	public void playHit()
	{
		playDeath();
	}
	@Override
	public void playAttack(){
		sounds.playSound(SoundSystem.monster1_1_bite);
	}
	
	@Override
	protected void evolve(){
		
	}

}
