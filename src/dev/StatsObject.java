package dev;

import java.util.HashMap;

public class StatsObject implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String packName;
	HashMap<String, Float> stats = new HashMap<String, Float>();
	float damage;
	float hp;
	float speed;
	float power;
	float range;
	
	//Takes in a string as the name of the stats set;
	public StatsObject(String statsPack, float damage, float hp, float speed, float power, float range)  {		
		packName = statsPack;
		stats.put("damage", damage);
		stats.put("hp",hp);
		stats.put("speed", speed);
		stats.put("power", power);
		stats.put("range", range);
	}
}
