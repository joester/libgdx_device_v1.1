package thedevice_spawneditor;

import java.util.ArrayList;
import java.awt.Color;

public class Gui{
    static ArrayList<GuiObject> guiObjects;
    public static void initialize(){
	guiObjects = new ArrayList<GuiObject>();
	initialize_SpawnLocationCreator();
	initialize_SpawnLocationPlacer();
	initialize_FileIO();
    }
    
    /////Register/Remove
    public static void registerGuiObject_First(GuiObject obj){
	if(guiObjects.contains(obj))
	    guiObjects.remove(obj);
	guiObjects.add(0,obj);
    }
    public static void registerGuiObject(GuiObject obj){
	if(!guiObjects.contains(obj))
	    guiObjects.add(obj);
    }
    public static void removeGuiObject(GuiObject obj){
	if(guiObjects.contains(obj))
	    guiObjects.remove(obj);
    }
    
    /////Update/Draw
    public static void update(){
	for(Object obj : guiObjects.toArray())
	    ((GuiObject)obj).update();
    }
    
    public static void draw(){
	draw_SpawnLocationPlacer();
	draw_SpawnLocationCreator();
	draw_FileIO();
	for(GuiObject obj : guiObjects)
	    obj.draw();
    }
    
    //////////SpawnLocation Creator
    static final Vector2 SLC_OFFSET = new Vector2(000,000);
    static final Vector2 SLC_SIZE = new Vector2(791,100);
    
    static CenteredText Text_Spawn;
    static CenteredText Text_At;
    static CenteredText Text_And;
    
    static EnemyVariator ev;
    static SecondsVariator sv;
    static MillisecondsVariator msv;
    public static void initialize_SpawnLocationCreator(){
	ev = new EnemyVariator();
	ev.setPosition(new Vector2(200,0));
	ev.register();
	sv = new SecondsVariator();
	sv.setPosition(new Vector2(479,0));
	sv.register();
	msv = new MillisecondsVariator(sv);
	msv.setPosition(new Vector2(691,0));
	msv.register();
	
	GraphicsDraw.biggishFont();
	Text_Spawn = new CenteredText("Spawn one:",new Vector2(SLC_OFFSET.x+73,SLC_OFFSET.y+SLC_SIZE.y/2));
	Text_At = new CenteredText("at",new Vector2(SLC_OFFSET.x+406,SLC_OFFSET.y+SLC_SIZE.y/2));
	Text_And = new CenteredText("and",new Vector2(SLC_OFFSET.x+610,SLC_OFFSET.y+SLC_SIZE.y/2));
    }
    public static void draw_SpawnLocationCreator(){
	GraphicsDraw.biggishFont();
	GraphicsDraw.setColor(Color.BLACK);
	GraphicsDraw.rectangle(SLC_OFFSET.add(SLC_SIZE.div(2)),SLC_SIZE);
	GraphicsDraw.centerText(Text_Spawn);
	GraphicsDraw.centerText(Text_At);
	GraphicsDraw.centerText(Text_And);
    }
    
    public static SpawnType SpawnLocationCreator_getDesiredSpawnType(){
	return SpawnType.values()[ev.value];
    }
    public static float SpawnLocationCreator_getDesiredSpawnTime(){
	return sv.value+((float)msv.value/msv.INCREMENTS);
    }
    
    /////////////////////////SpawnLocation Placer
    static SpawnList spawnList;
    public static void initialize_SpawnLocationPlacer(){
	new MapScaleVariator().register();
	FileManager.newMap();
	new SpawnList().register();
	new SpawnMap_ToggleShowColors().register();
	new SpawnMap_ToggleOnlySelected().register();
	new SpawnMap_ToggleShowNumbers().register();
	new SpawnMap_ToggleShowMouseDistance().register();
	new SpawnMap_RecenterButton().register();
	new SnapTypeVariator().register();
    }
    
    public static void draw_SpawnLocationPlacer(){
	if(SpawnMap.currentMap != null){
	    SpawnMap.currentMap.update();
	    SpawnMap.currentMap.draw();
	}
    }
    
    /////////////////////////File I/O
    public static void initialize_FileIO(){
	FileManager.initialize();
    }
    public static void draw_FileIO(){
	FileManager.draw();
    }
}
