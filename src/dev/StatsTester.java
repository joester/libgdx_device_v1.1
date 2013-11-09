package dev;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class StatsTester {
	public static void main(String args[]) throws IOException, ClassNotFoundException{
		new StatsManager();
		HashMap<String, StatsObject> stats = new HashMap<String, StatsObject>();
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream("stats.jcf"));
		ArrayList<StatsObject> objs;
		objs = (ArrayList<StatsObject>) stream.readObject();
		
		
		for(StatsObject obj : objs){
			System.out.println(obj.damage);
		}
		
		
		stream.close();
	}
}
