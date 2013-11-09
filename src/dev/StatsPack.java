package dev;

import java.util.ArrayList;

public class StatsPack {
	
	ArrayList<StatsObject> objs = new ArrayList<StatsObject>();
	
	public StatsPack(){
		
	}
	
	public void addObj(StatsObject obj){
		objs.add(obj);
	}
}
