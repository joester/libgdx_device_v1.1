package game.objects.enemy;

import java.util.ArrayList;

import game.objects.GameObject;
import game.objects.actions.Attack;
import game.room.Room;
import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class PlantOne extends Enemy{

	Texture monster2;
	Texture monster3;
	float cooldown = 0.6f;
	
	public PlantOne(GameObject device, float posX, float posY, Texture sprites, Texture monster2, Texture monster3, SoundSystem sounds, Room room) {
		super("plant1", device, 3, posX, posY, 12, 12, 6, 6, 0,
				0, true, 10, true, 12, 12,
				sprites, 256, 256, sounds, room);
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
		this.monster2 = monster2;
		this.monster3 = monster3;
		
		this.animator.add_animation("death", 0, 0, 5, false, 0, 1, 2, 3, 2, 1, 0);
		this.animator.add_animation("attack", 0, 0, 5, true, 0, 1, 2, 3, 2, 1, 0);
		this.animator.add_animation("walk", 0, 0, 5, true, 0, 1, 2, 3, 2, 1, 0);
		this.set_animation("walk", true);
	}
	
	@Override
	public void playDeath() {
		sounds.playSound(SoundSystem.monster2_1_death);
	}
	public void playHit()
	{
		sounds.playSound(SoundSystem.monster1_2_damage);
	}
	@Override
	public void playAttack(){
		sounds.playSound(SoundSystem.monster1_1_bite);
	}
	
	@Override
	protected void evolve(){
		this.worth = 0;
		this.terminate();
		GameObject monster = new PlantTwo(device, this.get_positionX(), this.get_positionY(), monster2, monster3, sounds, room);
		monster.levelUp = 3;
		room.spawn_object(monster);
		sounds.playSound(SoundSystem.mlevel);
	}

}
