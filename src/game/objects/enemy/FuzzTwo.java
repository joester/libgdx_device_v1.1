package game.objects.enemy;

import java.util.ArrayList;

import sounds.SoundSystem;

import game.controls.GameTimer;
import game.objects.GameObject;
import game.objects.Player;
import game.room.Room;

import com.badlogic.gdx.graphics.Texture;

public class FuzzTwo extends Enemy{

	GameTimer timer = new GameTimer(5);
	GameTimer prepTime = new GameTimer(1);
	GameTimer runTime = new GameTimer(0.5f);
	
	float xRun, yRun;
	boolean isPrepping, isRunning;
	
	Texture monster3;
	public FuzzTwo(GameObject device, float posX, float posY, Texture sprites, Texture monster3, SoundSystem sounds, Room room) {
		super(device, 3, posX, posY, 2, 5, 6, 6, 0,
				0, true, 10, true, 9, 9,
				sprites, 128, 128, sounds, room);
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
		
		this.add_animation(0, 0, 7, 12, false);
		this.add_animation(0, 1, 2, 5, true);
		this.animator.add_animation(0, 2, 5, true, 0,1,0,2);
		this.add_animation(0, 3, 3, 5, true);

		this.set_animation(2);
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		if(this.getHp() <= 0){
			super.update(dt, objects);
			return;
		}
		if((!isPrepping && !isRunning) && this.animator.get_currentAnimation() != 2){
			this.animator.set_animation(2);
			return;
		}
		if(timer.isDone()){
			this.action_queue.clear();
			if(this.animator.get_currentAnimation() != 3){
				this.animator.set_animation(3);
				isPrepping = true;
			}
			if(isPrepping){
				prepTime.update_timer(dt);
				if(prepTime.isDone()){
					for(GameObject obj : objects){
						if(obj.getID() == 0){
							charge(obj);
							break;
						}
					}
					isRunning = true;
					sounds.playSound(SoundSystem.monster1_2_charge);
					isPrepping = false;
					prepTime.reset_timer();
					this.animator.set_animation(1);
					timer.reset_timer();
				}
				else{
					this.animator.update(dt);
				}				
			}
			return;				
		}
		if(isRunning){
			
			runTime.update_timer(dt);
			this.action_queue.clear();
			
			this.set_velocity(xRun,yRun);
			
			if(runTime.isDone()){
				clearAction();
				this.isRunning = false;
			}
			
		}
		timer.update_timer(dt);
		super.update(dt, objects);
	}	
	
	@Override
	public void behavior_collision(GameObject obj){
		super.behavior_collision(obj);
		if(obj.getID() == 0 && isRunning){
			((Player) obj).stun(this);
			sounds.playSound(SoundSystem.monster1_2_collide);
			clearAction();
		}
	}
	
	private void clearAction(){
		this.movement.speed = 15;
		this.action_queue.clear();
		isRunning = false;
		this.mass = 2;
		runTime.reset_timer();
		this.animator.set_animation(2);
	}
	
	private void charge(GameObject obj){
		this.mass = 7;
		this.action_queue.clear();
		double angle = (Math.atan2(obj.get_positionY() - this.get_positionY(), obj.get_positionX() - this.get_positionX()));
		this.xRun = (float)(40 * Math.cos(angle));
		this.yRun = (float)(40 * Math.sin(angle));
		this.set_velocity(xRun,yRun);
		
		//(360 * (float)Math.atan2(this.get_positionY() - obj.get_positionY(), this.get_positionX() - obj.get_positionX()))
	}
	
	public void playSound(){
		sounds.playSound(SoundSystem.monster1_2_bite);
	}
	
	public void playHit(){
		sounds.playSound(SoundSystem.monster1_2_damage);
	}
	
	@Override
	public void playDeath() {
		sounds.playSound(SoundSystem.monster1_2_death);
	}
	
	@Override
	protected void evolve(){
		this.worth = 0;
		this.terminate();
		GameObject monster = new FuzzThree(device, this.get_positionX(), this.get_positionY(), monster3, sounds, room);
		room.spawn_object(monster);
		monster.levelUp = 3;
		sounds.playSound(SoundSystem.mlevel);
	}
}
