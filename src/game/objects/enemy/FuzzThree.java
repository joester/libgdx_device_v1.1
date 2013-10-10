package game.objects.enemy;

import java.util.ArrayList;

import sounds.SoundSystem;

import game.controls.GameTimer;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.actions.Goto;
import game.room.Room;

import com.badlogic.gdx.graphics.Texture;

public class FuzzThree extends Enemy{

	GameTimer hitTimer = new GameTimer(5);
	
	public FuzzThree(GameObject device, float posX, float posY, Texture sprites, SoundSystem sounds, Room room) {
		super(device, 3, posX, posY, 30, 50, 12, 12, 0,
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
		
		this.add_animation(0, 0, 7, 7, false);
		this.animator.add_animation(0, 1, 13, false, 0,1,2,3,4,5,6);
		this.animator.add_animation(0, 2, 5, true,0,1,2,1,0,3,4,3,0);
		
		this.set_animation(2);
	}
	
	private void specAttack(ArrayList<GameObject> objects){
		this.action_queue.clear();		
		for(GameObject obj : objects){
			if(obj != this)
			{
				float xDist = this.get_positionX() - obj.get_positionX();
				float yDist = this.get_positionY() - obj.get_positionY();
				float dist = (float)Math.pow((xDist * xDist) + (yDist * yDist), .5);
				if(dist < 20){
					if(obj.getID() == 0){
						((Player) obj).movement.acceleration = 0;
						((Player) obj).isKnocked = true;
					}
					else if(obj.getID() == 1){
						obj.setHp(obj.getHp() - this.attack.damage);
					}
					obj.impact(-150, Math.atan2(yDist, xDist));
				}
			}
		}
		room.addShock(this);
		sounds.playSound(SoundSystem.monster1_3_roar);
		this.set_animation(2);
		this.isAttacking = false;
		hitTimer.reset_timer();
		
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		
		if(this.getHp() <= 0){
			super.update(dt, objects);
			return;
		}
		if(this.isAttacking){
			this.animator.update(dt);
			if(this.animator.isDone()){
				this.specAttack(objects);
			}
			return;
		}
		else{
			hitTimer.update_timer(dt);
			this.action_queue.clear();
			this.action_queue.add_action(new Goto(device.get_positionX(), device.get_positionY(), movement));
		}
		
		if(this.hitTimer.isDone()){
			for(GameObject obj : objects){
				if(obj.getID() == 0 || obj.getID() == 1){
					float xDist = this.get_positionX() - obj.get_positionX();
					float yDist = this.get_positionY() - obj.get_positionY();
					float dist = (float)Math.pow((xDist * xDist) + (yDist * yDist), .5);
					if(dist < 15){
						if(this.animator.get_currentAnimation() != 1){
							this.set_animation(1);
							isAttacking = true;
							return;
						}	
					}
				}
			}
		}
		
		this.hitTimer.update_timer(dt);
		
		super.update(dt, objects);
	}
	
	public void playSound(){
		sounds.playSound(SoundSystem.monster1_3_attack);
	}
	
	public void playHit(){
		sounds.playSound(SoundSystem.monster1_3_damage);
	}
	
	@Override
	public void playDeath() {
		sounds.playSound(SoundSystem.monster1_3_death);
	}
}
