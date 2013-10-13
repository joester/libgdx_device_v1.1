package game.draw;

import game.objects.GameObject;
import game.objects.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GraphicIndicators
{
	/* Indicator 1 */
	private Sprite sprite_1;
	private Animator animation_1;
	
	/* Indicator 2 */
	private Sprite sprite_2;
	private Animator animation_2;
	private boolean selectedDevice;
	
	/* Draw */
	private float drawWidth = 7, drawHeight = 7;
	private float drawOffsetX = 0, drawOffsetY = 1;
	private float drawWidth2 = 7, drawHeight2 = 7;
	
	/* Position */
	float x, y;
	private Player player;
	private GameObject device;
	
	/* Constructor */
	public GraphicIndicators(Texture indicators, Player player)
	{
		this.player = player;
		
		/* Sprites */
		this.sprite_1 = new Sprite(indicators); 
		this.animation_1 = new Animator(this.sprite_1, 50, 50);
		this.sprite_2 = new Sprite(indicators);
		this.animation_2 = new Animator(this.sprite_2, 50, 50);
		
		/* Add Animations */
		this.animation_1.add_animation(5, 0, 5, 10, true);
		this.animation_1.add_animation(0, 1, 5, 10, true);
		this.animation_1.add_animation(5, 1, 5, 10, true);
		this.animation_1.add_animation(0, 2, 8, 10, true);
		this.animation_1.set_animation(0);
		
		this.animation_2.add_animation(0, 0, 5, 10, true);
		this.animation_2.add_animation(5, 1, 5, 10, true);
		this.animation_2.set_animation(0);
	}//END GraphicIndicators
	
	public void initialize_device(GameObject device)
	{
		this.device = device;
	}//END initialize_device
	
	/* Update */
	public void update(float dt)
	{
		this.animation_1.update(dt);
		this.animation_2.update(dt);
	}//END update
	
	/* Render */
	public void render(SpriteBatch spritebatch, float[] renderInfo)
	{
		this.selectedDevice = false;
		GameObject target = player.get_target();
		if(target == null || this.player.isIdle())
		{
			if(this.animation_1.get_currentAnimation() != 3)
			{
				this.animation_1.set_animation(3);
				this.animation_1.setCurrentFrame(0);
				this.drawHeight = 10;
				this.drawWidth = 10;
				this.drawOffsetY = 1;
			}//fi
			Vector2 heading = player.get_heading();
			this.x = heading.x;
			this.y = heading.y;
		}//fi
		else
		{
			if(target.getID() == 3 && this.animation_1.get_currentAnimation() != 1)
			{
				this.animation_1.set_animation(1);
				this.animation_1.setCurrentFrame(0);
				this.drawHeight = 7;
				this.drawWidth = 7;
				this.drawOffsetY = 0.5f;
			}//fi
			if (target.getID() == 1 && !this.player.isIdle())
			{
				if(this.animation_2.get_currentAnimation() != 1)
				{
					this.animation_2.set_animation(1);
				}//fi
				this.selectedDevice = true;
			}//fi esle
			this.x = target.get_positionX();
			this.y = target.get_positionY();
		}//esle
		
		if(this.selectedDevice == false && this.animation_2.get_currentAnimation() != 0)
		{
			this.animation_2.set_animation(0);
		}//fi
		
		if(!this.player.isIdle() && !this.selectedDevice)
		{
			/* Sprite 1 */
			this.sprite_1.setOrigin(renderInfo[2] * (this.drawWidth/2),
					renderInfo[2] * (this.drawHeight/2));
			this.sprite_1.setSize(renderInfo[2] * (this.drawWidth),
					renderInfo[2] * (this.drawHeight));
			this.sprite_1.setPosition(renderInfo[2] * (this.x - this.drawWidth/2 + this.drawOffsetX),
					renderInfo[2] * (this.y - this.drawHeight/2  + this.drawOffsetY));
			this.sprite_1.draw(spritebatch);
		}//fi
		
		/* Sprite 2 */
		this.sprite_2.setOrigin(renderInfo[2] * (this.drawWidth2/2),
				renderInfo[2] * (this.drawHeight2/2));
		this.sprite_2.setSize(renderInfo[2] * (this.drawWidth2),
				renderInfo[2] * (this.drawHeight2));
		this.sprite_2.setPosition(renderInfo[2] * (this.device.get_positionX() - this.drawWidth2/2 + this.drawOffsetX),
				renderInfo[2] * (this.device.get_positionY() - this.drawHeight2/2  - 1));
		this.sprite_2.draw(spritebatch);
	}//END render
}
