<<<<<<< HEAD
package game;

import sounds.SoundSystem;
import menu.BaseState;
import menu.StateManager;
import game.UI.UI;
import game.controls.Controller;
import game.draw.GraphicsManager;
import game.objects.Device;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.enemy.MonsterManager;
import game.objects.spawner.Spawner;
import game.room.Room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.DesignHelper;
import dev.Manager;

public class TheDevice extends BaseState
{
	public StateManager state;
	GraphicsManager graphics;
	UI gameUI;
	Player player;
	Manager manager;
	
	public TheDevice(StateManager state, GraphicsManager g, SoundSystem sound, Manager manager) {
		super(state, sound, manager);
		this.graphics = g;
		this.state = state;
		this.manager = manager;
		// TODO Auto-generated constructor stub
	}
	
	/* Game */
	private GameObject box;
	private Spawner[] spawner = new Spawner[3];
	
	public Room room;
	private GameStats g;
	private int spawnsToUse = 1;
	
	//Controller
	Controller controller;
	
	@Override
	public void create()
	{
		
		
		manager.loadArtAssets("Game");
		
		assets = manager.getArtAssets("Game");
		
		/* Start Room */
		player = new Player(
				0, //ID
				50, 30, //Position
				1, //Mass
				100, //Friction
				5, 2, //Hitbox
				0, 0, //Hitoffset
				true, //Solid
				8, //Touch Radius
				false, //Touchability
				8, (16 + 2/3) * 0.8f, //Draw width and height
				assets.get("hero"), //Sprite
				150, 250,
				g,//Src width and height
				state.sounds
				);	
		
		//UI
		this.g = new GameStats(player, state.sounds);
		//Set XP Fill
		
		
		this.room = new Room(graphics, player, this.g, state.sounds, assets);
		
		box = new Device(25,25,assets.get("device"),assets.get("device_spawn"), room, assets.get("exp"), assets.get("device_hit"),state.sounds);
		this.room.add_object(this.box);
		this.room.add_object(player);
		
		this.spawner[0] = new Spawner(this.room, box, 2f, 1f, 2f, graphics, 1, state.sounds);
		this.spawner[1] = new Spawner(this.room, box, 2f, 1f, 2f, graphics, 1, state.sounds);
		this.spawner[2] = new Spawner(this.room, box, 2f, 1f, 2f, graphics, 1, state.sounds);
		
		//Spawn Management for dev tool
		new DesignHelper(new MonsterManager(box, this.graphics, state.sounds, this.room), this.room, box, g, gameUI);
		
		
		gameUI = new UI(g, graphics, this, this.room, state.sounds, state.renderInfo);
		
		/* Controls */
		this.controller = new Controller(state.renderInfo);
		this.controller.add_controllable(room);
		Gdx.input.setInputProcessor(this.controller);
		
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		manager.unloadArtAssets();
		
		//room.dispose();
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		if(this.gameUI.placeItem())
		{
			this.player.pause_touch();
		}//fi
		
		float dt = Gdx.graphics.getDeltaTime();
		if (!gameUI.update(dt))
		{
			
			if(box.getHp() <= 0){
				state.endGame(g);
				return;
			}
			
			this.controller.isPause = false;
			//boolean gameIsOver = this.room.update(dt);
			g.updateTimeElapsed(dt);
			
			for(int i = 0; i < spawnsToUse; i ++){
				if(spawner[i] != null){
					spawner[i].incMax(0.02f * dt);
					spawner[i].update(dt);
					if(i == spawnsToUse - 1 && spawner[i].isDone()){
						if(spawnsToUse < 3){
							spawnsToUse ++;
						}
					}
				}
			}
			if(gameUI.nukeState)
			{
				this.room.wipe();
				gameUI.nukeState = false;
			}
			this.room.update(dt);
		}
		else
		{
			this.controller.isPause = true;
			dt = 0;
			
		}
		
		//Draw	
		this.room.render(batch, state.renderInfo);
		gameUI.render(batch, dt);
	}
}
=======
package game;

import sounds.SoundSystem;
import menu.BaseState;
import menu.StateManager;
import game.UI.UI;
import game.controls.Controller;
import game.draw.GraphicsManager;
import game.objects.Device;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.enemy.MonsterManager;
import game.objects.spawner.Spawner;
import game.room.Room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.DesignHelper;

public class TheDevice extends BaseState
{
	public StateManager state;
	GraphicsManager graphics;
	UI gameUI;
	Player player;
	
	public TheDevice(StateManager state, GraphicsManager g, SoundSystem sound) {
		super(state, sound);
		this.graphics = g;
		this.state = state;
		// TODO Auto-generated constructor stub
	}
	
	/* Game */
	private GameObject box;
	private Spawner[] spawner = new Spawner[3];
	
	public Room room;
	private GameStats g;
	private int spawnsToUse = 1;
	
	//Controller
	Controller controller;
	
	
	
	@Override
	public void create()
	{
		/* Start Room */
		player = new Player(
				0, //ID
				50, 30, //Position
				1, //Mass
				100, //Friction
				5, 2, //Hitbox
				0, 0, //Hitoffset
				true, //Solid
				8, //Touch Radius
				false, //Touchability
				8, (16 + 2/3) * 0.8f, //Draw width and height
				graphics.ID(0), //Sprite
				150, 250,
				g,//Src width and height
				state.sounds
				);	
		
		//UI
		this.g = new GameStats(player, state.sounds);
		//Set XP Fill
		
		
		this.room = new Room(graphics, player, this.g, state.sounds);
		
		box = new Device(25,25,graphics.ID(1),graphics.ID(4), room, graphics.ID(2), graphics.ID(7),state.sounds);
		this.room.add_object(this.box);
		this.room.add_object(player);
		
		this.spawner[0] = new Spawner(this.room, box, 2f, 1f, 2f, graphics, 1, state.sounds);
		this.spawner[1] = new Spawner(this.room, box, 2f, 1f, 2f, graphics, 1, state.sounds);
		this.spawner[2] = new Spawner(this.room, box, 2f, 1f, 2f, graphics, 1, state.sounds);
		
		//Spawn Management for dev tool
		new DesignHelper(new MonsterManager(box, this.graphics, state.sounds, this.room), this.room, box, g, gameUI);
		
		
		gameUI = new UI(g, graphics, this, this.room, state.sounds, state.renderInfo);
		
		/* Controls */
		this.controller = new Controller(state.renderInfo);
		this.controller.add_controllable(room);
		Gdx.input.setInputProcessor(this.controller);
		
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		graphics.dispose();
		
		//room.dispose();
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		if(this.gameUI.placeItem())
		{
			this.player.pause_touch();
		}//fi
		
		float dt = Gdx.graphics.getDeltaTime();
		if (!gameUI.update(dt))
		{
			
			if(box.getHp() <= 0){
				state.endGame(g);
				return;
			}
			
			this.controller.isPause = false;
			//boolean gameIsOver = this.room.update(dt);
			g.updateTimeElapsed(dt);
			
			for(int i = 0; i < spawnsToUse; i ++){
				if(spawner[i] != null){
					spawner[i].incMax(0.02f * dt);
					spawner[i].update(dt);
					if(i == spawnsToUse - 1 && spawner[i].isDone()){
						if(spawnsToUse < 3){
							spawnsToUse ++;
						}
					}
				}
			}
			if(gameUI.nukeState)
			{
				this.room.wipe();
				gameUI.nukeState = false;
			}
			this.room.update(dt);
		}
		else
		{
			this.controller.isPause = true;
			dt = 0;
			
		}
		
		//Draw	
		this.room.render(batch, state.renderInfo);
		gameUI.render(batch, dt);
	}
}
>>>>>>> branch 'master' of https://github.com/EyeWumbo/libgdx_device_v1.1.git
