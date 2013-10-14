package game.objects.spawner;

import sounds.SoundSystem;

import com.badlogic.gdx.math.Vector2;
import game.draw.GraphicsManager;
import game.objects.GameObject;
import game.objects.enemy.MonsterManager;
import game.room.Room;

import java.io.*;
import java.util.LinkedList;

import game.objects.enemy.Enemy;

import java.util.Random;

public class CustomSpawner{
	//////////Constants
	static final boolean APPLYRANDOMDIRECTIONTOWAVES = true;//makes each wave of enemies appear in a random direction while keeping their formation
	static final boolean LOOPWHENFINISHED = true;//Will start over if it is finished
	static final float LOOPDELAY = 3;//Delay when looping
	/////The following two constants are temporary until we figure out a way to get the map size.
	/////They should depend on the size of the field.
	static final Vector2 center = new Vector2(45,30);//The center of the map
	static final float baseDistance = 27;//The distance from the center to the corner; the basis of the SpawnLocation position system
	//////////END Constants
	
	GameObject gameObject;
	GraphicsManager graphicsManager;
	SoundSystem soundSystem;
	Room room;
	
	MonsterManager monsterManager; 
	
	LinkedList<SpawnLocation> spawnLocations;
	private float time;
	
	private Random rand;
	private VectorMath vmath;
	
	private String mapPath;
	
	public CustomSpawner(String mappath,GameObject gameObject,GraphicsManager graphicsManager,SoundSystem soundSystem,Room room){
		mapPath = mappath;
		this.gameObject = gameObject;
		this.graphicsManager = graphicsManager;
		this.soundSystem = soundSystem;
		this.room = room;
		spawnLocations = new LinkedList<SpawnLocation>();

		this.monsterManager = new MonsterManager(gameObject, graphicsManager, soundSystem, room);
		
		rand = new Random();
		vmath = new VectorMath();
		
		reset();
	}

    private void loadData(DataInputStream stream) throws java.io.EOFException,Exception{
	    for(int i = 0; i < 9999; i++){
			int type = stream.readByte();
			float time = stream.readFloat();
			float xpos = stream.readFloat();
			float ypos = stream.readFloat();
			spawnLocations.add(new SpawnLocation(new Vector2(xpos,ypos),type,time));
	    }
    }
    
    public void reset(){
    	System.out.println("GL HF!");
    	time = 0;
		loadMapFromFile(mapPath);
    }
    
    public void loadMapFromFile(String filename){
		DataInputStream stream = null;
		try{
		    stream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
		    byte version = stream.readByte(); 
		    switch(version){
		    	case 10:
		    		loadData(stream);
		    		break;
		    	default://For backwards compatibility; the first byte in older versions (corresponding to the SpawnType) should be less than 10; my mistake for not adding a version number before
					int itype = version;//The "version number" byte in older versions is actually the SpawnType byte
					float itime = stream.readFloat();
					float ixpos = stream.readFloat();
					float iypos = stream.readFloat();
					spawnLocations.add(new SpawnLocation(new Vector2(ixpos,iypos),itype,itime));
					loadData(stream);
					break;
		    }
		}catch(java.io.EOFException _){
		    //Completed sucessfully
		}catch(Exception e){
			System.out.println("Map loading threw exception: "+e);
			System.out.println("Check if the file "+filename+" is missing. If it isn't, please inform Masana.");
		}finally{
		    try{
		    	stream.close();
		    }catch(Exception e){
		    	System.out.println("Well this sucks.");
		    }
		}
    }
    
    public void spawnEnemy(SpawnLocation location,float relativerotation){
    	Vector2 pos = new Vector2(center.x+location.position.x*baseDistance,center.y-location.position.y*baseDistance);//Negative Y because GDX's system has a positive Y go upward rather than downward
    	pos = vmath.rotate(pos,relativerotation);
    	Enemy enemy = monsterManager.spawnMonster(location.type+1,pos.x,pos.y);
    	room.spawn_object(enemy);
    }

    private float waveDirection = 0;
	public void update(float dseconds){
		if((int)time != (int)(time += dseconds) & APPLYRANDOMDIRECTIONTOWAVES){//Will randomize the spawn direction every second
			waveDirection = (float)rand.nextDouble()*VectorMath.pi*2;
			System.out.println("waveDirection: "+(int)(waveDirection/VectorMath.pi/2*360)+" degrees");
		}

		SpawnLocation location;
		
		if(spawnLocations.isEmpty()){
			System.out.println("NO ENEMIES LEFT!");
			if(LOOPWHENFINISHED){
				System.out.println("Resetting spawner!");
				reset();
				time = -LOOPDELAY;
			}
		}else while(!spawnLocations.isEmpty()){
			location = spawnLocations.getFirst();
			if(location.time > time)
				break;
			spawnEnemy(location,waveDirection);
			spawnLocations.pollFirst();
		}
	}
}

class SpawnLocation{
    public Vector2 position;
    public int type;//spawn type
    public float time;
    
    public SpawnLocation(Vector2 pos,int typ,float tim){
		position = pos;
		type = typ;
		time = tim;
    }
}

class VectorMath{//Masana's Vector Math class
    static final float pi = (float)Math.PI;
    
    public float x,y;
    
    //Static values
    public Vector2 zero(){return new Vector2();}
    public Vector2 one(){return new Vector2(1,1);}
    
    //Methods
    public boolean equals(Vector2 v1,Vector2 v2){return distance(v1,v2) < .1f;}
    
    public float distance(Vector2 v1,Vector2 v2){return magnitude(sub(v1,v2));}
    public boolean distanceLessThan(Vector2 v1,Vector2 v2,float dist){//Optimized
    	return magnitude(new Vector2(v1.x-v2.x,v1.y-v2.y)) < dist;
    	/*
    	float x = v1.x-v2.x;
    	if(Math.abs(x) >= dist) return false;
    	float y = v2.y-v1.y;
    	return Math.abs(y) < dist && x*x+y*y < dist*dist;*/
    }
    	
    public float magnitude(Vector2 v){return (float)Math.sqrt(v.x*v.x+v.y*v.y);}
    
    //Use radians
    public float toAngle(Vector2 v){
    	float r = (float)Math.atan(v.y/v.x);
    	return v.x < 0 ? pi+r : r;
    }
    public float toAngle(Vector2 v1,Vector2 v2){
    	return toAngle(sub(v2,v1));
    }
    public Vector2 fromAngle(float angle){return new Vector2((float)Math.cos(angle),(float)Math.sin(angle));}
    public Vector2 fromAngle(float angle,float magnitude){return mul(fromAngle(angle),magnitude);}
    
    //Gets a Vector2 between two points
    public Vector2 lerp(Vector2 v1,Vector2 v2,float t){return add(v1,mul(sub(v2,v1),t));}
    
    //Normalize
    //Gives a vector that will have a length of one.
    //Example:
    //Vector2.normalize(new Vector2(0,10)).magnitude() -->1
    //Vector2.normalize(new Vector2(23409,234987)).magnitude() -->1
    public Vector2 normalize(Vector2 v){return fromAngle(toAngle(v));}
    public Vector2 normalize(Vector2 v,float magnitude){return mul(normalize(v),magnitude);}//Will multiply for you
    public Vector2 normalize(Vector2 v1,Vector2 v2){return normalize(sub(v2,v1));}
    
    //Rotates a vector
    public Vector2 rotate(Vector2 v,float angle){return mul(fromAngle(toAngle(v)+angle),magnitude(v));}
    
    //Arithmetic
    public Vector2 add(Vector2 v1,Vector2 v2){return new Vector2(v1.x+v2.x,v1.y+v2.y);}
    public Vector2 sub(Vector2 v1,Vector2 v2){return new Vector2(v1.x-v2.x,v1.y-v2.y);}
    public Vector2 mul(Vector2 v1,Vector2 v2){return new Vector2(v1.x*v2.x,v1.y*v2.y);}
    public Vector2 div(Vector2 v1,Vector2 v2){return new Vector2(v1.x/v2.x,v1.y/v2.y);}
    public Vector2 add(Vector2 v1,float f){return new Vector2(v1.x+f,v1.y+f);}
    public Vector2 sub(Vector2 v1,float f){return new Vector2(v1.x-f,v1.y-f);}
    public Vector2 mul(Vector2 v1,float f){return new Vector2(v1.x*f,v1.y*f);}
    public Vector2 div(Vector2 v1,float f){return new Vector2(v1.x/f,v1.y/f);}
    
    //Getting line collisions with horizontal/vertical lines
    public Vector2 getLineCollision_Horizontal(Vector2 p1,Vector2 p2,float y){
    	float disttoy = y-p1.y;
    	float slope = (p2.x-p1.x)/(p2.y-p1.y);
    	return new Vector2(p1.x+disttoy*slope,y);
    }
    public Vector2 getLineCollision_Vertical(Vector2 p1,Vector2 p2,float x){
    	float disttox = x-p1.x;
    	float slope = (p2.y-p1.y)/(p2.x-p1.x);
    	return new Vector2(x,p1.y+disttox*slope);
    }
    
    //Returns a vector with the minimum/maximum x and y from two vectors
    public Vector2 minVector(Vector2 v1,Vector2 v2){
    	return new Vector2(v1.x < v2.x ? v1.x : v2.x,v1.y < v2.y ? v1.y : v2.y);
    }
    public Vector2 maxVector(Vector2 v1,Vector2 v2){
    	return new Vector2(v1.x >= v2.x ? v1.x : v2.x,v1.y >= v2.y ? v1.y : v2.y);
    }
}
