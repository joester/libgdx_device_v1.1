package menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OptionsContainer {
	
	File f = new File("data/options.txt");
	HashMap<String, Boolean> optionsBool = new HashMap<String, Boolean>();
	HashMap<String, Integer> optionsInt = new HashMap<String, Integer>();
	HashMap<String, Set<String>> assoc = new HashMap<String, Set<String>>();
	
	public OptionsContainer() {
		
		try {
			f.createNewFile();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assoc.put("bool", new HashSet<String>());
		assoc.put("int", new HashSet<String>());
		for(;;){
			try{
				String str = reader.readLine();
				String key = "", value = "";
				for(int i = 0; i < str.length(); i ++){
					if(str.charAt(i) == '='){
						value = str.substring(i + 1);
						break;
					}
					key += str.charAt(i);
				}
				if(value.equals("true") || value.equals("false")){
					assoc.get("bool").add(key);
					if(value.equals("true")){
						optionsBool.put(key, true);
					}
					else{
						optionsBool.put(key, false);
					}
				}
				if(value.charAt(0) <= '9' && value.charAt(0) >= '0'){
					assoc.get("int").add(key);
					optionsInt.put(key, Integer.parseInt(value));
				}
				
			}
			catch(Exception e){
				break;
			}
			
		}
	}
	
	public void close() throws IOException{
		File f2 = new File("data/options.txt");
		FileWriter fw = new FileWriter(f2);
		for(String key : optionsBool.keySet()){
			fw.write(key + "=" + optionsBool.get(key) + "\n");
		}
		for(String key : optionsInt.keySet()){
			fw.write(key + "=" + optionsInt.get(key) + "\n");
		}
		fw.close();
	}
}

