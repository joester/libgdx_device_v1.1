package game.objects.enemy;

import sounds.SoundSystem;
import game.objects.GameObject;
import game.room.Room;

import com.badlogic.gdx.graphics.Texture;

public class FuzzOne extends Enemy{
	
	Texture monster2;
	Texture monster3;
	public FuzzOne(GameObject device, float posX, float posY, Texture sprites, Texture monster2, Texture monster3, SoundSystem sounds, Room room) {
		super(device, 3, posX, posY, 12, 12, 6, 6, 0,
				0, true, 10, true, 12, 12,
				sprites, 128, 128, sounds, room);
		// TODO Auto-generated constructor stub
		
		this.monster2 = monster2;
		this.monster3 = monster3;
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
		this.worth = 0;
		this.terminate();
		GameObject monster = new FuzzTwo(device, this.get_positionX(), this.get_positionY(), monster2, monster3, sounds, room);
		monster.levelUp = 3;
		room.spawn_object(monster);
		sounds.playSound(SoundSystem.mlevel);
	}
}
