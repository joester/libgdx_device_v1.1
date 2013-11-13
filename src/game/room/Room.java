
package game.room;


import game.GameStats;
import game.controls.Controllable;
import game.draw.Animator;
import game.draw.GraphicIndicators;
import game.draw.GraphicsManager;
import game.objects.Device;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.ShockWave;
import game.objects.items.Mine;
import game.objects.items.VortexIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import sounds.SoundSystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.Manager;

public class Room implements Controllable
{
	/* Background */
	private Sprite background;
	
	private Sprite deathRing;
	
	/* Objects */
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private HashSet<GameObject> spawn = new HashSet<GameObject>();
	private ArrayList<GameObject> drops = new ArrayList<GameObject>();
	private Player player;
	private GameObject device;
	public int monsterCount;
	public GameStats stats;
	private SoundSystem sound;
	private GraphicsManager graphics;
	
	/* Drop Chance */
	private float mineChance = 0.3f;
	private float vortexChance = 0.2f;
	
	/* Health */
	private Sprite HPBar = new Sprite(new Texture("data/UI/HPBar.png"));
	private Sprite HPFill = new Sprite(new Texture("data/UI/HPFill.png"));
	
	/* Level Up */
	private Sprite plevel = new Sprite(new Texture("data/UI/pLevel.png"));
	private Sprite mlevel = new Sprite(new Texture("data/UI/mLevel.png"));
	
	/* Indicators */
	private GraphicIndicators indicators;
	
	/* Monster Warning */
	private Sprite warningS;
	private Animator warning;
	
	/* Wipe */
	private boolean isWiping = false;
	private float wipe = 0;
	private float wipeFade = 0;
	
	/*Texture placeholders for items*/
	private Texture mineTex;
	private Texture vortTex;
	private Texture vortKill;
	
	private float quickDT = 0;
	
	//Condition to toggle monster spawns.
	private boolean canSpawn = true;
	HashMap<String, Texture> assets;
	
	/* Constructor */
	public Room(GraphicsManager g, Player player, GameStats stats, SoundSystem sound, HashMap<String, Texture> assets)
	{
		this.assets = assets;
		this.background = new Sprite(assets.get("game_bg"));
		this.deathRing = new Sprite(assets.get("deathRing"));
		this.player = player;
		this.stats = stats;
		this.indicators = new GraphicIndicators(assets.get("indicate"), player);
		this.sound = sound;
		this.graphics = g;
		
		warningS = new Sprite(assets.get("ui_warn"));
		warning = new Animator("warning",warningS,62,62);
		warning.add_animation("warning", 0, 0, 4, 5, true);
		warning.set_animation("warning", true);
		
		mineTex = assets.get("mine_drop");
		vortTex = assets.get("vortex_drop");
		vortKill = assets.get("vortex");
	}//END Room
	
	/* Object Management */
	/**
	 * Adds an object to the Room's list of objects.
	 * 
	 * @param obj the object to add
	 */
	public void add_object(GameObject obj)
	{
		this.objects.add(obj);
		if(obj.getID() == 1)
		{
			this.indicators.initialize_device(obj);
			this.device = obj;
		}//fi
	}//END add_object
	
	
	public void addDrop(GameObject obj){
		this.drops.add(obj);
	}
	/**
	 * Adds an object to the Room's list of objects to spawn.
	 * 
	 * @param obj the object to add
	 */
	public void spawn_object(GameObject obj)
	{
		if(canSpawn && obj.getID() == 3){			
			monsterCount ++;	
			this.spawn.add(obj);
		}
		else if(obj.getID() != 3){
			this.spawn.add(obj);
		}
					
	}//END spawn_object
	
	//Specialized method to spawn monsters regardless of dev settings. Used for designer testing.
	public void test_spawn_obj(GameObject obj){
		this.spawn.add(obj);
		if(obj.getID() == 3){
			monsterCount ++;
		}
	}
	
	/* Input */
	@Override
	public void input_touchDown(float x, float y, int pointer, int button)
	{
		if(x > 85){
			return;
		}
		
		Iterator<GameObject> iter = this.objects.iterator();
		
		GameObject target = null;
		while(iter.hasNext())
		{
			GameObject obj = iter.next();
			boolean touchedObject = obj.touch(x, y);
			if(touchedObject && (target == null || obj.get_position().dst(x,y) < target.get_position().dst(x,y)))
			{
				target = obj;
			}//fi
		}//elihw
		this.player.input_touchDown(x, y, pointer, button, target);
	}//END input_touch
	
	@Override
	public void input_touchUp(float x, float y, int pointer, int button)
	{
		this.player.input_touchUp(x, y, pointer, button);
	}//END input_touchUp
	
	@Override
	public void input_touchDrag(float x, float y, int pointer)
	{
		if(x > 85){
			return;
		}
		this.player.input_touchDrag(x, y, pointer);
	}//END input_touchUp
	
	/* Update */
	/**
	 * Updates all the objects in the room.
	 * 
	 * @param dt the difference in time between this and the last cycle
	 */
	public boolean update(float dt)
	{
		this.quickDT = dt;
		boolean gameIsLost = true;
		/* Spawn Objects */
		Iterator<GameObject> iter = this.spawn.iterator();
		while(iter.hasNext())
		{
			GameObject obj = iter.next();
			if(obj.getID() == 2)
			{
				gameIsLost = false;
				stats.setBoxHP(obj.getHp());
			}//fi
			
			this.objects.add(obj);
						
			iter.remove();
		}//elihw
		
		/* Update Indicators */
		this.indicators.update(dt);
		
		/* Update Objects */
		iter = this.objects.iterator();
		monsterCount = 0;
		while(iter.hasNext())
		{
			GameObject obj = iter.next();
			obj.update(dt, this.objects);
			obj.endUpdate();	
			if(obj.isGone())
			{
				if(obj.getID() == 2 || obj.getID() == 3)
				{
					stats.addScore(obj.worth);
					stats.addXP(obj.worth);
				}
				
				if(obj.getID() == 3)
				{
					if(stats.getLevel() > 2 && Math.random() < mineChance)
					{
						drops.add(new Mine(obj.get_positionX(), obj.get_positionY(), stats, sound, assets.get("mine_drop")));
					}
					if(stats.getLevel() > 5 && Math.random() < vortexChance)
					{
						drops.add(new VortexIcon(obj.get_positionX(), obj.get_positionY(), stats, sound, assets.get("vortex_drop")));
					}
					if(obj.getHp() == 0)
					{
						stats.addMonsterKill();
					}
				}
				iter.remove();
			}//fi	
			if(obj.getID() == 3){
				monsterCount ++;
			}
		}//elihw
		for(GameObject obj : drops){
			objects.add(obj);
		}
		drops = new ArrayList<GameObject>();
		
		if(this.wipe > 0)
		{
			this.wipe -= dt;
		}//fi
		else if(isWiping)
		{
			this.wipe_dmg();
			this.isWiping = false;
		}//fi esle
		else if(this.wipeFade > 0)
		{
			this.wipeFade -= dt;
		}//fi esle
		

		
		//Draw Warning Sign
		warning.update(dt);
		
		return gameIsLost;
	}//END Update
	
	/* Draw */
	public void render(SpriteBatch spritebatch, float[] renderInfo)
	{
		float current;
		float max;
		float drawWidth;
		float drawHeight;
		//Render Background
		this.background.setSize(renderInfo[2] * (100),
				renderInfo[2] * (62.5f));
		this.background.draw(spritebatch);
		
		//Render Indicator
		this.indicators.render(spritebatch, renderInfo);
		
		//Render Objects
		Collections.sort(this.objects);
		
		Iterator<GameObject> iter = this.objects.iterator();
		while(iter.hasNext())
		{
			GameObject obj = iter.next();
			float warningWidth = 5;
			float warningHeight = 5;
			float x = obj.get_positionX() - warningWidth/2;
			float y = obj.get_positionY() - warningHeight/2;
			
			
			//Draw Warning
			if(obj.getID() == 3)
			{
				if(obj.get_positionX() < 0)
				{
					x = 0;
					warningS.setOrigin(renderInfo[2] *warningWidth/2,renderInfo[2] * warningHeight/2);
					warningS.setSize(renderInfo[2] *warningWidth, renderInfo[2] *warningHeight);
					warningS.setPosition(renderInfo[2] *x, renderInfo[2] *y);
					warningS.draw(spritebatch);
				}
				if(obj.get_positionX() > renderInfo[0] * (1-0.15))
				{
					x = renderInfo[0] * (1-0.15f) - warningWidth;
					warningS.setOrigin(renderInfo[2] *warningWidth/2,renderInfo[2] * warningHeight/2);
					warningS.setSize(renderInfo[2] *warningWidth, renderInfo[2] *warningHeight);
					warningS.setPosition(renderInfo[2] *x, renderInfo[2] *y);
					warningS.draw(spritebatch);
				}
				if(obj.get_positionY() < 0)
				{
					y = 0;
					warningS.setOrigin(renderInfo[2] *warningWidth/2,renderInfo[2] * warningHeight/2);
					warningS.setSize(renderInfo[2] *warningWidth, renderInfo[2] *warningHeight);
					warningS.setPosition(renderInfo[2] *x, renderInfo[2] *y);
					warningS.draw(spritebatch);
				}
				if(obj.get_positionY() > renderInfo[1])
				{
					y = renderInfo[1] - warningHeight;
					warningS.setOrigin(renderInfo[2] *warningWidth/2,renderInfo[2] * warningHeight/2);
					warningS.setSize(renderInfo[2] *warningWidth, renderInfo[2] *warningHeight);
					warningS.setPosition(renderInfo[2] *x, renderInfo[2] *y);
					warningS.draw(spritebatch);
				}
			}

			
			//Draw objects
			obj.render(spritebatch, renderInfo);
			
			//Draw Healthbar
			current = obj.get_healthAttribute().current;
			max = obj.get_healthAttribute().max;
			drawWidth = 5;
			drawHeight = 1;

			if (current != max && current > 0)
				{
					HPFill.setOrigin((current/max) * renderInfo[2] * (drawWidth/2),
							renderInfo[2] * (drawHeight/2));
					HPFill.setSize((current/max) * renderInfo[2] * (drawWidth),
							10);
					HPFill.setPosition(renderInfo[2] * (obj.get_positionX() - drawWidth/2),
							renderInfo[2] * (obj.get_positionY() - drawHeight/2  + obj.getSpriteHeight()));
					HPFill.draw(spritebatch);
					
					HPBar.setOrigin(renderInfo[2] * (drawWidth/2),
							renderInfo[2] * (drawHeight/2));
					HPBar.setSize(renderInfo[2] * (drawWidth),
							renderInfo[2] * (drawHeight));
					HPBar.setPosition(renderInfo[2] * (obj.get_positionX() - drawWidth/2),
							renderInfo[2] * (obj.get_positionY() - drawHeight/2  + obj.getSpriteHeight()));
					HPBar.draw(spritebatch);
					
				}
			//Draw Level Up animation
			if (obj.levelUp > 0)
			{
				Sprite lvlup = plevel;
				if(obj.getID() == 3)
				{
					lvlup = mlevel;
				}
				drawWidth = 4 * (4-obj.levelUp);
				drawHeight = 0.88f * (3-obj.levelUp);
				lvlup.setOrigin(renderInfo[2] * (drawWidth/2),
						renderInfo[2] * (drawHeight/2));
				lvlup.setSize(renderInfo[2] * (drawWidth),
						renderInfo[2] * (drawHeight));
				lvlup.setPosition(renderInfo[2] * (obj.get_positionX() - drawWidth/2),
						renderInfo[2] * (obj.get_positionY() - drawHeight/2  + obj.getSpriteHeight()));
				lvlup.draw(spritebatch);
				obj.levelUp -= this.quickDT;
			}
		}//elihw
		
		drawWidth = 300-300*this.wipe;
		drawHeight = 300-300*this.wipe;
		if(this.isWiping || this.wipeFade > 0)
		{
			this.deathRing.setOrigin(renderInfo[2] * (drawWidth/2),
					renderInfo[2] * (drawHeight/2));
			this.deathRing.setSize(renderInfo[2] * (drawWidth),
					renderInfo[2] * (drawHeight));
			this.deathRing.setPosition(renderInfo[2] * (this.device.get_positionX() - drawWidth/2),
					renderInfo[2] * (this.device.get_positionY() - drawHeight/2));
			this.deathRing.draw(spritebatch, 2*this.wipeFade);
		}//fi
	}//END render
	
	/* Wipe */
	public void wipe()
	{
		this.wipe = 1;
		this.wipeFade = 0.5f;
		this.isWiping = true;
		((Device)(this.device)).inv();
		sound.playSound(SoundSystem.nuke);
	}//END wipe
	
	private void wipe_dmg()
	{
		((Device)(this.device)).disInv();
		Iterator<GameObject> iter = this.objects.iterator();
		while(iter.hasNext())
		{
			GameObject obj = iter.next();
			if(obj.getID() == 3 || obj.getID() == 2)
			{
				obj.setHp(0);
				obj.terminate();
				obj.worth = 0;
			}//fi
		}//elihw
	}//END wipe
	
	public void addShock(GameObject obj){
		this.spawn_object(new ShockWave(obj.get_positionX(), obj.get_positionY(), assets.get("shock_wave"), sound));
	}
	
	public void toggleSpawn(){
		canSpawn = !canSpawn;
	}
	
	public Texture getAsset(String str){
		return assets.get(str);
	}	
}