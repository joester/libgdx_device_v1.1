package device.graphics;

import java.util.HashSet;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import device.graphics.Graphics.TYPES;

public final class Graphics
{
	
	private static int screenWidth = Gdx.graphics.getWidth();
	private static int screenHeight = Gdx.graphics.getHeight();
	
	private static Sprite background = new Sprite();
	private static TreeMap<Float, Sprite> actors = new TreeMap<Float, Sprite>();
	private static TreeMap<Float, Sprite> hpbars = new TreeMap<Float, Sprite>();
	private static HashSet<Sprite> ui = new HashSet<Sprite>();
	private static HashSet<Sprite> buttons = new HashSet<Sprite>();
	private static HashSet<Sprite> extras = new HashSet<Sprite>();
	
	public static enum TYPES { BACKGROUND, ACTOR, HPBAR, UI, BUTTON, EXTRAS }
	
	public Graphics()
	{
	}
	
	/** Adds element to the drawing list. Sprite draw order is automatically done correctly. Valid TYPES are: { BACKGROUND, ACTOR, HPBAR, UI, BUTTON, EXTRAS }*/
	public static void add(TYPES type, Sprite sprite, float xPos, float yPos, float width, float height)
	{
		switch(type)
		{
			case BACKGROUND:
				sprite.setOrigin(0,0);
				sprite.setBounds(xPos*screenWidth, yPos*screenHeight, width*screenWidth, height*screenHeight);
				background = sprite;
				break;
			
			case ACTOR:
				actors.put(-sprite.getBoundingRectangle().y, sprite);
				break;
				
			case HPBAR:
				hpbars.put(-sprite.getBoundingRectangle().y, sprite);
				break;
				
			case UI:
				sprite.setOrigin(0, 0);
				sprite.setBounds(xPos*screenWidth, yPos*screenHeight, width*screenWidth, height*screenHeight);
				ui.add(sprite);
				break;
				
			case BUTTON:
				sprite.setOrigin(0, 0);
				sprite.setBounds(xPos*screenWidth, yPos*screenHeight, width*screenWidth, height*screenHeight);
				buttons.add(sprite);
				break;
				
			case EXTRAS:
				System.out.println("ADD EXTRA");
				sprite.setOrigin(0, 0);
				sprite.setBounds(xPos*screenWidth, yPos*screenHeight, width*screenWidth, height*screenHeight);
				buttons.add(sprite);
				break;
		}
	}
	
	public void draw(SpriteBatch s)
	{
		background.draw(s);
		for(Sprite sprite : actors.values())
			sprite.draw(s);
		for(Sprite sprite : hpbars.values())
			sprite.draw(s);
		for(Sprite sprite : ui)
			sprite.draw(s);
		for(Sprite sprite : buttons)
			sprite.draw(s);
		for(Sprite sprite : extras)
			sprite.draw(s);
	}
	
	public void clearAll()
	{
		background = null;
		actors.clear();
		hpbars.clear();
		ui.clear();
		buttons.clear();
		extras.clear();
	}
}