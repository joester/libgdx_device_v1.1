package game.objects.spawner;

import game.objects.enemy.MonsterManager;
import game.room.Room;

public class Column extends Formation{
	
	int columnNum;
	MonsterManager m;
	int ID;
	
	public Column(int num, boolean isVert, boolean isMaxSize, float dt, Room r, int columnNum, 
			MonsterManager m, int ID){
		super(num, isVert, isMaxSize, dt, r);
		this.columnNum = columnNum;
		this.m = m;
		this.ID = ID;
		set();
	}
	
	private void set(){
		int multi = (isMaxSize ? 1 : 0);
		int displaceMult = (isMaxSize ? 1 : -1);
		if(isVert){
			for(int i = 0; i < enemies.length; i ++){
				enemies[i] = m.spawnMonster(ID, 
						(width * multi) + (displaceMult * 256 * i % columnNum + (displaceMult * 50)),
						(i % columnNum) * height / ((enemies.length % columnNum) + 1));
			}
			
		}
		else{
			for(int i = 0; i < enemies.length; i ++){
				enemies[i] = m.spawnMonster(ID, 
						(i % columnNum) * width / ((enemies.length % columnNum) + 1),
						(height * multi) + (displaceMult * 256 * i % columnNum + (displaceMult * 50)));			
			}
			
		}
	}
}
