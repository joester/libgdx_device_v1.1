package thedevice_spawneditor;
import java.util.ArrayList;
import java.awt.Color;

enum MapSnapType{None,Ring,Grid}

public class SpawnMap extends GuiObject{
    //////////Constants
    public static final int MINSCALE = 4;
    public static final int MAXSCALE = 16;
    public static final int DEFAULTSCALE = 6;
    
    public static final Color COLOR_BACKGROUND = new Color(0,0,0);
    public static final Color COLOR_LINES = new Color(0f,1,0);
    public static final Color COLOR_FIELD = new Color(0.5f,0,1);
    public static final Color COLOR_OUTOFBOUNDS = new Color(.5f,0,0);
    public static final Color COLOR_DRAGSELECTIONBOX = new Color(1,.5f,0);
    
    public static final Vector2 OFFSET = new Vector2(160,139);
    public static final float SIZEFLOAT = 400;
    public static final float RADIUSFLOAT = SIZEFLOAT/2;
    public static final Vector2 SIZE = new Vector2(SIZEFLOAT,SIZEFLOAT);
    public static final Vector2 CENTER = OFFSET.add(SIZE.div(2));
    
    public static final Vector2 FIELDSIZE = new Vector2(996,662);
    public static final Vector2 FIELDUNITSIZE = Vector2.normalize(FIELDSIZE);
    public static final float UNITSIZE = FIELDSIZE.magnitude();
    public static final float FIELDANGLE = Vector2.toAngle(FIELDSIZE);
    
    static final int NUMANGLELINES = 12;
    
    //////////Statics
    public static SpawnMap currentMap;
    public static void setCurrentSpawnMap(SpawnMap map){
	currentMap = map;
    }
    
    public Vector2 relativeViewOffset = new Vector2(0,0);
    
    public static boolean onlyShowSelected;
    public static boolean showEnemyColors = true;
    public static boolean showRingNumbers;
    public static boolean showMouseDistance;
    
    public static MapSnapType snapType;
    public static int snapSize;
    
    static float scale = MAXSCALE;
    static float desiredscale = DEFAULTSCALE;
    
    public static void setScale(float sc){
	desiredscale = sc;
    }
    
    //////////Instance
    public ArrayList<SpawnLocation> spawns;
    public SpawnMap(){
	spawns = new ArrayList<SpawnLocation>();
	setPosition(OFFSET);
	setSize(SIZE);
	currentMap = this;
	/*
	for(int i = 0; i < 1000; i++){
	    addSpawnLocation(new SpawnLocation(Vector2.zero(),SpawnType.Fuzzy,i));
	}//*/
    }
    
    /////Register (or not)
    public void register(){
    }
    
    /////Position conversions
    Vector2 getCenterOnScreen(){
	return CENTER.sub(relativeViewOffset.mul(RADIUSFLOAT/scale));
    }
    
    public Vector2 originalScreenToMap(Vector2 pos){
	return pos.sub(CENTER).div(RADIUSFLOAT/scale);
    }
    public Vector2 screenToMap(Vector2 pos){
	//return pos.sub(CENTER).div(RADIUSFLOAT/scale);
	return pos.sub(getCenterOnScreen()).div(RADIUSFLOAT/scale);
    }
    
    public Vector2 mapToScreen(Vector2 pos){
	//return pos.mul(RADIUSFLOAT/scale).add(CENTER);
	return pos.mul(RADIUSFLOAT/scale).add(getCenterOnScreen());
    }
    
    boolean checkOutOfMapBounds(Vector2 mappos){//Checks if a map position would be out of the maximum range
	return !Vector2.distanceLessThan(mappos,Vector2.zero(),MAXSCALE/2);
    }
    
    /////Add/Remove locations
    void addSpawnLocationAt(SpawnLocation spawn,int index){
	spawns.add(index,spawn);
	if(SpawnList.instance != null)
	    SpawnList.instance.focusOnBar(index);
    }
    public void addSpawnLocation(SpawnLocation spawn){
	SpawnLocation otherspawn;
	for(int i = 0; i < spawns.size(); i++){
	    otherspawn = spawns.get(i);
	    if(spawn.time <= otherspawn.time){
		if(spawn.time == otherspawn.time){
		    for(int j = i; j < spawns.size(); j++){
			if(spawns.get(j).time != spawn.time || spawn.type.ordinal() < spawns.get(j).type.ordinal()){
			    addSpawnLocationAt(spawn,j);
			    return;
			}
		    }
		}else{
		    addSpawnLocationAt(spawn,i);
		    return;
		}
	    }
	}
	addSpawnLocationAt(spawn,spawns.size());
    }
    public void removeSpawnLocation(SpawnLocation spawn){
	spawns.remove(spawn);
    }
    
    /////Message (at bottom of screen)
    static String message;
    static final int MESSAGECOUNTDOWN = 600;
    static final int MESSAGECOUNTDOWN_FADESTART = 100;
    static int countdown;
    public static void showMessage(String message){
	currentMap.message = message;
	countdown = MESSAGECOUNTDOWN;
    }
    
    /////Update
    SpawnLocation currentPoint;
    boolean checkValidSpawnLocation(){
	return currentPoint.position.magnitude() <= MAXSCALE && checkMouseOver();
    }
    
    boolean movingMap;
    Vector2 originalmousescreenpos;
    void update_middleclickmapmovement(){
	if(Center.middleMouseClicked() && checkMouseOver()){
	    movingMap = true;
	    originalmousescreenpos = Center.mousePosition();
	}
	if(movingMap){
	    if(Center.middleMouseDown()){
		relativeViewOffset = relativeViewOffset.sub(originalScreenToMap(Center.mousePosition().sub(originalmousescreenpos).add(CENTER)));
		originalmousescreenpos = Center.mousePosition();
		if(relativeViewOffset.magnitude() > MAXSCALE)
		    relativeViewOffset = Vector2.normalize(relativeViewOffset,MAXSCALE);
	    }else{
		movingMap = false;
	    }
	}
    }
    
    Vector2 rightclickdragstartposition;
    void update_rightclickselection(){
	if(Center.rightMouseDown()){
	    if(!checkMouseOver()) return;
	    if(Center.rightMouseClicked()){
		Vector2 mousepos = screenToMap(Center.mousePosition());
		rightclickdragstartposition = mousepos;
		float dist = 99999;
		ArrayList<SpawnLocation> selectedlocations = new ArrayList<SpawnLocation>();
		for(SpawnLocation location : spawns){
		    float d = Vector2.distance(mousepos,location.position);
		    if(d == dist){
			selectedlocations.add(location);
		    }else if(d < dist){
			selectedlocations.clear();
			dist = d;
			selectedlocations.add(location);
		    }
		}
		if(!Center.getKey(Keys.SHIFT) && !Center.getKey(Keys.CONTROL))
		    SpawnList.clearSelections();
		for(SpawnLocation location : selectedlocations){
		    SpawnList.instance.selectedSpawns.add(location);
		    SpawnList.instance.focusOnBar(spawns.indexOf(location));
		}
	    }
	    if(rightclickdragstartposition == null)
		return;
	    Vector2 mousepos = screenToMap(Center.mousePosition());
	    if(!Vector2.distanceLessThan(rightclickdragstartposition,mousepos,.1f)){
		Vector2 pos0 = new Vector2(mousepos.x < rightclickdragstartposition.x ? mousepos.x : rightclickdragstartposition.x,
			mousepos.y < rightclickdragstartposition.y ? mousepos.y : rightclickdragstartposition.y);
		Vector2 pos1 = new Vector2(mousepos.x > rightclickdragstartposition.x ? mousepos.x : rightclickdragstartposition.x,
			mousepos.y > rightclickdragstartposition.y ? mousepos.y : rightclickdragstartposition.y);
		if(!Center.getKey(Keys.SHIFT) && !Center.getKey(Keys.CONTROL))
		    SpawnList.clearSelections();
		for(SpawnLocation location : spawns){
		    if(location.position.x > pos0.x && location.position.y > pos0.y
			    && location.position.x < pos1.x && location.position.y < pos1.y)
			SpawnList.selectedSpawns.add(location);
		}
	    }
	}else
	    rightclickdragstartposition = null;
    }
    
    public void update(){//sorry for all the megafunctions here
	super.update();
	
	scale = desiredscale*.08f+scale*.92f;
	//drawOffset = viewOffset.add(scale);
	
	update_middleclickmapmovement();
	update_rightclickselection();
	
	if(this.checkMouseOver()){
	    if(Center.leftMouseDown()){
		if(Center.leftMouseClicked()){
		    currentPoint = new SpawnLocation(Center.mousePosition(),Gui.SpawnLocationCreator_getDesiredSpawnType(),Gui.SpawnLocationCreator_getDesiredSpawnTime());
		    SpawnList.clearSelections();
		}
		if(currentPoint != null){
		    currentPoint.position = screenToMap(Center.mousePosition());
		    if(!checkValidSpawnLocation()){
			showMessage("Invalid spawn location.");
			currentPoint.position = new Vector2(9999,0);
		    }else{
			String mess = "Placing spawn point.";
			for(int i = 0; i < (_G.cycle%150)/50; i++)
			    mess += '.';
			showMessage(mess);
		    }
		}
	    }else if(currentPoint != null){
		if(!checkValidSpawnLocation()){//Out of bounds
		    showMessage("Invalid spawn location.");
		    currentPoint = null;
		}else{
		    if(currentPoint.position.magnitude() < 2){
			showMessage("WARNING: Location too close!");
		    }else{
			showMessage("Spawn point placed!");
		    }
		    addSpawnLocation(currentPoint);
		    SpawnList.selectSpawn(currentPoint);
		    currentPoint = null;
		}
	    }
	}else if(currentPoint != null){
	    if(Center.leftMouseDown()){
		showMessage("Invalid spawn location.");
		currentPoint.position = new Vector2(9999,0);
	    }else{
		currentPoint = null;
		showMessage("Point placement cancelled.");
	    }
	}else if(SpawnList.selectedSpawns.size() != 0){
	    int siz = SpawnList.selectedSpawns.size();
	    //showMessage(""+siz+(siz == 1 ? " point selected." : " points selected."));
	}
    }
    
    /////Draw
    void drawSingleSpawnLocation(SpawnLocation s,Vector2 screenpos){
	/////Basic filled circle
	//GraphicsDraw.fillCircle(screenpos,5);
	
	/////X's with a small filled circle
	/*GraphicsDraw.fillCircle(screenpos,3);
	GraphicsDraw.line(new Vector2(screenpos.x-5,screenpos.y-5),new Vector2(screenpos.x+5,screenpos.y+5));
	GraphicsDraw.line(new Vector2(screenpos.x+5,screenpos.y-5),new Vector2(screenpos.x-5,screenpos.y+5));*/
	
	/////X's with a small lined circle
	float siz = 5+SpawnTypes.getLevel(s.type)*3;
	GraphicsDraw.circle(screenpos,siz/2);
	GraphicsDraw.line(new Vector2(screenpos.x-siz,screenpos.y-siz),new Vector2(screenpos.x+siz,screenpos.y+siz));
	GraphicsDraw.line(new Vector2(screenpos.x+siz,screenpos.y-siz),new Vector2(screenpos.x-siz,screenpos.y+siz));
	if(showEnemyColors){
	    Color c = GraphicsDraw.screen.getColor();
	    GraphicsDraw.setColor(SpawnTypes.getColor1(s.type));
	    GraphicsDraw.fillCircle(screenpos,siz/2);
	    /*GraphicsDraw.setColor(SpawnTypes.getColor2(s.type));
	    GraphicsDraw.fillCircle(screenpos,2);*/
	    GraphicsDraw.setColor(c);
	}
    }
    final float MESSAGEHEIGHT = 32;
    void draw_message(){
	Vector2 pos = new Vector2(CENTER.x,CENTER.y+RADIUSFLOAT+MESSAGEHEIGHT/2);
	Vector2 siz = new Vector2(SIZEFLOAT,MESSAGEHEIGHT);
	GraphicsDraw.setColor(Color.WHITE);
	GraphicsDraw.fillRectangle(pos,siz);
	GraphicsDraw.setColor(Color.BLACK);
	GraphicsDraw.rectangle(pos,siz);
	if(countdown != 0 && message != null){
	    countdown--;
	    if(countdown < MESSAGECOUNTDOWN_FADESTART){
		float a = 1-((float)countdown/MESSAGECOUNTDOWN_FADESTART);
		GraphicsDraw.setColor(new Color(a,a,a));
	    }
	    GraphicsDraw.biggishFont();
	    GraphicsDraw.centerText(message,new Vector2(pos.x,pos.y-3));
	}
    }
    Vector2 drawOffset;
    public void draw(){
	Vector2 centeronscreen = getCenterOnScreen();
	
	/////Draw square/out-of-bounds
	GraphicsDraw.setColor(COLOR_OUTOFBOUNDS);
	GraphicsDraw.fillRectangle(CENTER,SIZE);
	GraphicsDraw.setColor(COLOR_BACKGROUND);
	GraphicsDraw.fillCircle(getCenterOnScreen(),RADIUSFLOAT*MAXSCALE/scale);
	
	/////Draw field
	GraphicsDraw.setColor(COLOR_FIELD);
	Vector2 p1 = FIELDUNITSIZE.mul(SIZEFLOAT/scale);
	Vector2 p0 = centeronscreen.add(p1.mul(-1));
	p1 = p1.mul(2);//CENTER.add(p1);
	GraphicsDraw.screen.fillRect((int)p0.x,(int)p0.y,(int)p1.x,(int)p1.y);
	
	/////Draw lines
	GraphicsDraw.setColor(COLOR_LINES);
	GraphicsDraw.smallBoldFont();
	float ringradius = 0;
	for(int ring = 2; ring <= MAXSCALE; ring += 2){
	    ringradius = RADIUSFLOAT*ring/scale;
	    GraphicsDraw.circle(centeronscreen,ringradius);
	    if(showRingNumbers){
		    GraphicsDraw.text(""+(ring/2),centeronscreen.add(new Vector2(ringradius,0)));
		    GraphicsDraw.text(""+(ring/2),centeronscreen.add(new Vector2(-ringradius-8,0)));
	    }
	}
	for(int line = 0; line < NUMANGLELINES; line++){
	    GraphicsDraw.line(getCenterOnScreen(),getCenterOnScreen().add(Vector2.fromAngle(line*_G.TAU/NUMANGLELINES,/*finalring*//*RADIUSFLOAT*1.5f*/RADIUSFLOAT/scale*MAXSCALE)));
	}
	
	/////Draw mouse distance from center
	if(showMouseDistance){
	    GraphicsDraw.setColor(COLOR_LINES);
	    GraphicsDraw.text(""+(float)(int)(screenToMap(Center.mousePosition()).magnitude()/2*100)/100,Center.mousePosition());
	}
	
	/////Draw points
	//normal points
	if(!onlyShowSelected){
	    GraphicsDraw.setColor(Color.RED);
	    for(int i = 0; i < spawns.size(); i++){
		drawSingleSpawnLocation(spawns.get(i),mapToScreen(spawns.get(i).position));
	    }
	}
	//selected points
	float blah = ((float)Math.sin((float)_G.cycle/10)+1)/4;
	GraphicsDraw.setColor(new Color(1,.5f+blah,0));
	for(int i = 0; i < spawns.size(); i++){
	    if(SpawnList.selectedSpawns.contains(spawns.get(i)))
		drawSingleSpawnLocation(spawns.get(i),mapToScreen(spawns.get(i).position));
	}
	if(currentPoint != null){
	    drawSingleSpawnLocation(currentPoint,mapToScreen(currentPoint.position));
	}
	
	/*/////Silly Radar Effect for Eric
	float radarangle = ((float)_G.cycle/50)%(_G.PI*2);
	for(float add = 0; add < _G.PI/16; add += _G.PI/1024){
	    float a = add/(_G.PI/16);
	    GraphicsDraw.setColor(new Color(0,a,0,a));
	    GraphicsDraw.line(CENTER,CENTER.add(Vector2.fromAngle(radarangle+add,RADIUSFLOAT)));
	}//*/
	
	/////Draw the drag-select box
	if(rightclickdragstartposition != null){
	    GraphicsDraw.setColor(COLOR_DRAGSELECTIONBOX);
	    Vector2 pos = mapToScreen(rightclickdragstartposition);
	    Vector2 mousepos = Center.mousePosition();
	    Vector2 siz = mousepos.sub(pos);
	    siz = new Vector2(Math.abs(siz.x),Math.abs(siz.y));
	    pos = new Vector2(pos.x < mousepos.x ? pos.x : mousepos.x,pos.y < mousepos.y ? pos.y : mousepos.y);
	    GraphicsDraw.screen.drawRect((int)pos.x,(int)pos.y,(int)siz.x,(int)siz.y);
	}
	
	/////Draw over any out-of-boundary lines to hide them
	GraphicsDraw.setColor(_G.COLOR_BACKGROUND);
	GraphicsDraw.fillRectangle(CENTER.add(new Vector2(0,-SIZE.y)),new Vector2(SIZE.x*2,SIZE.y));
	GraphicsDraw.fillRectangle(CENTER.add(new Vector2(-SIZE.x,0)),new Vector2(SIZE.x,SIZE.y*2));
	GraphicsDraw.fillRectangle(CENTER.add(new Vector2(0,SIZE.y)),new Vector2(SIZE.x*2,SIZE.y));
	GraphicsDraw.fillRectangle(CENTER.add(new Vector2(SIZE.x,0)),new Vector2(SIZE.x,SIZE.y*2));
	
	/////Draw message
	draw_message();
	
	/////Draw title
	GraphicsDraw.setColor(Color.BLACK);
	GraphicsDraw.boldFont();
	GraphicsDraw.centerText("MAP",new Vector2(OFFSET.x+SIZE.x/2,OFFSET.y-10));
    }
}

class SpawnLocation{
    public Vector2 position;
    public SpawnType type;
    public float time;
    
    public SpawnLocation(Vector2 pos,SpawnType typ,float tim){
	position = pos;
	type = typ;
	time = tim;
    }
}

class SpawnMap_RecenterButton extends GuiButton{
    CenteredText centeredText;
    public SpawnMap_RecenterButton(){
	setSize(new Vector2(150,40));
	setPosition(new Vector2(SpawnMap.OFFSET.x-160,SpawnMap.OFFSET.y+40));
	applyfont();
	centeredText = new CenteredText("Recenter",getCenter());
    }
    public Color getBaseColor(){
	return Color.GREEN;
    }
    
    final void applyfont(){
	GraphicsDraw.biggishFont();
    }
    
    public void button_OnMouseClick(){
	SpawnMap.currentMap.relativeViewOffset = Vector2.zero();
    }
    
    public boolean checkMouseClicked(){
	return super.checkMouseClicked() || Center.getKey(Keys.R);
    }
    
    public boolean shouldBeDepressed(){
	return super.shouldBeDepressed() || Center.getKey(Keys.R);
    }
    
    public void draw(){
	super.draw();
	applyfont();
	GraphicsDraw.centerText(centeredText);
	GraphicsDraw.smallBoldFont();
	GraphicsDraw.text("[R]",new Vector2(position.x+size.x-17,position.y+size.y-7));
    }
}
