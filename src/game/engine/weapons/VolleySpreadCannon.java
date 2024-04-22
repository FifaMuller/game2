package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.interfaces.Attacker;
import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon implements Attacker
{
	public static final int WEAPON_CODE = 3;

	private final int minRange;
	private final int maxRange;

	public VolleySpreadCannon(int baseDamage, int minRange, int maxRange)
	{
		super(baseDamage);
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	public int getMinRange()
	{
		return minRange;
	}

	public int getMaxRange()
	{
		return maxRange;
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int rv = 0;
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		if (laneTitans.isEmpty())
			return rv;
		else {
			while(!laneTitans.isEmpty()) {
				if (laneTitans.peek().getDistance()<=getMaxRange() && laneTitans.peek().getDistance()>=getMinRange()) {
					rv = rv + attack(laneTitans.peek());
					if(laneTitans.peek().isDefeated())
						laneTitans.poll();
					else
						pq.add(laneTitans.poll());
					
					
				}
				else
					pq.add(laneTitans.poll());
				
				
			}
			while(!pq.isEmpty()) {
				
					laneTitans.add(pq.poll());
				
			}
		
			return rv;

	}

}
}
