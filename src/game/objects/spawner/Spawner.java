package game.objects.spawner;

import sounds.SoundSystem;
import game.controls.GameTimer;
import game.draw.GraphicsManager;
import game.objects.GameObject;
import game.room.Room;

public class Spawner
{
	private Room room;
	private FormationSpawner spawner;
	
	/* Interval */
	private GameTimer interval;
	private float interval_max;
	private float interval_min;
	private float interval_decr;
	private float max = 1;
	
	private float timeElapsed;
	
	/* Constructor */
	public Spawner(Room room, GameObject box, float interval, float interval_min, float interval_decr, GraphicsManager g, int ID, SoundSystem s)
	{
		this.room = room;
		this.interval = new GameTimer(interval);
		this.interval_max = interval;
		this.interval_min = interval_min;
		this.interval_decr = interval_decr;
		this.spawner = new FormationSpawner(room, box, g, ID, s);
		
	}//END Spawner
	
	/* Interval */
	public float get_interval()
	{
		return this.interval.get_interval();
	}//END get_interval	
	
	/* Spawn */
	private void spawn()
	{	
		spawner.addFormation();
	}//END spawn
	
	public boolean isDone(){
		if(timeElapsed > 5){
			timeElapsed = 0;
			return true;
		}
		return false;
	}
	
	/* Max */
	public void incMax(float m)
	{
		this.max += m;
	}
	
	/* Update */
	public void update(float dt)
	{
		if(room.monsterCount < max){
			spawner.update(dt);
		}
		
		this.interval.update_timer(dt);	
		if(this.interval.isDone())
		{
			this.interval.set_interval(this.interval.get_interval() - this.interval_decr);
			if(this.interval.get_interval() < this.interval_min)
			{
				this.interval.set_interval(this.interval_max);
			}//fi
			
			this.spawn();
			
			this.interval.reset_timer();
		}//fi
		timeElapsed += dt;	
	}//END update
}//END class Spawner
