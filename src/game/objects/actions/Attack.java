package game.objects.actions;

import game.objects.AnimatedObject;
import game.objects.GameObject;
import game.objects.attributes.AttackAttributes;
import game.objects.attributes.MovementAttributes;
import game.objects.enemy.Enemy;

import com.badlogic.gdx.math.Vector2;


public class Attack extends Action
{
	private GameObject target; //The object the attacking object is attacking.
	private MovementAttributes movement; //Determines how the object will move.
	private AttackAttributes attack; //The stats of the incoming attack.
	
	
	/* Constructor */
	/**
	 * Creates a command telling an object to attack another object.
	 * <p>
	 * The ID of attack is 2.
	 * 
	 * @param target the target of the attacker
	 * @param attack the attack stats of the object.
	 */
	public Attack(GameObject target, MovementAttributes movement, AttackAttributes attack)
	{
		super(2);
		this.target = target;
		this.movement = movement;
		this.attack = attack;
	}//END Attack
	
	/* Act */
	@Override
	public void act(GameObject self)
	{
		if(
				((self.get_positionX() >= this.target.get_positionX() - this.attack.range) && (self.get_positionX() <= this.target.get_positionX() + this.attack.range)
				&&
				(self.get_positionY() >= this.target.get_positionY() - this.attack.range) && (self.get_positionY() <= this.target.get_positionY() + this.attack.range))
				||
				this.attack.isAttacking
			)
		{
			//TODO Different things for different things.
			this.attack.isAttacking = true;
			
			AnimatedObject aniself = null;
			if(self instanceof AnimatedObject)
			{
				aniself = (AnimatedObject) self;
			}//fi
			
			if(aniself == null || aniself.get_frame() >= this.attack.attackframe)
			{
				float direction = (float)Math.atan2(this.target.get_positionY()  - self.get_positionY(), this.target.get_positionX()  - self.get_positionX());
				
				if(((self.get_positionX() >= this.target.get_positionX() - this.attack.attackrange_limit) && (self.get_positionX() <= this.target.get_positionX() + this.attack.attackrange_limit)
				&&
				(self.get_positionY() >= this.target.get_positionY() - this.attack.attackrange_limit) && (self.get_positionY() <= this.target.get_positionY() + this.attack.attackrange_limit)))
				{
					target.impact(attack.power, direction);
					this.target.setHp(this.target.getHp() - this.attack.damage);
					if(this.target.getID() == 3){
						((Enemy)target).playHit();
					}
				}//fi
				this.attack.isAttacking = false;	
				this.terminate();
			}//fi
		}//fi
		else
		{
			double direction = Math.atan2(this.target.get_positionY()  - self.get_positionY(), this.target.get_positionX()  - self.get_positionX());
			float xCompMax = (float)(movement.speed * Math.cos(direction));
			float yCompMax = (float)(movement.speed * Math.sin(direction));
			float xComp = (float)(movement.acceleration * Math.cos(direction));
			float yComp = (float)(movement.acceleration * Math.sin(direction));
			
			Vector2 v = self.get_velocity();
			
			if(xCompMax > 0)
			{
				if(v.x + xComp < xCompMax)
				{
					v.x += xComp;
				}//fi
				else
				{
					v.x = xCompMax;
				}//esle
			}//fi
			else
			{
				if(v.x + xComp > xCompMax)
				{
					v.x += xComp;
				}//fi
				else
				{
					v.x = xCompMax;
				}//esle
			}//esle
			
			if(yCompMax > 0)
			{
				if(v.y + yComp < yCompMax)
				{
					v.y += yComp;
				}//fi
				else
				{
					v.y = yCompMax;
				}//esle
			}//fi
			else
			{
				if(v.y + yComp > yCompMax)
				{
					v.y += yComp;
				}//fi
				else
				{
					v.y = yCompMax;
				}//esle
			}//esle
			
			self.set_velocity(v);
		}//esle
	}//END act
}//END class Attack

//EOF
