package game.objects.spawner;

import game.draw.GraphicsManager;
import game.objects.enemy.Enemy;
import game.room.Room;

public class Formation {
		
	GraphicsManager g = new GraphicsManager();
	Enemy[] enemies;
	boolean isVert, isMaxSize;
	float spawnDT;
	Room r;
	float width = 100;
	float height = 320 * 100 / 557;
	
	public Formation(int num, boolean isVert, boolean isMaxSize, float dt, Room r){
		this.enemies = new Enemy[num];
		this.isVert = isVert;
		this.isMaxSize = isMaxSize;
		this.spawnDT = dt;
		this.r = r;
	}
	
	
}
