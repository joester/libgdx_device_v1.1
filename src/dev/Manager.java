package dev;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import device.graphics.Graphics;

public class Manager {
	
	HashMap<String, ArrayList<String>> textureMap = new HashMap<String, ArrayList<String>>();
	AssetManager a_manager = new AssetManager();
	HashMap<String, HashMap<String, String>> entries = new HashMap<String, HashMap<String, String>>();
	String current_pack = "";
	HashMap<String, Texture> tex = new HashMap<String, Texture>();
	HashMap<String, Sound> sound = new HashMap<String, Sound>();
	HashMap<String, Music> music = new HashMap<String, Music>();
	
	Graphics graphics = new Graphics();
	
	public Manager(){
	this.addPath(
				"Game",
				"hero", "data/objects/hero.png",			
				"device", "data/objects/device.png",			
				"exp", "data/exp.png",						
				"fuzz1", "data/monsters/Fuzzies1.png",		
				"device_spawn", "data/objects/device_spawn.png",	
				"fuzz2", "data/monsters/Fuzzies2.png",		
				"fuzz3", "data/monsters/Fuzzies3.png",		
				"device_hit", "data/objects/device_hit.png",		
				"indicate", "data/UI/indication_sheet.png",		
				"mine_drop","data/objects/mineDrop.png",
				"mine","data/mine.png",					
				"vortex_drop","data/vortIcon.png",			
				"vortex","data/vortex.png",					
				"dust","data/monsters/dust.png",			
				"shock_wave","data/monsters/shock_wave.png",	
				"game_bg","data/backgrounds/grass.png",		
				"deathRing", "data/DeathRing.png",
				"ui_base","data/UI/UIBase.png",
				"ui_xpfill", "data/UI/XPFill.png",
				"ui_xpempty", "data/UI/XPEmpty.png",
				"ui_xpglow", "data/UI/XPGlow.png",
				"ui_pause", "data/UI/Pause.png",
				"ui_warn", "data/UI/warn.png",
				"ui_bomb", "data/UI/bomb.png",
				"ui_bombcount", "data/UI/BombCount.png",
				"ui_mine", "data/UI/mine.png",
				"ui_minecount", "data/UI/count.png",
				"ui_vortex", "data/UI/vortex.png",
				"plant1", "data/monsters/plant/plant_one.png",
				"plant2", "data/monsters/plant/plant_two.png"
				
			
		);
	
	this.addPath(
			"Main",
			"main_play","data/UI/play.png", 
			"main_bg","data/backgrounds/TitleScreen.png", 			
			"main_help","data/UI/help.png"
			
			);
	
	this.addPath(
			
			"Tut",
			"tut_pg1", "data/UI/tut1.png",
			"tut_pg2", "data/UI/tut2.png",
			"tut_nav_left", "data/UI/nav_left.png",
			"tut_nav_right","data/UI/nav_right.png",
			"tut_nav_exit", "data/UI/nav_exit.png"
			
			);
	
	this.addPath(			
			"End",
			"end_bg", "data/backgrounds/gameover.png",
			"end_retry", "data/UI/retry.png",
			"end_quit", "data/UI/quitter.png"
			
	);
	
	this.addPath(
			"Intro",
			"sc0","data/scenes/sketch_scene1-2.png",
			"sc1", "data/scenes/sketch_scene2.png",
			"sc2", "data/scenes/sketch_scene3-2.png",
			"sc3", "data/scenes/sketch_scene4-2.png",
			"sc4", "data/scenes/sketch_scene5.png"
	);
	this.addPath(
			"Outro",
			"sc0", "data/scenes/sketch_scene6-2.png",
			"sc1", "data/scenes/sketch_scene7.png",
			"sc2", "data/scenes/sketch_scene8.png"				
	);
	
	}
	
	
	//Takes variable number of filepaths as strings to add into map for asset reference.
	public void addPath(String packageName, String... args){
		if(!entries.containsKey(packageName)){
			entries.put(packageName, new HashMap<String, String>());
		}
		for(int i = 0; i < args.length; i ++){
			entries.get(packageName).put(args[i], args[++i]);
		}
	}
	
	public void loadArtAssets(String packName){
		//System.out.println(entries.isEmpty());
		for(String str : entries.get(packName).values()){			
			a_manager.load(str, Texture.class);
		}
		current_pack=packName;
		a_manager.finishLoading();
	}
	
	public HashMap<String, Texture> getArtAssets(String packName){
		HashMap<String, Texture> thing = new HashMap<String, Texture>();
		for(String str : entries.get(packName).keySet()){
			thing.put(str, (Texture)a_manager.get(entries.get(packName).get(str)));
		}
		//System.out.println(thing.isEmpty());
		return thing;
	}
	
	public HashMap<String, Sound> getSoundAssets(String packName){
		return this.sound;
	}
	
	public HashMap<String, Music> getMusicAssets(String packName){
		return this.music;
	}
	
	public void unloadArtAssets(){
		if(current_pack == ""){
			return;
		}
		for(String str : entries.get(current_pack).values()){
			a_manager.unload(str);
		}
		current_pack="";
	}
	
}
