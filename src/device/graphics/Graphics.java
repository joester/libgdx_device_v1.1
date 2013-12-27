package device.graphics;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import device.graphics.Point;

public final class Graphics
{
	
	private static int screenWidth = Gdx.graphics.getWidth();
	private static int screenHeight = Gdx.graphics.getHeight();
	
	private static Sprite background = null;
	private static TreeMap<Float, Sprite> actors = new TreeMap<Float, Sprite>();
	private static TreeMap<Float, Sprite> hpbars = new TreeMap<Float, Sprite>();
	private static Sprite ui = null;
	private static HashSet<Sprite> buttons = new HashSet<Sprite>();
	private static HashSet<Sprite> extras = new HashSet<Sprite>();
	private static TreeMap<Point, String> text = new TreeMap<Point, String>();
	
	public static BitmapFont font;
	
	public static enum TYPES { BACKGROUND, ACTOR, HPBAR, UI, BUTTON, EXTRAS }
	
	public Graphics()
	{
		font = new BitmapFont(Gdx.files.internal("data/bearz/bearz.fnt"),Gdx.files.internal("data/bearz/bearz.png"), false);
	}
	
	/** Draws sprites to screen. Sprite draw order is automatically done correctly. Valid TYPES are: { BACKGROUND, ACTOR, HPBAR, UI, BUTTON, EXTRAS }
	 * @param type the category the object belongs to
	 * @param sprite the sprite to be drawn
	 * @param xPos the X Position to draw the sprite, relative to the screen width
	 * @param yPos the Y Position to draw the sprite, relative to the screen height
	 * @param width the width of the sprite, relative to the screen width
	 * @param height the height of the sprite, relative to the screen height
	 * */
	public static void draw(TYPES type, Sprite sprite, float xPos, float yPos, float width, float height)
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
				ui = sprite;
				break;
				
			case BUTTON:
				sprite.setOrigin(0, 0);
				sprite.setBounds(xPos*screenWidth, yPos*screenHeight, width*screenWidth, height*screenHeight);
				buttons.add(sprite);
				break;
				
			case EXTRAS:
				extras.remove(sprite);
				sprite.setOrigin(0, 0);
				sprite.setBounds(xPos*screenWidth, yPos*screenHeight, width*screenWidth, height*screenHeight);
				extras.add(sprite);
				break;
		}
	}
	
	/** Write text to screen
	 * @param string Text to write
	 * @param xPos xPosition to write at, relative to screen width
	 * @param yPos yPosition to write at, relative to screen height*/
	public static void write(String string, float xPos, float yPos)
	{
		text.put(new Point(xPos*screenWidth, yPos*screenHeight), string);
	}
	
	public static void draw(SpriteBatch s)
	{
		//SpriteBatch s = new SpriteBatch();
		//s.begin();
		if(background != null)
			background.draw(s);
		for(Sprite sprite : actors.values())
			sprite.draw(s);
		for(Sprite sprite : hpbars.values())
			sprite.draw(s);
		if(ui != null)
			ui.draw(s);
		for(Sprite sprite : buttons)
			sprite.draw(s);
		for(Sprite sprite : extras)
			sprite.draw(s);
		for(Entry<Point, String> entry : text.entrySet())
			font.draw(s, entry.getValue(), entry.getKey().x, entry.getKey().y);
		//s.end();
	}
	
	public static void clearAll()
	{
		background = null;
		actors.clear();
		hpbars.clear();
		ui = null;
		buttons.clear();
		extras.clear();
		text.clear();
	}

	public static void clear()
	{
		actors.clear();
		hpbars.clear();
		buttons.clear();
		extras.clear();
		text.clear();
	}

	public static void textRefresh()
	{
		text.clear();
	}
}