//ToDo: All UI Size values don't scale
package game.UI;

import game.GameStats;
import game.TheDevice;
import game.draw.GraphicsManager;
import game.objects.items.ActiveMine;
import game.objects.items.Vortex;
import game.room.Room;

import device.graphics.Graphics;

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
	int score = 0;
	int level = 1;
	BitmapFont font;
	BitmapFont fontsm;
	GraphicsManager graphics;
	private Sprite UIBase;
	private Sprite pause;
	private Sprite nuke;
	private Sprite nukeCount1;
	private Sprite nukeCount2;
	private Sprite nukeCount3;
	private Sprite mine;
	private Sprite mineCount;
	private Sprite vortex;
	private Sprite vortexCount;
	private Room room;
	private SoundSystem sound;
	TheDevice state;
	GameStats g;
	float[] render;
	
	private float gWidth;
	private float gHeight;
	
	private Rectangle nukeB;
	private Rectangle pauseB;
	private Rectangle mineB;
	private Rectangle vortexB;
	
	public UI(GameStats stats, GraphicsManager graphics, TheDevice state, Room room, SoundSystem sound, float[] render){
		this.state = state;
		gWidth = render[0]*render[2];
		gHeight = render[1]*render[2];
		
		this.UIBase = new Sprite(room.getAsset("ui_base"));
		this.pause = new Sprite(room.getAsset("ui_pause"));
		this.nuke = new Sprite(room.getAsset("ui_bomb"));
		this.nukeCount1 = new Sprite(room.getAsset("ui_bombcount"));
		this.nukeCount2 = new Sprite(room.getAsset("ui_bombcount"));
		this.nukeCount3 = new Sprite(room.getAsset("ui_bombcount"));
		this.mine = new Sprite(room.getAsset("ui_mine"));
		this.mineCount = new Sprite(room.getAsset("ui_minecount"));
		this.vortex = new Sprite(room.getAsset("ui_vortex"));
		this.vortexCount = new Sprite(room.getAsset("ui_minecount"));
		this.sound = sound;
		this.g = stats;
		this.graphics = graphics;
		this.room = room;
		this.render = render;
		//Set UIBase
		Graphics.add(Graphics.TYPES.UI, UIBase,0.8f,0,0.2f,1);
		//Set Pause
		Graphics.add(Graphics.TYPES.BUTTON, pause, 0.90f, 0.90f, 0.05f, 0.09f);
		pauseB = pause.getBoundingRectangle();
		//Set Nuke
		Graphics.add(Graphics.TYPES.BUTTON, nuke, 0.85f, 0.06f,0.15f,0.18f);
		nukeB = nuke.getBoundingRectangle();
		//Set NukeCount
		Graphics.add(Graphics.TYPES.BUTTON, nukeCount1, 0.835f, 0.20f,0.08f,0.095f);
		Graphics.add(Graphics.TYPES.BUTTON, nukeCount2, 0.885f, 0.22f,0.08f,0.095f);
		Graphics.add(Graphics.TYPES.BUTTON, nukeCount3, 0.935f, 0.20f,0.08f,0.095f);
		//Set Mine
		Graphics.add(Graphics.TYPES.BUTTON, mine, 0.83f, 0.57f, 0.17f, 0.165f);
		mineB = mine.getBoundingRectangle();
		//Set Mine count
		Graphics.add(Graphics.TYPES.EXTRAS, mineCount, 0.935f, 0.62f, 0.04f, 0.06f);
		//Set Vortex
		vortex.setPosition(gWidth*0.875f, gHeight*0.365f);
		vortex.setSize(gWidth*0.125f,gHeight*0.165f);
		vortex.setRegion(0,0,124,98);
		vortexB = vortex.getBoundingRectangle();
		//Set Vortex count
		vortexCount.setPosition(gWidth*0.95f,gHeight*0.42f);
		vortexCount.setSize(gWidth*0.035f,gHeight*0.058f);
		vortexCount.setRegion(117-(39*g.getMineCount()),0,39,38);
		
		UIBar = Gdx.graphics.getWidth() - gWidth*0.18f;
		font = new BitmapFont(Gdx.files.internal("data/bearz/bearz.fnt"),Gdx.files.internal("data/bearz/bearz.png"), false);
	}
	
	public void create()
	{
		
	}
	
	public boolean update(float dt)
	{
		if(!g.pauseState())
		{
			if(Gdx.input.justTouched())
			{
				int touchX = Gdx.input.getX();
				int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
				if(pauseB.contains(touchX, touchY))
					g.pauseToggle();
				if(nukeB.contains(touchX, touchY) && g.nukeReady() && nukeCD <= 0)
					g.useNuke();
				if(mineB.contains(touchX, touchY) && g.getMineCount() > 0)
					g.useMine();
				if(vortexB.contains(touchX, touchY) && g.getVortCount() > 0)
					g.useVort();
			}
		}
		return g.pauseState();
	}
	
	public void render(SpriteBatch batch, float dt)
	{
		if(!g.pauseState())
		{
			if(Gdx.input.justTouched() )
			{
				int touchX = Gdx.input.getX();
				int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
				if(touchX < Gdx.graphics.getWidth() * 0.8f)
				{
					if(g.placeItem() == 1)
						g.placeMine();
					if(g.placeItem() == 2)
						g.useVort();
				}
			}
		}
		//Draw Score
		String scoreString = Integer.toString(g.getScore());
		font.setScale(Gdx.graphics.getHeight()/450.0f);
		font.draw(batch, scoreString, Gdx.graphics.getWidth() - font.getBounds(scoreString).width - gWidth*0.01f, gHeight*0.81f);
		
		//Draw time
		g.updateTimeElapsed(dt);
		String timeString = Integer.toString(g.timeElapsed());
		font.draw(batch, timeString, (Gdx.graphics.getWidth()-UIBase.getWidth() - font.getBounds(timeString).width)/2, Gdx.graphics.getHeight() - (font.getBounds(timeString).height / 2));
		
		if(g.placeItem() == 1 && Gdx.input.isTouched() && !mineB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.85f)
		{
			g.placeMine();
			room.add_object(new ActiveMine(Gdx.input.getX() / state.state.renderInfo[2], (Gdx.graphics.getHeight() - Gdx.input.getY()) / state.state.renderInfo[2], sound, room.getAsset("mine")));
			mine.setRegion(0,0,124,98);
		}
		
		//Draw vortex
		vortex.draw(batch);
		if (g.getVortCount() > 0 && !(g.placeItem() == 2))
		{
			vortex.setRegion(0,0,124,98);
			vortexCount.setRegion(117-(39*g.getVortCount()),0,39,38);
			vortexCount.draw(batch);
		}
		if(g.placeItem() == 2 && Gdx.input.isTouched() && !vortexB.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.85f)
		{
			g.placeVort();
			room.add_object(new Vortex(Gdx.input.getX() / render[2], (Gdx.graphics.getHeight() - Gdx.input.getY()) / render[2], sound, room.getAsset("vortex")));
			vortex.setRegion(124,0,124,98);
		}
		else if(g.placeItem() == 2)
		{
			vortex.setRegion(124,0,124,98);
		}
		
		//Draw Mine Button
		
		if(g.mineReady())
		{
			mine.setRegion(0,0,124,95);
			mineCount.setRegion(39*(3-g.getMineCount()),0,39,38);
		}
		else
		{
			mine.setRegion(124,0,124,95);
			mineCount.setRegion(156,0,39,38);
		}
		
		//Update Nuke Button
		if(g.nukeReady())
			nuke.setRegion(0,0,134,105);
		else
			nuke.setRegion(135,0,134,105);
		
		//Update NukeCount
		if(g.nukeCount() > 0)
			nukeCount1.setRegion(0,0,93,64);
		else
			nukeCount1.setRegion(94,0,93,64);
		if(g.nukeCount() > 1)
			nukeCount2.setRegion(0,0,93,64);
		else
			nukeCount2.setRegion(94,0,93,64);
		if(g.nukeCount() > 2)
			nukeCount3.setRegion(0,0,93,64);
		else
			nukeCount3.setRegion(94,0,93,64);
		
		//Update Pause
		if (!g.pauseState())
			pause.setRegion(0,0,57,57);
		else
			pause.setRegion(58,0,57,57);
	}
}