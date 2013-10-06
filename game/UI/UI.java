//ToDo: All UI Size values don't scale
package game.UI;

import game.GameStats;
import game.TheDevice;
import game.draw.GraphicsManager;
import game.objects.items.ActiveMine;
import game.objects.items.Vortex;
import game.room.Room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import sounds.SoundSystem;

public class UI
{
	float UIBar;
	float time = 0;
	float timeLimit = 90;
	float nukeCD = 0;
	boolean minePlace = false;
	boolean vortexPlace = false;
	boolean playMOnce = false;
	boolean playNOnce = false;
	boolean playVOnce = false;
	int score = 0;
	int life = 4;
	int nukes = 3;
	int XP = 0;
	int XPMax = 100;
	int level = 1;
	BitmapFont font;
	BitmapFont fontsm;
	GraphicsManager graphics;
	private Sprite UIBase;
	private Sprite XPFill;
	private Sprite XPEmpty;
	private Sprite XPGlow;
	private Sprite pause;
	private Sprite nuke;
	private Sprite nukeCount;
	private Sprite mine;
	private Sprite mineCount;
	private Sprite vortex;
	private Sprite vortexCount;
	private Room room;
	private SoundSystem sound;
	TheDevice state;
	GameStats g;
	float[] render;
	
	private Rectangle UIBox;
	private Rectangle nukeB;
	private Rectangle pauseB;
	private Rectangle mineB;
	private Rectangle vortexB;
	private boolean pauseState = false;
	public boolean nukeState = false;
	
	public UI(GameStats stats, GraphicsManager graphics, TheDevice state, Room room, SoundSystem sound, float[] render){
		this.state = state;
		this.UIBase = new Sprite(graphics.ID(1000));
		this.XPFill = new Sprite(graphics.ID(1001));
		this.XPEmpty = new Sprite(graphics.ID(1002));
		this.XPGlow = new Sprite(graphics.ID(1003));
		this.pause = new Sprite(graphics.ID(1004));
		this.nuke = new Sprite(graphics.ID(1006));
		this.nukeCount = new Sprite(graphics.ID(1007));
		this.mine = new Sprite(graphics.ID(1008));
		this.mineCount = new Sprite(graphics.ID(1009));
		this.vortex = new Sprite(graphics.ID(1010));
		this.vortexCount = new Sprite(graphics.ID(1009));
		this.sound = sound;
		this.g = stats;
		this.graphics = graphics;
		this.room = room;
		this.render = render;
		//Set Fill
		XPFill.setPosition(render[0]*render[2]*0.89f, Gdx.graphics.getHeight() - render[1]*render[2]*0.395f);
		XPEmpty.setPosition(render[0]*render[2]*0.89f, Gdx.graphics.getHeight() - render[1]*render[2]*0.395f);
		XPEmpty.setSize(render[0]*render[2]*0.11f, render[1]*render[2]*0.07f);
		UIBox = UIBase.getBoundingRectangle();
		//Set Glow
		XPGlow.setSize(render[0] * 0.15f * render[2],render[1] * render[2]);
		XPGlow.setPosition(render[0]*render[2]*0.85f, 0);
		//Set Pause
		pause.setPosition(render[0]*render[2]*0.92f, render[1]*render[2]*0.90f);
		pause.setRegion(0, 0, 57, 57);
		pause.setSize(render[0]*render[2]*0.05f,render[1]*render[2]*0.09f);
		pauseB = pause.getBoundingRectangle();
		//Set Nuke
		nuke.setPosition(render[0]*render[2]*0.88f, render[1]*render[2]*0.14f);
		nuke.setSize(render[0]*render[2]*0.12f, render[1]*render[2]*0.16f);
		nukeB = nuke.getBoundingRectangle();
		nukeCount.setSize(render[0]*render[2]*0.08f,render[1]*render[2]*0.095f);
		//Set Mine
		mine.setPosition(render[0]*render[2]*0.88f, render[1]*render[2]*0.30f);
		mine.setSize(render[0]*render[2]*0.11f,render[1]*render[2]*0.15f);
		mine.setRegion(0,0,124,98);
		mineB = mine.getBoundingRectangle();
		//Set Mine count
		mineCount.setPosition(render[0]*render[2]*0.942f,render[1]*render[2]*0.345f);
		mineCount.setSize(render[0]*render[2]*0.035f,render[1]*render[2]*0.058f);
		mineCount.setRegion(117-(39*g.getMineCount()),0,39,38);
		//Set Vortex
		vortex.setPosition(render[0]*render[2]*0.88f, render[1]*render[2]*0.45f);
		vortex.setSize(render[0]*render[2]*0.11f,render[1]*render[2]*0.15f);
		vortex.setRegion(0,0,124,98);
		vortexB = vortex.getBoundingRectangle();
		//Set Vortex count
		vortexCount.setPosition(render[0]*render[2]*0.942f,render[1]*render[2]*0.495f);
		vortexCount.setSize(render[0]*render[2]*0.035f,render[1]*render[2]*0.058f);
		vortexCount.setRegion(117-(39*g.getMineCount()),0,39,38);
		
		UIBar = Gdx.graphics.getWidth() - render[0]*render[2]*0.18f;
		font = new BitmapFont(Gdx.files.internal("data/bearz/bearz.fnt"),Gdx.files.internal("data/bearz/bearz.png"), false);
	}
	
	public void create()
	{
		
	}
	
	public boolean update(float dt)
	{
		//Nuke button
		if(Gdx.input.justTouched() && nukeB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && nukes > 0 && nukeCD <= 0 && !pauseState)
		{
			nukes--;
			nukeCD = 3;
			nukeState = true;
		}
		if(nukes == 0 || nukeCD > 0)
		{
			if(!playNOnce)
			{
				
				sound.playSound(SoundSystem.buttonl);
				playNOnce = true;
			}
			nuke.setRegion(135,0,134,105);
		}
		//Mine button
		
		if(Gdx.input.justTouched() && mineB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && g.getMineCount() > 0 && !minePlace && !vortexPlace && !pauseState)
		{
			g.useMine();
			minePlace = true;
		}
		if(g.getMineCount() == 0 || minePlace)
		{
			if(!playMOnce)
			{
				sound.playSound(SoundSystem.buttonl);
				playMOnce = true;
			}
			mine.setRegion(124,0,124,95);
		}
		//Vortex Button
		if(Gdx.input.justTouched() && vortexB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && g.getVortCount() > 0 && !minePlace && !vortexPlace && !pauseState)
		{
			g.useVort();
			vortexPlace = true;
		}
		if(g.getVortCount() == 0 || vortexPlace)
		{
			if(!playVOnce)
			{
				sound.playSound(SoundSystem.buttonl);
				playVOnce = true;
			}
			
			vortex.setRegion(124,0,124,95);
		}
		if(Gdx.input.justTouched())
		{
			if(pauseB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()))
			{
				pauseState = !pauseState;
			}
		}
		return pauseState;
		
	}
	
	public boolean placeItem()
	{
		return minePlace || vortexPlace;
	}
	
	public void render(SpriteBatch batch, float dt)
	{
		//Draw XP
		XPFill.setSize(g.getXPpercent() * 121, 45);
		XPEmpty.draw(batch);
		XPFill.draw(batch);
		
		//Draw Level
		String levelString = Integer.toString(g.getLevel());
		font.draw(batch, levelString, render[0]*render[2]*0.975f-font.getBounds(levelString).width/2, render[1]*render[2]*0.673f);
		
		//Draw UIBase
		UIBase.setSize(render[0] * 0.15f * render[2],render[1] * render[2]);
		UIBase.setPosition(Gdx.graphics.getWidth()-UIBase.getWidth(), 0);
		UIBase.draw(batch);
		
		//Draw Glow
		if(g.getXPpercent()>.95)
		{
			XPGlow.setRegion(345,0,172,602);
			XPGlow.draw(batch);
		}
		else if(g.getXPpercent() > .8)
		{
			XPGlow.setRegion(173,0,172,602);
			XPGlow.draw(batch);
		}
		else if(g.getXPpercent() > .5)
		{
			XPGlow.setRegion(0,0,172,602);
			XPGlow.draw(batch);
		}
		//Draw Score
		String scoreString = Integer.toString(g.getScore());
		font.draw(batch, scoreString, Gdx.graphics.getWidth() - font.getBounds(scoreString).width - render[0]*render[2]*0.01f, render[1]*render[2]*0.83f);
		
		//Draw time
		g.updateTimeElapsed(dt);
		String timeString = Integer.toString(g.timeElapsed());
		font.draw(batch, timeString, (Gdx.graphics.getWidth()-UIBase.getWidth() - font.getBounds(timeString).width)/2, Gdx.graphics.getHeight() - (font.getBounds(timeString).height / 2));

		//Draw mine
		mine.draw(batch);
		if (g.getMineCount() > 0 && !minePlace)
		{
			mine.setRegion(0,0,124,98);
			mineCount.setRegion(117-(39*g.getMineCount()),0,39,38);
			mineCount.draw(batch);
		}
		if(minePlace && Gdx.input.isTouched() && !mineB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.85f)
		{
			if(playMOnce)
			{
				sound.playSound(SoundSystem.buttonh);
				playMOnce = false;
			}	
			minePlace = false;
			room.add_object(new ActiveMine(Gdx.input.getX() / state.state.renderInfo[2], (Gdx.graphics.getHeight() - Gdx.input.getY()) / state.state.renderInfo[2], sound, graphics.ID(10)));
			mine.setRegion(0,0,124,98);
		}
		else if(minePlace)
		{
			mine.setRegion(124,0,124,98);
		}
		//Draw vortex
		vortex.draw(batch);
		if (g.getVortCount() > 0 && !vortexPlace)
		{
			vortex.setRegion(0,0,124,98);
			vortexCount.setRegion(117-(39*g.getVortCount()),0,39,38);
			vortexCount.draw(batch);
		}
		if(vortexPlace && Gdx.input.isTouched() && !vortexB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.85f)
		{
			if(playVOnce)
			{
				sound.playSound(SoundSystem.buttonh);
				playVOnce = false;
			}
			vortexPlace = false;
			room.add_object(new Vortex(Gdx.input.getX() / render[2], (Gdx.graphics.getHeight() - Gdx.input.getY()) / render[2], sound, graphics.ID(12)));
			vortex.setRegion(124,0,124,98);
		}
		else if(vortexPlace)
		{
			vortex.setRegion(124,0,124,98);
		}
		//Draw nuke
		nuke.draw(batch);
		if(nukeCD > 0)
		{
			nukeCD-=dt;
		}
		else
		{
			if(playNOnce)
			{
				sound.playSound(SoundSystem.buttonh);
				playNOnce = false;
			}
			nuke.setRegion(0,0,134,105);
		}
		
		//Draw NukeCount
		if(nukes > 0)
		{
			nukeCount.setRegion(0,0,93,64);

		}
		else
		{
			nukeCount.setRegion(94,0,93,64);
		}
		nukeCount.setPosition(render[0]*render[2]*0.86f,render[1]*render[2]*0.09f);
		nukeCount.draw(batch);
		if(nukes > 1)
		{
			nukeCount.setRegion(0,0,93,64);
			
		}
		else
		{
			nukeCount.setRegion(94,0,93,64);
		}
		nukeCount.setPosition(render[0]*render[2]*0.90f,render[1]*render[2]*0.07f);
		nukeCount.draw(batch);
		if(nukes > 2)
		{
			nukeCount.setRegion(0,0,93,64);
			
		}
		else
		{
			nukeCount.setRegion(94,0,93,64);
		}
		nukeCount.setPosition(render[0]*render[2]*0.94f,render[1]*render[2]*0.09f);
		nukeCount.draw(batch);
		
		
		//Draw Pause
		if (!pauseState)
		{
			pause.setRegion(0,0,57,57);
			pause.draw(batch);
		}
		else
		{
			pause.setRegion(58,0,57,57);
			pause.draw(batch);
		}
	}
	//Adds a nuke if nukeCount is not 3 or greater. Used for dev tool.
	public void addNuke(){
		if(nukes < 3){
			nukes ++;
		}
	}
}