package game.objects;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;

public class DustCloud extends AnimatedObject{

	public DustCloud(float posX, float posY, Texture sprites, SoundSystem sounds) {
		super(13, posX, posY, 0, 0, 0, 0, 0, 0,
				false, 0, false, 2, 2, sprites,
				256, 256, sounds);
		
	}
	
}
