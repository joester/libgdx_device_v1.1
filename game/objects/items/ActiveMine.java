package game.objects.items;

import game.objects.AnimatedObject;
import game.objects.GameObject;

import java.util.ArrayList;
import java.util.Iterator;

import sounds.SoundSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ActiveMine extends AnimatedObject{

	private boolean isExploding = false;
	private boolean isExploding2 = false;
	
	public ActiveMine(float posX, float posY, SoundSystem sounds, Texture tex) {
		super(97, posX, posY, 1, 90, 8, 8, 0, 0,
				false, 5, true, 18, 18, tex,
				200, 200, sounds);
		
		this.screenBound = true;
		this.drawOffsetY = 6.5f;
		this.add_animation(1, 0, 5, 10, false);
		this.add_animation(0, 0, 1, 1, true);
		this.animator.set_animation(1);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void behavior_collision(GameObject obj){
		if(obj.getID() == 3 && !this.isExploding){
			sounds.playSound(SoundSystem.mine);
			isExploding = true;
			isExploding2 = true;
			isTouchable = false;
			this.drawOffsetY = 10;
			this.drawWidth = 30;
			this.drawHeight = 30;
			this.animator.set_animation(0);
			this.set_velocity(0, 0);
			this.isTouchable = false;
		}
	}
	
	@Override
	public void update(float dt, ArrayList<GameObject> objects){
		super.update(dt, objects);
		if(isExploding2){
			Iterator<GameObject> iter = objects.iterator();
			while(iter.hasNext())
			{
				GameObject obj = iter.next();
				float dist = (float)Math.sqrt((this.get_positionX() - obj.get_positionX()) * (this.get_positionX() - obj.get_positionX()) + (this.get_positionY() - obj.get_positionY()) * (this.get_positionY() - obj.get_positionY()));
				if(obj.getID() == 3 && dist < 20)
				{
					obj.get_healthAttribute().current -= 3;
				}
			}
			this.isExploding2 = false;
		}
		if(isExploding && this.animator.isDone()){
			this.terminate();
		}
	}
	
}
