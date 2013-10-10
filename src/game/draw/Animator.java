package game.draw;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Animator
{
	/* Animation */
	private int animation_indicator;
	private Animation current_animation;
	private ArrayList<Animation> animations = new ArrayList<Animation>(); //A list of animations.
	
	/* Sprite */
	protected Sprite sprite;
	protected int srcWidth, srcHeight; //The size of the sprite to draw.
	
	/* Constructor */
	/**
	 * Creates an Animator object which helps control and manage animations of other objects.
	 * 
	 * @param sprite the sprites that are used for animation
	 * @param srcWidth the width of the sprites to cut out
	 * @param srcHeight the height of the sprites to cut out
	 */
	public Animator(Sprite sprite, int srcWidth, int srcHeight)
	{
		this.sprite = sprite;
		this.srcWidth = srcWidth;
		this.srcHeight = srcHeight;
	}//END Animator
	
	/* Animation */
	private class Animation
	{
		/* Frames */
		private int[] animationFrames;
		private int startX, startY; //Where the animation starts.
		private int curFrame = 0; //The current frame in the animation.
		
		private float animationIndicator = 0; //Indicates when to go to the next frame.
		private float animationSpeed; //The speed at which the animation animates.
		
		/* Looping */
		private boolean looping; //Indicates whether the animation loops or not.
		private boolean isDone = false; //Indicates whether or not the animation is looping or not.
		
		/* Constructor */
		public Animation(int startX, int startY, int numFrames, float animationSpeed, boolean loops)
		{
			this.startX = startX;
			this.startY = startY;
			
			this.animationFrames = new int[numFrames];
			for(int i = 0; i < this.numFrames(); i++)
			{
				this.animationFrames[i] = i;
			}//rof
			
			this.animationSpeed = animationSpeed;
			this.looping = loops;
		}//END Animation
		
		public Animation(int startX, int startY, float animationSpeed, boolean loops, int... frames)
		{
			this.startX = startX;
			this.startY = startY;
			
			this.animationFrames = frames;
			
			this.animationSpeed = animationSpeed;
			this.looping = loops;
		}//END Animation
		
		/* Animation */
		private int numFrames()
		{
			return this.animationFrames.length;
		}//END numFrames
		
		public int getFrameX()
		{
			return this.startX + this.animationFrames[this.curFrame];
		}//END getFrameX
		
		public int getFrameY()
		{
			return this.startY;
		}//END getFrameY
		
		public void reset_animation()
		{
			this.curFrame = 0;
			this.isDone = false;
		}//END reset_animation
		
		private void nextFrame()
		{
			this.curFrame++;
			
			if(this.curFrame >= this.numFrames())
			{
				if(this.looping)
				{
					this.curFrame = 0;
				}//fi
				else
				{
					this.curFrame--;
					this.isDone = true;
				}//esle
			}//fi
		}//END nextFrame
		
		public int getCurrentFrame()
		{
			return this.curFrame;
		}//END getCurrentFrame
		
		public void setCurrentFrame(int frame)
		{
			this.curFrame = frame;
		}//END setCurrentFrame
		
		public boolean isDone()
		{
			return this.isDone;
		}//END isDone
		
		/* Update */
		public void update(float delta)
		{
			this.animationIndicator += delta;
			
			while(this.animationIndicator > 1/this.animationSpeed)
			{
				this.animationIndicator -= 1/this.animationSpeed;
				this.nextFrame();
			}//elihw
		}//END update
		
		public void changeSpeed(float speed)
		{
			this.animationSpeed = speed;
		}
	}//END class Animation
	
	/**
	 * Adds an animations to a list of animations.
	 * 
	 * @param startX the column the starting sprite is on
	 * @param startY the row the sprites are on
	 * @param numFrames the number of frames total
	 * @param animationSpeed the number of frames per second/interval
	 * @param loops whether the animation is looping or not
	 */
	public void add_animation(int startX, int startY, int numFrames, float animationSpeed, boolean loops)
	{
		this.animations.add(new Animation(startX, startY, numFrames, animationSpeed, loops));
	}//END add_animation
	
	/**
	 * Adds an animations to a list of animations.
	 * 
	 * @param startX the column the starting sprite is on
	 * @param startY the row the sprites are on
	 * @param animationSpeed the number of frames per second/interval
	 * @param loops whether the animation is looping or not
	 * @param frames the frames to animate
	 */
	public void add_animation(int startX, int startY, float animationSpeed, boolean loops, int... frames)
	{
		this.animations.add(new Animation(startX, startY, animationSpeed, loops, frames));
	}//END add_animation
	
	public void change_animationSpeed(int animation_id, float animationSpeed)
	{
		Animation animation = this.animations.get(animation_id);
		animation.changeSpeed(animationSpeed);
	}//END add_animation
	
	/**
	 * Sets the animation to animate.
	 * 
	 * @param animationID the ID of the animation.
	 */
	public void set_animation(int animationID)
	{
		if(this.current_animation != null)
		{
			this.current_animation.reset_animation();
		}//fi
		
		this.animation_indicator = animationID;
		this.current_animation = this.animations.get(animationID);
	}//END set_animation
	
	/**
	 * Returns the current animation ID being used.
	 * 
	 * @return the ID of the animation.
	 */
	public int get_currentAnimation()
	{
		return this.animation_indicator;
	}//END get_currentAnimation
	
	/**
	 * @return the current frame of the current animation
	 */
	public int getCurrentFrame()
	{
		return this.current_animation.getCurrentFrame();
	}//END getCurrentFrame
	
	/**
	 * Sets the current frame to the indicated value.
	 * 
	 * @param frame the frame number
	 */
	public void setCurrentFrame(int frame)
	{
		this.current_animation.setCurrentFrame(frame);
	}//END setCurrentFrame
	
	/**
	 * @return whether the animation is done or not
	 */
	public boolean isDone()
	{
		return this.current_animation.isDone();
	}//END isDone
	
	/* Update */
	/**
	 * Updates the current animation and perhaps what it is displaying.
	 * 
	 * @param delta a value that helps determine how to animate.
	 */
	public void update(float delta)
	{
		if(this.current_animation != null)
		{
			//Update Animation
			this.current_animation.update(delta);
			//Update Sprite
			this.sprite.setRegion(this.current_animation.getFrameX() * this.srcWidth, this.current_animation.getFrameY() * this.srcHeight, this.srcWidth, this.srcHeight);
		}//fi
		else
		{
			//Set to first frame.
			this.sprite.setBounds(0, 0, this.srcWidth, this.srcHeight);
		}//esle
	}//END update
}//END class Animator

//EOF
