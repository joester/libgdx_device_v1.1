package device.graphics;

import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Graphics
{
	private int baseWidth = 1280;
	private int baseHeight = 800;
	
	private int phoneWidth;
	private int phoneHeight;
	
	private int widthRatio;
	private int heightRatio;
	
	private Sprite background;
	private TreeMap<Float, Sprite> actors = new TreeMap<Float, Sprite>();
	private Sprite[] ui;
	private Sprite[] buttons;
	
	private int uicount = 0;
	private int bcount = 0;
	
	public enum TYPES
	{
		BACKGROUND, ACTOR, UI, BUTTON
	}
	
	public Graphics()
	{
		phoneWidth = Gdx.graphics.getWidth();
		phoneHeight = Gdx.graphics.getHeight();
		
		widthRatio = phoneWidth / baseWidth;
		heightRatio = phoneHeight / baseHeight;
	}
	
	public void add(TYPES t, Sprite s, Rectangle dst)
	{
		switch(t)
		{
			case BACKGROUND:
				background = s;
				break;
			
			case ACTOR:
				actors.put(-dst.y, s);
				break;
				
			case UI:
				ui[uicount] = s;
				break;
				
			case BUTTON:
				buttons[bcount] = s;
				break;
		}
	}
	
	public void resize()
	{
		background.setScale(widthRatio, heightRatio);
		for (Sprite s : actors.values())
			s.setScale(widthRatio, heightRatio);
		for (Sprite s : ui)
			s.setScale(widthRatio, heightRatio);
		for (Sprite s : buttons)
			s.setScale(widthRatio, heightRatio);
	}
	public void draw(SpriteBatch s)
	{
		resize();
		s.begin();
		background.draw(s);
		for(Sprite sprite : actors.values())
			sprite.draw(s);
		for(Sprite sprite : ui)
			sprite.draw(s);
		for(Sprite sprite : buttons)
			sprite.draw(s);
		s.end();
	}
}