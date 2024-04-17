package game.engine.titans;

import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;

public class AbnormalTitan extends Titan implements Attacker
{
	public static final int TITAN_CODE = 2;

	public AbnormalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	
	public int attack(Attackee target) {
		if(this.hasReachedTarget()) {
			target.takeDamage(getDamage());
			if(target.isDefeated())
				return target.getResourcesValue();
			else {
				target.takeDamage(getDamage());
				if(target.isDefeated())
					return target.getResourcesValue();
				else
					return 0;
			}
			
		}
		else return 0;
	
			
		
	}

}
