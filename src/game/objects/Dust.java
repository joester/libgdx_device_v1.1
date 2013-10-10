package game.objects;

import java.util.ArrayList;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class Dust extends GameObject
{

	public Dust(int objectID, float posX, float posY, float mass,
			float friction, float hitWidth, float hitHeight, float hitX,
			float hitY, boolean isSolid, float touchRadius,
			boolean isTouchable, float drawWidth, float drawHeight,
			Texture sprites, int srcWidth, int srcHeight, SoundSystem sounds) {
		super(9001, posX, posY, 1, 7, 1, 1, 0, 0,
				false, 0, false, 5, 5, sprites,
				5, 5, sounds);
		// TODO Auto-generated constructor stub
	}
	
	public void update(float dt, ArrayList<GameObject> objects){
		
	}
	
}
