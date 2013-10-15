package game.objects.enemy;

import game.controls.GameTimer;
import game.objects.GameObject;
import game.objects.Player;
import game.room.Room;

import java.util.ArrayList;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class PlantTwo extends Enemy{
	
	Texture monster3;
	public PlantTwo(GameObject device, float posX, float posY, Texture sprites, Texture monster3, SoundSystem sounds, Room room) {
		super(device, 3, posX, posY, 2, 5, 6, 6, 0,
				0, true, 10, true, 12,12,//9, 9,
				sprites, 256, 256, sounds, room);
		// TODO Auto-generated constructor stub
		
		this.monster3 = monster3;
		this.device = device;
		this.evolution = 2;
		this.attack.attackframe = 1;
		this.drawOffsetY = 1.25f;
		this.attack.damage = 2;
		this.attack.power = 20;
		this.attack.range = 7;
		this.attack.attackrange_limit = 11;
		this.movement.speed = 15;
		this.movement.speedcap = 40;
		this.movement.acceleration = 1;
		this.health.current = 2;
		this.health.max = 2;
		this.worth = 7;
		
		this.animator.add_animation(0, 0, 5, true, 0,1,2,3,4,3,2,1,0);
		this.animator.add_animation(0, 0, 5, true, 0,1,2,3,4,3,2,1,0);
		this.animator.add_animation(0, 0, 5, true, 0,1,2,3,4,3,2,1,0);

		this.set_animation(0);
	}
	
	public void playSound(){
		sounds.playSound(SoundSystem.monster1_2_bite);
	}
	
	public void playHit(){
		sounds.playSound(SoundSystem.monster1_2_damage);
	}
	
	@Override
	public void playDeath() {
		sounds.playSound(SoundSystem.monster2_2_death);
	}
	
	@Override
	protected void evolve(){
		/*
		this.worth = 0;
		this.terminate();
		GameObject monster = new PlantThree(device, this.get_positionX(), this.get_positionY(), monster3, sounds, room);
		room.spawn_object(monster);
		monster.levelUp = 3;
		sounds.playSound(SoundSystem.mlevel);
		*/
	}
}
