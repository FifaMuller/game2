package game.engine.interfaces;

public interface Attacker
{
	int getDamage(); // gets the damage value to be applied
	
	
	
	public default int attack(Attackee target) {
		return target.takeDamage(getDamage());
		
			
	}

}
