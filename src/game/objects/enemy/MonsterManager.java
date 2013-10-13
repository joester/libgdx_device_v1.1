package game.objects.enemy;

import sounds.SoundSystem;
import game.draw.GraphicsManager;
import game.objects.GameObject;
import game.room.Room;

public class MonsterManager {
	
	GameObject d;
	GraphicsManager g;
	SoundSystem s;
	Room r;
	
	public MonsterManager(GameObject d, GraphicsManager g, SoundSystem sounds, Room room){
		this.d = d;
		this.g = g;
		this.s = sounds;
		this.r = room;
	}
	
	public Enemy spawnMonster(int ID, float xPos, float yPos){
		if(ID == 1){
			return new FuzzOne(d, xPos, yPos, r.getAsset("fuzz1"), r.getAsset("fuzz2"), r.getAsset("fuzz3"), s, r);
		}
		if(ID == 2){
			return new FuzzTwo(d, xPos, yPos, r.getAsset("fuzz2"), r.getAsset("fuzz3"), s, r);
		}
		if(ID == 3){
			return new FuzzThree(d, xPos, yPos, r.getAsset("fuzz3"), s, r );
		}
		return null;
	}
	
}
