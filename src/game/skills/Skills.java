package game.skills;

public abstract class Skills
{
	protected String iconPath;
	protected boolean buttonToggle;
	protected int currentStock;
	protected int maxStock;
	
	public String getPath(){ return iconPath; }
	public boolean getToggle() { return buttonToggle; }
	public int getCurrentStock() { return currentStock; }
	public int getMaxStock() { return maxStock; }
	
	public abstract void add();
	public abstract void activate();
}