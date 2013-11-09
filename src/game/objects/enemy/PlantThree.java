package game.objects.enemy;

import game.objects.GameObject;
import game.room.Room;
import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class PlantThree extends Enemy{
	public PlantThree(GameObject device, float posX, float posY, Texture sprites, SoundSystem sounds, Room room) {
		super("plant3",device, 3, posX, posY, 30, 50, 12, 12, 0,
				0, true, 15, true, 12, 12,
				sprites, 128, 128, sounds, room);
		// TODO Auto-generated constructor stub
		
		this.device = device;
		
		this.attack.damage = 2;
		this.attack.power = 20;
		this.attack.attackframe = 6;
		this.attack.range = 10;
		this.attack.attackrange_limit = 14;
		this.movement.speed = 10;
		this.movement.speedcap = 35;
		this.movement.acceleration = 1;
		this.health.current = 6;
		this.health.max = 6;
		this.worth = 13;
		
		this.drawOffsetY = 2;
		
		this.add_animation("death", 0, 0, 7, 7, false);
		this.animator.add_animation("attack", 0, 1, 13, false, 0,1,2,3,4,5,6);
		this.animator.add_animation("walk", 0, 2, 5, true,0,1,2,1,0,3,4,3,0);
		
		this.set_animation("walk", true);
	}
	
	public void playSound(){
		sounds.playSound(SoundSystem.monster1_3_attack);
	}
	
	public void playHit(){
		sounds.playSound(SoundSystem.monster1_3_damage);
	}
	
	@Override
	public void playDeath() {
		sounds.playSound(SoundSystem.monster2_3_death);
	}
	
}
