//ToDo: All UI Size values don't scale
package game.UI;

import game.GameStats;
import game.objects.items.ActiveMine;
import game.objects.items.Vortex;
import game.room.Room;

import device.graphics.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import sounds.SoundSystem;

public class UI
{
	private BitmapFont font;
	private Sprite UIBase;
	
	private Sprite pause;
	
	private Sprite mine;
	private Sprite mineCount;
	
	private Sprite vortex;
	private Sprite vortexCount;
	
	private Sprite nukeCount1;
	private Sprite nukeCount2;
	private Sprite nukeCount3;
	private Sprite nuke;
	
	private Room room;
	private SoundSystem sound;
	private GameStats stats;
	
	public UI(GameStats istats, Room iroom, SoundSystem isound){
		sound = isound;
		stats = istats;
		room = iroom;
		font = new BitmapFont(Gdx.files.internal("data/bearz/bearz.fnt"),Gdx.files.internal("data/bearz/bearz.png"), false);
		UIBase = new Sprite(room.getAsset("ui_base"));
		pause = new Sprite(room.getAsset("ui_pause"));
		nuke = new Sprite(room.getAsset("ui_bomb"));
		nukeCount1 = new Sprite(room.getAsset("ui_bombcount"));
		nukeCount2 = new Sprite(room.getAsset("ui_bombcount"));
		nukeCount3 = new Sprite(room.getAsset("ui_bombcount"));
		mine = new Sprite(room.getAsset("ui_mine"));
		mineCount = new Sprite(room.getAsset("ui_minecount"));
		vortex = new Sprite(room.getAsset("ui_vortex"));
		vortexCount = new Sprite(room.getAsset("ui_minecount"));
		
		//Set UIBase
		Graphics.draw(Graphics.TYPES.UI, UIBase,0.8f,0,0.2f,1);
		
		//Set Pause
		Graphics.draw(Graphics.TYPES.BUTTON, pause, 0.90f, 0.90f, 0.05f, 0.09f);
		
		//Set Mine
		Graphics.draw(Graphics.TYPES.BUTTON, mine, 0.83f, 0.57f, 0.17f, 0.165f);
		
		//Set Vortex
		Graphics.draw(Graphics.TYPES.BUTTON, vortex, 0.83f, 0.35f, 0.17f, 0.165f);
		
		//Set Nuke
		Graphics.draw(Graphics.TYPES.BUTTON, nuke, 0.84f, 0.06f,0.15f,0.18f);
		
		//Set NukeCount
		Graphics.draw(Graphics.TYPES.BUTTON, nukeCount1, 0.825f, 0.20f,0.08f,0.095f);
		Graphics.draw(Graphics.TYPES.BUTTON, nukeCount2, 0.875f, 0.22f,0.08f,0.095f);
		Graphics.draw(Graphics.TYPES.BUTTON, nukeCount3, 0.925f, 0.20f,0.08f,0.095f);
	}
	
	public void create()
	{
	}
	
	public boolean update()
	{
		if(Gdx.input.justTouched())
		{
			int touchX = Gdx.input.getX();
			int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
			if(pause.getBoundingRectangle().contains(touchX, touchY))
				stats.pauseToggle();
			if(!stats.pauseState())
			{
				if(mine.getBoundingRectangle().contains(touchX, touchY)&& stats.mineReady())
					stats.useMine();
				else if(vortex.getBoundingRectangle().contains(touchX, touchY) && stats.vortexReady())
					stats.useVort();
				else if(vortex.getBoundingRectangle().contains(touchX, touchY) && stats.nukeReady())
					stats.useNuke();
			}
		}
		return stats.pauseState();
	}
	
	public void render()
	{
		if(!stats.pauseState())
		{
			if(Gdx.input.justTouched() )
			{
				int touchX = Gdx.input.getX();
				int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
				if(touchX < Gdx.graphics.getWidth() * 0.8f)
				{
					if(stats.placeItem() == 1)
					{
						stats.placeMine();
						System.out.println("Touch X: " + touchX + "\t Touch Y: " + touchY);
						room.add_object(new ActiveMine(touchX * Gdx.graphics.getWidth(), touchY * Gdx.graphics.getHeight(), sound, room.getAsset("mine")));
					}
						
					else if(stats.placeItem() == 2)
					{
						stats.placeVort();
						room.add_object(new Vortex(touchX, touchY, sound, room.getAsset("vortex")));
					}
				}
			}
		}
		
		Graphics.textRefresh();
		
		//Draw time
		stats.updateTimeElapsed();
		String timeString = Integer.toString(GameStats.timeElapsed());
		float timeXPos = (((Gdx.graphics.getWidth() * 0.8f) - font.getBounds(timeString).width) / 2) / Gdx.graphics.getWidth();
		Graphics.write(timeString, timeXPos, 0.98f);
		
		//Draw Score
		String scoreString = Integer.toString(GameStats.getScore());
		float scoreXPos = ((Gdx.graphics.getWidth() * 0.99f) - font.getBounds(scoreString).width) / Gdx.graphics.getWidth();
		Graphics.write(scoreString, scoreXPos, 0.825f);
		
		//Draw Mine Button with Count
		if(stats.mineReady())
		{
			mine.setRegion(0,0,124,95);
			Graphics.draw(Graphics.TYPES.EXTRAS, mineCount, 0.935f, 0.62f, 0.04f, 0.06f);
		}
		else
		{
			mine.setRegion(124,0,124,95);
			Graphics.draw(Graphics.TYPES.EXTRAS, mineCount, 0.935f, 0.60f, 0.04f, 0.06f);
		}
		mineCount.setRegion(39*(3-stats.getMineCount()),0,39,38);
		
		//Draw Vortex Button with Count
		if(stats.vortexReady())
		{
			vortex.setRegion(0,0,124,95);
			Graphics.draw(Graphics.TYPES.EXTRAS, vortexCount, 0.935f, 0.40f, 0.04f, 0.06f);
		}
		else
		{
			vortex.setRegion(124,0,124,95);
			Graphics.draw(Graphics.TYPES.EXTRAS, vortexCount, 0.935f, 0.38f, 0.04f, 0.06f);
		}
		vortexCount.setRegion(39*(3-stats.getVortCount()),0,39,38);
		
		//Update Nuke Button
		if(stats.nukeReady())
			nuke.setRegion(0,0,134,105);
		else
			nuke.setRegion(135,0,134,105);
		
		//Update NukeCount
		if(stats.nukeCount() > 0)
			nukeCount1.setRegion(0,0,93,64);
		else
			nukeCount1.setRegion(94,0,93,64);
		if(stats.nukeCount() > 1)
			nukeCount2.setRegion(0,0,93,64);
		else
			nukeCount2.setRegion(94,0,93,64);
		if(stats.nukeCount() > 2)
			nukeCount3.setRegion(0,0,93,64);
		else
			nukeCount3.setRegion(94,0,93,64);
		
		//Update Pause
		if (!stats.pauseState())
			pause.setRegion(0,0,57,57);
		else
			pause.setRegion(58,0,57,57);
	}
}