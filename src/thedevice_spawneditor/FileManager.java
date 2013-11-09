package thedevice_spawneditor;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

public class FileManager{
	static final byte FILEVERSION = 10;
	static final String DEFAULTFOLDER = "./data/editorsettings/spawnmaps";
    static final String FILENAMEEXTENSION = ".spawnmap";
    static boolean saving;
    static boolean loading;
    static String fileName;
	    
    public static void initialize(){
	new FileManager_SaveButton().register();
	new FileManager_SaveAsButton().register();
	new FileManager_LoadButton().register();
	new FileManager_NewButton().register();
	newMap();
    }
    public static void draw(){
    }
    
    public static void newMap(){
	SpawnMap.setCurrentSpawnMap(new SpawnMap());
	fileName = null;
	TheDevice_SpawnEditor.changeTitle(fileName);
    }
    
    static String chooseFileName(boolean savingnotloading){
		Center.resetLeftMouse();
		
		JFileChooser chooser = new JFileChooser(DEFAULTFOLDER);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("The Device Spawnpoint Map (*"+FILENAMEEXTENSION+")","spawnmap");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle(savingnotloading ? "Save As" : "Open");
		chooser.setDialogType(savingnotloading ? JFileChooser.SAVE_DIALOG : JFileChooser.OPEN_DIALOG);
		int returnval = chooser.showOpenDialog(Center.instance);
		if(returnval == 0){
		    return chooser.getSelectedFile().getPath();
		}
		return null;
    }
    
    public static void saveMap(){
		saving = true;
		String s = chooseFileName(true);
		if(s != null){
		    int i2 = s.length()-FILENAMEEXTENSION.length();
		    if(!s.substring(i2).equals(FILENAMEEXTENSION))
			s += FILENAMEEXTENSION;
		    saveMapToFile(s);
		}else
		    SpawnMap.showMessage("Save cancelled.");
		saving = false;
    }
    
    public static void saveMap_Default(){
	if(fileName == null)
	    saveMap();
	else
	    saveMapToFile(fileName);
    }
    
    static void saveMapToFile(String filename){
	SpawnMap.showMessage("Saving...");
	DataOutputStream stream = null;
	try{
	    stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
	    stream.write(FILEVERSION);
	    ArrayList<SpawnLocation> list = SpawnMap.currentMap.spawns;
	    for(int i = 0;i < list.size();i++){
			SpawnLocation location = list.get(i);
			stream.writeByte(location.type.ordinal());
			stream.writeFloat(location.time);
			stream.writeFloat(location.position.x);
			stream.writeFloat(location.position.y);
	    }
	    stream.flush();
	    SpawnMap.showMessage("Map saved.");
	    fileName = filename;
	    TheDevice_SpawnEditor.changeTitle(fileName);
	}catch(Exception e){
	    System.out.println("MAP SAVING THREW EXCEPTION: "+e);
	    SpawnMap.showMessage("Save failed!!");
	}finally{
	    try{
		stream.close();
	    }catch(Exception _){
		System.out.println("This doesn't even make sense");
	    }
	}
    }
    
    static void loadMap(){
	loading = true;
	String s = chooseFileName(false);
	if(s != null)
	    loadMapFromFile(s);
	else
	    SpawnMap.showMessage("Load cancelled.");
	loading = false;
    }
    
    static void loadData(DataInputStream stream) throws java.io.EOFException,Exception{
	    SpawnType[] spawntypes = SpawnType.values();
	    for(int i = 0; i < 9999; i++){
			SpawnType type = spawntypes[stream.readByte()];
			float time = stream.readFloat();
			float xpos = stream.readFloat();
			float ypos = stream.readFloat();
			SpawnMap.currentMap.addSpawnLocation(new SpawnLocation(new Vector2(xpos,ypos),type,time));
	    }
    }
    static void loadMapFromFile(String filename){
		SpawnMap.showMessage("Loading...");
		newMap();
		DataInputStream stream = null;
		boolean success = true;
		try{
		    stream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
		    byte version = stream.readByte(); 
		    switch(version){
		    	case 10:
		    		loadData(stream);
		    		break;
		    	default://For backwards compatibility; the first byte in older versions (corresponding to the SpawnType) should be less than 10; my mistake for not adding a version number before
					SpawnType itype = SpawnType.values()[version];//The "version number" byte in older versions is actually the SpawnType byte
					float itime = stream.readFloat();
					float ixpos = stream.readFloat();
					float iypos = stream.readFloat();
					SpawnMap.currentMap.addSpawnLocation(new SpawnLocation(new Vector2(ixpos,iypos),itype,itime));
					loadData(stream);
					break;
		    }
		}catch(java.io.EOFException _){
		    //Completed sucessfully
		    success = true;
		}catch(Exception e){
		    System.out.println("MAP LOADING THREW EXCEPTION: "+e);
		    SpawnMap.showMessage("Map loaded.");
		    success = false;
		}finally{
		    if(success){
			SpawnMap.showMessage("Map loaded.");
			fileName = filename;
			TheDevice_SpawnEditor.changeTitle(fileName);
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
