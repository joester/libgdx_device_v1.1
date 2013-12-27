package game;

import java.util.HashMap;

import game.UI.UI;
import game.controls.Controller;
import game.draw.GraphicsManager;
import game.objects.Device;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.enemy.MonsterManager;
import game.objects.spawner.CustomSpawner;
import game.room.Room;
import menu.BaseState;
import menu.StateManager;
import sounds.SoundSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;




import dev.DesignHelper;
//import dev.DesignHelper;
import dev.Manager;

public class TheDevice extends BaseState
{
	static final String LEVELPATH = "./data/leveldata/levels/Level1.devicelevel";//Temporary until we make a way for the game to choose the map to use
	
	public StateManager state;
	GraphicsManager graphics;
	UI gameUI;
	Player player;
	Manager manager;
	DesignHelper helper;
	
	public TheDevice(StateManager state, GraphicsManager g, SoundSystem sound, Manager manager) {
		super(state, sound, manager);
		this.graphics = g;
		this.state = state;
		this.manager = manager;
		// TODO Auto-generated constructor stub
	}
	
	/* Game */
	private GameObject box;
	private CustomSpawner spawner;
	
	public Room room;
	private GameStats g;
	private int spawnsToUse = 1;
	
	//Controller
	Controller controller;
	
	@Override
	public void create()
	{
		
		
		manager.loadArtAssets("Game");
		
		HashMap<String, Texture> asset = new HashMap<String, Texture>();
		
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
		
		spawner = new CustomSpawner(LEVELPATH,box,graphics,state.sounds,room);//temporary path
		
		//Spawn Management for dev tool
		helper = new DesignHelper(new MonsterManager(box, this.graphics, state.sounds, this.room), this.room, box, g, gameUI);
		
		
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
				//helper.dispose();
				state.endGame(g);
				return;
			}
			
			this.controller.isPause = false;
			//boolean gameIsOver = this.room.update(dt);
			g.updateTimeElapsed(dt);
			
			spawner.update(dt);
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
