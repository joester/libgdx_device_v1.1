package game.objects;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class Projectile extends AnimatedObject {

	public Projectile (int objectID, float posX, float posY, float mass,
			float friction, float hitWidth, float hitHeight, float hitX,
			float hitY, boolean isSolid, float touchRadius,
			boolean isTouchable, float drawWidth, float drawHeight,
			Texture sprites, int srcWidth, int srcHeight, SoundSystem sounds) {
		super(objectID, posX, posY, mass, friction, hitWidth, hitHeight, hitX, hitY,
				isSolid, touchRadius, isTouchable, drawWidth, drawHeight, sprites,
				srcWidth, srcHeight, sounds);
		
		float flyingTimer = 30;
		// Joseph will come back to this
	}

	
}
