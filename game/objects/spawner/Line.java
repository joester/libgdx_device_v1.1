package game.objects.spawner;

import game.objects.enemy.MonsterManager;
import game.room.Room;

public class Line extends Formation{
	
	MonsterManager m;
	int ID;
	
	public Line(int num, boolean isVert, boolean isMaxSize, float dt, Room r, MonsterManager m, int ID){
		super(num, isVert, isMaxSize, dt, r);
		this.m = m;
		this.ID = ID;
		set();
	}
	
	private void set(){
		int multi = (isMaxSize ? 1 : 0);
		if(isVert){
			for(int i = 0; i < enemies.length; i ++){
			enemies[i] = m.spawnMonster(ID, 
				width * multi,
				i * height / (enemies.length + 1));
			}
		}
		else{
			for(int i = 0; i < enemies.length; i ++){
				enemies[i] = m.spawnMonster(ID, 
						i * width / (enemies.length + 1),
						height * multi);
			}
		}
	}
}
