//Global stuff

package thedevice_spawneditor;

import java.awt.Color;

public class _G{
    public static final float PI = (float)Math.PI;
    public static final float TAU = PI*2;
    
    public static int screenWidth = 800,screenHeight = 600;
    
    public static Vector2 screenSize = new Vector2(screenWidth,screenHeight);
    
    public static long timeElapsed;
    
    public static int cycle = 0;
    
    public static final Color COLOR_BACKGROUND = new Color(.85f,.8f,.99f);//Color.WHITE;
    public static final Color COLOR_FOREGROUND = Color.BLACK;
    
    public static final Color COLOR_ENEMYVARIATOR = new Color(1,.6f,.6f);
    public static final Color COLOR_SECONDSVARIATOR = new Color(.5f,.75f,1);
    public static final Color COLOR_MILLISECONDSVARIATOR = new Color(.6f,.85f,1);
    
    public static final Color COLOR_MAPZOOMVARIATOR = new Color(0f,.9f,0);
    public static final Color COLOR_MAPSNAPVARIATOR = new Color(.1f,.7f,0);
}
