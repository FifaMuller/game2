package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.interfaces.Attacker;
import game.engine.titans.Titan;

public class SniperCannon extends Weapon implements Attacker
{
	public static final int WEAPON_CODE = 2;

	public SniperCannon(int baseDamage)
	{
		super(baseDamage);
	}

	
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int rv = 0;
		if (laneTitans.isEmpty()) {
			return rv;
		}
		else {
			Titan a = laneTitans.poll();
			rv = rv + attack(a);
			if (a.isDefeated())
				return rv;
			else
				laneTitans.add(a);
			return rv;
		}
			
		
	}

}
