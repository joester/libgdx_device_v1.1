package game;

import com.badlogic.gdx.Gdx;

import game.objects.Player;
import sounds.SoundSystem;

public final class GameStats {
	private static int monsterCount = 0;
	private static int score = 0;
	private int xpCount = 0;
	private int xpMax=20;
	private int level = 1;
	private int mineCount = 3;
	private int vortCount = 3;
	private int nukeCount = 3;
	private float nukeCD = 0;
	private int maxItemCount;
	private float boxHP;
	private static float totalTimeElapsed;
	private SoundSystem sound;
	private Player player;
	private boolean pause = false;
	private int placeItem = 0;
	private boolean nukeState = false;
	
	public GameStats(Player player, SoundSystem sound){
		maxItemCount = 3;
		this.sound = sound;
		this.player = player;
	}
	
	public void addMonsterKill()
	{
		monsterCount++;
	}
	public static int getMonsterCount()
	{
		return monsterCount;
	}
	
	public void addScore(int scoreAdd){
		score += scoreAdd;
	}
	
	public static int getScore(){
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
	
	public static int timeElapsed(){
		return (int)totalTimeElapsed;
	}
	
	public void updateTimeElapsed(){
		float dt = Gdx.graphics.getDeltaTime();
		totalTimeElapsed += dt;
		nukeCD -= dt;
	}
	
	public int getMineCount(){
		return mineCount;
	}
	
	public boolean useMine(){
		if(mineCount > 0 && placeItem == 0){
			mineCount--;
			placeItem = 1;
			return true;
		}
		return false;
	}
	
	public boolean placeMine()
	{
		placeItem = 0;
		return true;
	}
	
	public boolean mineReady()
	{
		return (mineCount > 0 && placeItem != 1);
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
		if(vortCount > 0 && placeItem == 0){
			vortCount--;
			placeItem = 2;
			return true;
		}
		return false;
	}
	
	public void placeVort()
	{
		placeItem = 0;
	}
	
	public boolean vortexReady()
	{
		return (vortCount > 0 && placeItem != 2);
	}
	
	public boolean addVortex(){
		if(vortCount + 1 > maxItemCount){
			return false;
		}
		vortCount ++;
		return true;
	}
	
	public boolean useNuke()
	{
		if (nukeCount > 0)
		{
			nukeCount--;
			nukeState = true;
			nukeCD = 3;
			return true;
		}
		return false;
	}
	
	public void nukeStateOff()
	{
		nukeState = false;
	}
	
	public boolean nukeState()
	{
		return nukeState;
	}
	
	public boolean nukeReady()
	{
		return nukeCount > 0 && nukeCD <= 0;
	}
	
	public int nukeCount()
	{
		return nukeCount;
	}
	
	public void pauseToggle()
	{
		pause = !pause;
	}
	
	public boolean pauseState()
	{
		return pause;
	}
	
	public int placeItem()
	{
		return placeItem;
	}
}
