package game.skills;

public class MineSkill extends Skills
{
	public MineSkill(String icon, Boolean toggle, int start, int max)
	{
		iconPath = icon;
		buttonToggle = toggle;
		currentStock = start;
		maxStock = max;
	}

	public void add() {
		if(currentStock < maxStock)
			currentStock++;
	}

	public void activate() {
		currentStock--;
		
		// TODO Skill - Game interaction
	}

}