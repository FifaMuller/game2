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
	    int res = 0;
	    if (this.hasReachedTarget()) {
	        res = res + target.takeDamage(getDamage());
	        if (target.isDefeated()) {
	            target.setCurrentHealth(0); 
	            return res;
	        } else {
	            res = res + target.takeDamage(getDamage());
	            return res;
	        }
	    } else {
	        return 0;
	    }
	}

}