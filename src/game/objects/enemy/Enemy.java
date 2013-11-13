package game.objects.enemy;

import game.objects.AnimatedObject;
import game.objects.GameObject;
import game.objects.actions.Attack;
import game.objects.attributes.AttackAttributes;
import game.room.Room;

import java.util.ArrayList;

import sounds.SoundSystem;


import com.badlogic.gdx.graphics.Texture;

public class Enemy extends AnimatedObject
{
	Room room;
	int xpcounter = 0;
	int evolution = 0;
	AttackAttributes attack = new AttackAttributes();
	private float cooldown = 0;
	GameObject device;
	public boolean stunned, slowed;
	byte track;
	boolean isWalking, isAttacking;
	float speedCopy = this.get_speed();
	
	public Enemy(String name, GameObject device, int objectID, float posX, float posY, float mass,
			float friction, float hitWidth, float hitHeight, float hitX,
			float hitY, boolean isSolid, float touchRadius,
			boolean isTouchable, float drawWidth, float drawHeight,
			Texture sprites, int srcWidth, int srcHeight, SoundSystem sounds, Room room) {
		super(name, objectID, posX, posY, mass, friction, hitWidth, hitHeight, hitX, hitY,
				isSolid, touchRadius, isTouchable, drawWidth, drawHeight,
				sprites, srcWidth, srcHeight, sounds);
		
		this.room = room;
		this.device = device;
		/* Stats */
		isWalking = true;
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects)
	{
		if(this.isDying){
			if(!this.animator.get_currentAnimation().equals("death")){
				this.animator.playAnimation("death", 15, false);
				this.isTouchable = false;
				this.isSolid = false;
				playDeath();
			}
			super.update(dt, objects);
		}
		if(this.getHp() <= 0){
			this.isDying = true;
			this.friction = 5000000;
			return;
		}
		
		if(this.attack.isAttacking){
			if(!this.animator.get_currentAnimation().equals("attack")){
				this.animator.playAnimation("attack", 15, false);
			}
			if(this.animator.isDone()){
				//this.action_queue.clear();
			}
			super.update(dt, objects);
			return;
		}
		else{
			this.animator.playAnimation("walk", 10, true);
		}
		
		if(!stunned)
		{
			if(this.action_queue.get_actionID() == 0)
			{
				if(this.cooldown <= 0)
				{
						this.cooldown = 0.6f;
						if(this.action_queue.get_actionID() == 0)
						{
							this.action_queue.clear();
							this.action_queue.add_action(new Attack(device, movement, attack));
						}//fi
				}//fi
				else
				{
					this.cooldown -= dt;
				}//esle
			}//fi
			
		}//fi
		else
		{
			this.unstun();
		}//esle
		
		super.update(dt, objects);
	}//END update
	
	protected void playAttack() {
		// TODO Auto-generated method stub
		
	}

	public void stun()
	{
		this.isSolid = false;
		this.stunned = true;
		this.action_queue.clear();
	}
	
	public void unstun()
	{
		this.isSolid = true;
		this.stunned = false;
	}
	
	@Override
	public void behavior_collision(GameObject collider)
	{
		if(collider.isSolid())
		{
			double direction = Math.atan2(this.get_positionY() - collider.get_positionY(),
					this.get_positionX() - collider.get_positionX());
			float xComp = (float)(3 * collider.get_mass() / this.get_mass() * Math.cos(direction));
			float yComp = (float)(3 * collider.get_mass() / this.get_mass() *  Math.sin(direction));
			this.add_velocity(xComp, yComp);
			
			if(this.isColliding(collider, this.get_position()))
			{
				if(collider.isSolid())
				{
					this.stun();
				}//fi
			}//fi
		}//fi
		
		if(collider.getID() == 2 && !this.isDying)
		{
			collider.worth = 0;
			collider.terminate();
			xpcounter++;
			if(this.xpcounter >= this.evolution)
			{
				this.evolve();
			}
		}
	}//END behavior_collision
	
	/* Sound */
	protected void playDeath()
	{
		//Override
	}
	
	public void playHit()
	{
		
	}
	
	/* Evolution */
	protected void evolve()
	{
		
	}
	
	//Get attack att's for dev tool
	public AttackAttributes getAttack(){
		return attack;
	}
}
