package game.objects.spawner;

import java.util.Random;

import game.objects.enemy.MonsterManager;
import game.room.Room;

public class RandomSpawn extends Formation{
	
	Random rand = new Random();
	MonsterManager m;
	int ID;
	
	public RandomSpawn(int num, boolean isVert, boolean isMaxSize, float dt, Room r,
			MonsterManager m, int ID){
		super(num, isVert, isMaxSize, dt, r);
		this.m = m;
		this.ID = ID;
		set();
	}
	
	private void set(){
		for(int i = 0; i < enemies.length; i ++){
			boolean b = rand.nextBoolean();
			boolean isMax = rand.nextBoolean();
			int multi = (isMax ? -1 : 1);
			if(b){
				enemies[i] = m.spawnMonster(ID, (isMax ? 0 : 1) * width + (5 * multi), rand.nextFloat() * height);
			}
			else{
				enemies[i] = m.spawnMonster(ID, rand.nextFloat() * width, (isMax ? 0 : 1) * height + (5 * multi));
			}
		}
	}
}
