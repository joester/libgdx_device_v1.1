package game;

import game.objects.Player;
import sounds.SoundSystem;

public class GameStats {
	
	private int score = 0;
	private int xpCount = 0;
	private int xpMax=20;
	private int level = 1;
	private int mineCount = 0;
	private int vortCount = 0;
	private int maxItemCount;
	private float boxHP;
	private float totalTimeElapsed;
	private SoundSystem sound;
	private Player player;
	
	public GameStats(Player player, SoundSystem sound){
		maxItemCount = 3;
		this.sound = sound;
		this.player = player;
	}
	
	public void addScore(int scoreAdd){
		score += scoreAdd;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getXP(){
		return xpCount;
	}
	
	public void addXP(int XP){
		xpCount += XP;
		while (xpCount > xpMax)
		{
			xpCount -= xpMax;
			setXPMax((int)Math.floor(getXPMax() * 1.2));
			level++;
			sound.playSound(SoundSystem.plevel);
			player.levelUp = 3;
		}
	}
	
	public int getXPMax(){
		return xpMax;
	}
	
	public void setXPMax(int XP){
		xpMax = XP;
	}
	
	public float getXPpercent(){
		return (float)xpCount/xpMax;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void levelUp(){
		level++;
	}
	
	public float getboxHP(){
		return boxHP;
	}
	
	public void setBoxHP(float f){
		boxHP = f;
	}
	
	public int minutesElapsed(){
		return (int)totalTimeElapsed / 60;
	}
	
	public int secondsElapsed(){
		return (int)totalTimeElapsed % 60;
	}
	
	public int timeElapsed(){
		return (int)totalTimeElapsed;
	}
	
	public void updateTimeElapsed(float dt){
		totalTimeElapsed += dt;
	}
	
	public int getMineCount(){
		return mineCount;
	}
	
	public boolean useMine(){
		if(mineCount > 0){
			mineCount--;
			return true;
		}
		return false;
	}
	
	public boolean addMine(){
		if(mineCount + 1 > maxItemCount){
			return false;
		}
		mineCount ++;
		return true;
	}
	
	public int getVortCount(){
		return vortCount;
	}
	
	public boolean useVort(){
		if(vortCount > 0){
			vortCount --;
			return true;
		}
		return false;
	}
	
	public boolean addVortex(){
		if(vortCount + 1 > maxItemCount){
			return false;
		}
		vortCount ++;
		return true;
	}
}
