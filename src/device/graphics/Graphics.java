package device.graphics;

import com.badlogic.gdx.Gdx;

public class Graphics
{
	private int baseWidth = 1280;
	private int baseHeight = 800;
	
	private int phoneWidth;
	private int phoneHeight;
	
	private int widthRatio;
	private int heightRatio;
	
	private SpriteObject[] background;
	private SpriteObject[] actors;
	private SpriteObject[] items;
	private SpriteObject[] ui;
	private SpriteObject[] buttons;
	
	public Graphics()
	{
		phoneWidth = Gdx.graphics.getWidth();
		phoneHeight = Gdx.graphics.getHeight();
		
		widthRatio = phoneWidth / baseWidth;
		heightRatio = phoneHeight / baseHeight;
	}
	
	public void resize()
	{
		
	}
}