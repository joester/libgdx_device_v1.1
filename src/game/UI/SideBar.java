package game.UI;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//outside: pass Manager.getArtAssets("string packname")

public class SideBar
{
	private static int screenWidth = 1280;
	private static int screenHeight = 800;
	private Sprite base;
	private Sprite xpBar;
	private Sprite xpBack;
	private Sprite xpGlow;
	
	public SideBar(HashMap<String, Texture> m)
	{
		base = new Sprite(m.get("ui_base"));
		base.setSize(screenWidth*0.15f,screenHeight);
		base.setPosition(screenWidth - base.getWidth(), 0);
		
		xpBar = new Sprite(m.get("ui_xpfill"));
		xpBar.setSize(screenWidth*0.15f, screenHeight);
		xpBar.setPosition(screenWidth - xpBar.getWidth(), 0);
	}
}