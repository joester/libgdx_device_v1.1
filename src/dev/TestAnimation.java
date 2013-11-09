package dev;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TestAnimation {
	
	private int index, numFrames;
	private float animationTime, timeElapsed;
	private Sprite animation;
	private int[][] dimensions;
	
	public TestAnimation(int numFrames, float animationTime, Texture t, int[][] dimensions){
		animation = new Sprite(t);
		this.dimensions = dimensions;
		this.animationTime = animationTime;
	}
	
	public void update(float dt){
		if(timeElapsed >= animationTime / numFrames){
			if(index == numFrames){
				index = 0;
			}
		}
	}	
}
