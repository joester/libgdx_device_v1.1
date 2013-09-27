package game.objects.spawner;

import game.draw.GraphicsManager;
import game.objects.GameObject;
import game.objects.enemy.Enemy;
import game.objects.enemy.MonsterManager;
import game.room.Room;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import sounds.SoundSystem;

public class FormationSpawner{
	
	Queue<Formation> f;
	Formation nextToSpawn;
	float timeElapsed;
	Room r;
	Random generator;
	private MonsterManager manager;
	private int ID;
	
	public FormationSpawner(Room r, GameObject device, GraphicsManager g, int ID, SoundSystem s){
		this.f = new LinkedList<Formation>();
		this.r = r;
		this.generator = new Random();
		this.manager = new MonsterManager(device, g, s, r);
		this.ID = ID;
	}
	
	public void addFormation(Formation formation){
		f.add(formation);
	}
	
	public void addFormation(){
		int choice = generator.nextInt(3);
		
		boolean isMaxSize = generator.nextBoolean();
		boolean isVert = generator.nextBoolean();
		if(ID == 1){
			switch(choice){
			case 0:
				addFormation(new RandomSpawn(1, isMaxSize, isVert, 1, r, manager, ID));
				break;
			case 1:
				addFormation(new Line(3, isMaxSize, isVert, 1, r, manager, ID));
				break;
			case 2:
				addFormation(new Column(3, isMaxSize, isVert, 1, r, 2, manager, ID));
				break;
			}
		}
		else{
			addFormation(new RandomSpawn(1, isMaxSize, isVert, 1, r, manager, ID));
		}
	}
	
	public void update(float dt){	
		if(f.isEmpty()){
			return;
		}
		if(nextToSpawn == null){
			nextToSpawn = f.remove();
		}
		if(timeElapsed >= nextToSpawn.spawnDT){
			spawn();
		}
		timeElapsed += dt;		
	}
	
	public void spawn(){
		for(Enemy e : nextToSpawn.enemies){
			r.spawn_object(e);
		}
		timeElapsed = 0;
		nextToSpawn = f.remove();
	}
}
