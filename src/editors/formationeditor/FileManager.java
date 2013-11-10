package editors.formationeditor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import editors.shared.SpawnType;
import editors.shared.Vector2;
import editors.shared._G;

public class FileManager{
	static final byte FILEVERSION = 0;
	static final String DEFAULTFOLDER = _G.DATAFOLDER+"formations/";
    static final String FILENAMEEXTENSION = ".spawnformation";
    static boolean saving;
    static boolean loading;
    static String fileName;
	    
    public static void initialize(){
		new FileManager_SaveButton().register();
		new FileManager_SaveAsButton().register();
		new FileManager_LoadButton().register();
		new FileManager_NewButton().register();
		newFormation();
    }
    public static void draw(){
    }
    
    public static void newFormation(){
		SpawnMap.setCurrentSpawnMap(new SpawnMap());
		fileName = null;
		TheDevice_FormationEditor.changeTitle(fileName);
    }
    
    static String chooseFileName(boolean savingnotloading){
		Center.resetLeftMouse();
		
		JFileChooser chooser = new JFileChooser(DEFAULTFOLDER);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("The Device Formation Map (*"+FILENAMEEXTENSION+")","spawnformation");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle(savingnotloading ? "Save As" : "Open");
		chooser.setDialogType(savingnotloading ? JFileChooser.SAVE_DIALOG : JFileChooser.OPEN_DIALOG);
		int returnval = chooser.showOpenDialog(Center.instance);
		if(returnval == 0){
		    return chooser.getSelectedFile().getPath();
		}
		return null;
    }
    
    public static void saveFormation(){
		saving = true;
		String s = chooseFileName(true);
		if(s != null){
		    int i2 = s.length()-FILENAMEEXTENSION.length();
		    if(i2 < 0 || !s.substring(i2).equals(FILENAMEEXTENSION))
		    	s += FILENAMEEXTENSION;
		    saveFormationToFile(s);
		}else
		    SpawnMap.showMessage("Save cancelled.");
		saving = false;
    }
    
    public static void saveFormation_Default(){
		if(fileName == null)
		    saveFormation();
		else
		    saveFormationToFile(fileName);
    }
    
    static void saveFormationToFile(String filename){
		SpawnMap.showMessage("Saving...");
		DataOutputStream stream = null;
		try{
		    stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
		    stream.write(FILEVERSION);
		    ArrayList<EventLocation> list = SpawnMap.currentMap.spawns;
		    for(int i = 0;i < list.size();i++){
				EventLocation location = list.get(i);
				stream.writeByte(location.type.ordinal());
				stream.writeFloat(location.position.x);
				stream.writeFloat(location.position.y);
		    }
		    stream.flush();
		    SpawnMap.showMessage("Formation saved.");
		    fileName = filename;
		    TheDevice_FormationEditor.changeTitle(fileName);
		}catch(Exception e){
		    System.out.println("FORMATION SAVING THREW EXCEPTION: "+e);
		    SpawnMap.showMessage("Save failed!!");
		}finally{
		    try{
		    	stream.close();
		    }catch(Exception _){
		    	System.out.println("This doesn't even make sense");
		    }
		}
    }
    
    static void loadFormation(){
		loading = true;
		String s = chooseFileName(false);
		if(s != null)
		    loadFormationFromFile(s);
		else
		    SpawnMap.showMessage("Load cancelled.");
		loading = false;
    }
    
    public static void loadFormationFromFileName(String name){
    	newFormation();
    	loadFormationFromFile(DEFAULTFOLDER+name+FILENAMEEXTENSION);
    }
    
    static void loadFormationFromFile(String path){
		SpawnMap.showMessage("Loading...");
		newFormation();
		DataInputStream stream = null;
		boolean success = false;
		try{
		    stream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
		    byte version = stream.readByte();
		    int size;
		    SpawnType[] spawntypes = SpawnType.values();
		    for(int i = 0; i < 9999; i++){//The last iteration will throw an EOFException
				SpawnType type = spawntypes[stream.readByte()];
				float xpos = stream.readFloat();
				float ypos = stream.readFloat();
				SpawnMap.currentMap.addSpawnLocation(new EventLocation(new Vector2(xpos,ypos),type));
		    }
		}catch(java.io.EOFException _){
		    //Completed sucessfully
		    success = true;
		}catch(Exception e){
		    System.out.println("FORMATION LOADING THREW EXCEPTION: "+e);
		    success = false;
		}finally{
		    if(success){
				SpawnMap.showMessage("Formation loaded.");
				fileName = path;
				TheDevice_FormationEditor.changeTitle(fileName);
		    }else
		    	SpawnMap.showMessage("Load failed!!");
		    try{
		    	stream.close();
		    }catch(Exception e){
		    	System.out.println("Well this sucks.");
		    }
		}
    }
}
