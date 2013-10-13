package thedevice_spawneditor;

import java.awt.Color;

public enum SpawnType{//Do not change the order/numbers or it will mess up saves
    Fuzzy1,//0
    Fuzzy2,//1
    Fuzzy3,//2
    Plant1,//3
    Plant2,//4
    Plant3,//5
    Knight1,//6
    Knight2,//7
    Knight3,//8
    Phoenix,//9
}

class SpawnTypes{
    public static int getLevel(SpawnType t){
	switch(t){
	    case Fuzzy1:
	    case Plant1:
	    case Knight1:
		return 0;
	    case Fuzzy2:
	    case Plant2:
	    case Knight2:
		return 1;
	    case Fuzzy3:
	    case Plant3:
	    case Knight3:
		return 2;
	}
	return 10;
    }
    
    public static String getName(SpawnType t){
	switch(t){
	    case Fuzzy1:
		return "Fuzzy 1";
	    case Fuzzy2:
		return "Fuzzy 2";
	    case Fuzzy3:
		return "Fuzzy 3";
	    case Plant1:
		return "Plant 1";
	    case Plant2:
		return "Plant 2";
	    case Plant3:
		return "Plant 3";
	    case Knight1:
		return "Knight 1";
	    case Knight2:
		return "Knight 2";
	    case Knight3:
		return "Knight 3";
	    case Phoenix:
		return "Phoenix";
	}
	return "ERROR";
    }
    
    static final float FUZZYGRAY = 174f/255;
    public static Color getColor1(SpawnType t){
	switch(t){
	    case Fuzzy1:
	    case Fuzzy2:
	    case Fuzzy3:
		return new Color(FUZZYGRAY,FUZZYGRAY,FUZZYGRAY);
	    /*case Fuzzy2:
		return new Color(.3f,.3f,.3f);
	    case Fuzzy3:
		return new Color(.1f,.1f,.1f);*/
	    case Plant1:
	    case Plant2:
	    case Plant3:
		return new Color(.2f,1,.2f);
	    /*case Plant2:
		return new Color(.2f,.7f,.2f);
	    case Plant3:
		return new Color(.2f,.4f,.2f);*/
	    case Knight1:
	    case Knight2:
	    case Knight3:
		return new Color(.3f,.3f,.3f);
	    case Phoenix:
		return new Color(.85f,0,0);
	}
	return Color.BLUE;
    }
    public static Color getColor2(SpawnType t){
	switch(t){
	    case Fuzzy1:
		return new Color(222f/255,165f/255,198f/255);
	    case Fuzzy2:
		return new Color(222f/255,100f/255,125f/255);
	    case Fuzzy3:
		return new Color(222f/255,20f/255,25f/255);
	    case Plant1:
		return new Color(.2f,.6f,.2f);
	    case Plant2:
		return new Color(.6f,.4f,.2f);
	    case Plant3:
		return new Color(1,.2f,.2f);
	    case Knight1:
		return new Color(1,1,.3f);
	    case Knight2:
		return new Color(1,.7f,.21f);
	    case Knight3:
		return new Color(1,.4f,.12f);
	    case Phoenix:
		return new Color(.75f,.32f,0);
	}
	return Color.RED;
    }
}
