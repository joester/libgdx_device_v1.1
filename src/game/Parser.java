package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
	
	public Parser(){
		
	}	
	
	 public String[][] parseFile(File f){
		 Scanner scan = null;
		 try {
			 scan = new Scanner(f);
		 } catch (FileNotFoundException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 scan.useDelimiter("\\Z");
		 String str = scan.next();
		 String[] lines = str.split("\n");
		 String[][] values = new String[lines.length][lines[0].split(",").length];
		 for(int i = 0; i < values.length; i ++){
			 values[i] = lines[i].split(",");
		 }
		 scan.close();
		 return values;
	}
	
}
