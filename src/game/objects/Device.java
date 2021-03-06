package game.objects;

import game.controls.GameTimer;
import game.draw.Animator;
import game.room.Room;

import java.util.ArrayList;

import sounds.SoundSystem;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Device extends AnimatedObject {	
	private GameTimer timer;
	private GameTimer timer_2;
	private int timer_count = 3;
	private Texture exp_sprites;
	private Room room;
	private float bob = 1;
	private float bobRate = 2;
	private float currentBob;
	
	/* Spawn */
	private boolean isSpawning = false;
	private Sprite spawn;
	private Animator spawn_animator;
	
	/* Hit */
	private Sprite hit;
	private float hitLength;
	private float hpMemory;
	private float inv = -1;
	
	//Handy Dandy tool variables
	private boolean canSpawnXP = true;
	
	public Device(float posX, float posY, Texture sprites, Texture spawn, Room room, Texture exp_sprites, Texture hit, SoundSystem sounds)
	 {
		super(
				"device", //animator name
				1, //ID
				posX, posY, //Position
				1, //Mass
				200, //Friction
				5,5, //Hit Height and Width
				0, 0, //Hit x and y offset
				true, //Solid or not
				10, //Touch Radius
				true, //Touchable
				12, 12, //Draw width and height
				sprites, //Spritesheet
				200, 200, //srcwidth and height
				sounds
				);
		
		this.movement.speedcap = 100;
		this.exp_sprites = exp_sprites;
		this.screenBound = true;
		this.room = room;
		this.timer = new GameTimer(0.3f);
		this.timer_2 = new GameTimer(5f);
		this.health.max = 10;
		this.health.current = 10;
		
		this.animator.add_animation("device_float", 0, 0, 5, 24, true);
		this.animator.set_animation("device_float", true);
		
		/* Spawn */
		this.spawn = new Sprite(spawn);
		this.spawn_animator = new Animator("device", this.spawn, 200, 200);
		this.spawn_animator.add_animation("device_spawn", 0, 0, 3, 8, false);
		this.spawn_animator.set_animation("device_spawn", true);
		
		/* Hit */
		this.hit = new Sprite(hit);
		this.hitLength = 0;
		this.hpMemory = this.health.current;
	}//END Device
	
	@Override
	public void behavior_collision(GameObject collider)
	{
		if(collider.isSolid)
		{
			if(collider.getID() == 0)
			{
				double direction = Math.atan2(this.get_positionY() - collider.get_positionY(),
						this.get_positionX() - collider.get_positionX());
				float xComp = (float)(10 * collider.get_mass() / this.get_mass() * Math.cos(direction));
				float yComp = (float)(10 * collider.get_mass() / this.get_mass() *  Math.sin(direction));
				this.add_velocity(xComp, yComp);
			}//fi
			else
			{
				double direction = Math.atan2(this.get_positionY() - collider.get_positionY(),
						this.get_positionX() - collider.get_positionX());
				float xComp = (float)(1 * collider.get_mass() / this.get_mass() * Math.cos(direction));
				float yComp = (float)(1 * collider.get_mass() / this.get_mass() *  Math.sin(direction));
				this.add_velocity(xComp, yComp);
			}
		}//fi
	}//END behavior_collision
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects)
	{
		if(this.inv > 0)
		{
			this.setHp(this.inv);
		}//fi
		
		if(this.health.current <= 0)
		{
			this.terminate();
		}//fi
		
		this.timer_2.update_timer(dt);
		if(this.timer_2.isDone() && canSpawnXP)
		{
			float dir = (float)(360 * Math.random());
			float dst = 40 + (float)(30 * Math.random());
			
			XP exp1 = new XP(this.get_positionX(), this.get_positionY(), this.exp_sprites, dst, dir, this.sounds);
			
			this.timer.update_timer(dt);
			if(this.timer.isDone())
			{
				sounds.playSound(SoundSystem.spawn);
				this.timer_count = this.timer_count - 1;
				this.room.spawn_object(exp1);
				this.timer.reset_timer();
				this.isSpawning = true;
				this.spawn_animator.set_animation("device_spawn", false);
			}//fi
			
			if (this.timer_count <= 0)
			{
				this.timer_2.reset_timer();
				this.timer_count = 3;
			}//fi
		}//fi
		
		if(this.isSpawning)
		{
			this.spawn_animator.update(dt);
		}//fi
		
		if(this.spawn_animator.isDone())
		{
			this.isSpawning = false;
		}//fi
		
		this.currentBob += this.bobRate * dt;
		this.bob = (float)(Math.sin(currentBob) * 0.5);
		
		this.drawOffsetY = 1 + this.bob;
		
		//Check if hit
		if (this.health.current != this.hpMemory)
		{
			this.hpMemory = this.health.current;
			this.hitLength = 20;
		}
		
		super.update(dt, objects);
	}
	
	/* Render */
	@Override
	public void render(SpriteBatch spritebatch, float[] renderInfo)
	{
		super.render(spritebatch, renderInfo);
		if(this.isSpawning)
		{
			this.spawn.setOrigin(renderInfo[2] * (this.drawWidth/2),
					renderInfo[2] * (this.drawHeight/2));
			this.spawn.setSize(renderInfo[2] * (this.drawWidth),
					renderInfo[2] * (this.drawHeight));
			this.spawn.setPosition(renderInfo[2] * (this.position.x - this.drawWidth/2 + this.drawOffsetX),
					renderInfo[2] * (this.position.y - this.drawHeight/2  + this.drawOffsetY));
			this.spawn.draw(spritebatch);
		}//fi
		if(this.hitLength > 0)
		{
			sounds.playSound(SoundSystem.hit);
			this.hit.setOrigin(renderInfo[2] * (this.drawWidth/2),
					renderInfo[2] * (this.drawHeight/2));
			this.hit.setSize(renderInfo[2] * (this.drawWidth),
					renderInfo[2] * (this.drawHeight));
			this.hit.setPosition(renderInfo[2] * (this.position.x - this.drawWidth/2 + this.drawOffsetX),
					renderInfo[2] * (this.position.y - this.drawHeight/2  + this.drawOffsetY));
			this.hit.draw(spritebatch);
			this.hitLength -= 1;
		}
	}//END render

		/* Other */
		public void inv()
		{
			this.inv = this.get_healthAttribute().current;
		}//END inv
		
		public void disInv()
		{
			this.inv = -1;
		}
		
		public void toggleSpawnXP(){
			canSpawnXP = !canSpawnXP;
		}
		
	
}
